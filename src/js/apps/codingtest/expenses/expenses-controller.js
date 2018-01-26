"use strict";

/******************************************************************************************

Expenses controller

******************************************************************************************/

var app = angular.module("expenses.controller", []);

app.controller("ctrlExpenses", ["$rootScope", "$scope", "config", "restalchemy", "$filter", function ExpensesCtrl($rootScope, $scope, $config, $restalchemy, $filter) {
	// Update the headings
	$rootScope.mainTitle = "Expenses";
	$rootScope.mainHeading = "Expenses";

	// Update the tab sections
	$rootScope.selectTabSection("expenses", 0);

	var restExpenses = $restalchemy.init({ root: $config.apiroot }).at("expenses");

	$scope.dateOptions = {
		changeMonth: true,
		changeYear: true,
		dateFormat: "dd/mm/yy"
	};

	var loadExpenses = function() {
		// Retrieve a list of expenses via REST
		restExpenses.get().then(function(expenses) {
			$scope.expenses = expenses;
		});
	}

	$scope.saveExpense = function() {
		$scope.clearMessages();
		if ($scope.expensesform.$valid) {
			// Format amout, currency and date values
			var amountArr = $scope.newExpense.amount.split(' ');
			$scope.newExpense.amount = amountArr[0];
			$scope.newExpense.currency = amountArr[1];

			$scope.newExpense.date = $filter('date')($scope.newExpense.date, 'dd/MM/yyyy');

			// Post the expense via REST
			restExpenses.post($scope.newExpense).then(function(response) {
				// Show success message
				$scope.successMessage = "Successfully added the expense!";
				$scope.showSuccessMessage = true;
				
				// Reload new expenses list
				loadExpenses();
				$scope.clearExpense();
			}).error(function(response) {
				// Show error message
				$scope.errorMessage = response.message;
				$scope.showErrorMessage = true;
			});
		}
	};

	$scope.clearExpense = function() {
		$scope.newExpense = {};
	};

	$scope.clearMessages = function() {
		$scope.successMessage = "";
		$scope.showSuccessMessage = false;
		$scope.errorMessage = "";
		$scope.showErrorMessage = false;
	};

	$scope.calculateVat = function() {
		if ($scope.newExpense.amount != null) {
			var amountWithoutCurrency = $scope.newExpense.amount.split(' ')[0];

			var vat = amountWithoutCurrency - (amountWithoutCurrency / 1.2);
			vat = $filter('number')(vat, 2);
			vat = (num => num.split(',').join(''))(vat);
			$scope.newExpense.vat = vat;
		} else {
			$scope.newExpense.vat = null;
		}
	}

	// Initialise scope variables
	loadExpenses();
	$scope.clearExpense();
	$scope.clearMessages();
}]);
