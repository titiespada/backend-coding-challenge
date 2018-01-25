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
		if ($scope.expensesform.$valid) {
			var amountArr = $scope.newExpense.amount.split(' ');
			$scope.newExpense.amount = amountArr[0];
			$scope.newExpense.currency = amountArr[1];

			// Post the expense via REST
			restExpenses.post($scope.newExpense).then(function(response) {
				// Reload new expenses list
				loadExpenses();
				$scope.clearExpense();
			});
		}
	};

	$scope.clearExpense = function() {
		$scope.newExpense = {};
	};

	$scope.calculateVat = function() {
		if ($scope.newExpense.amount != null) {
			var amountWithoutCurrency = $scope.newExpense.amount.split(' ')[0];

			var vat = amountWithoutCurrency - (amountWithoutCurrency / 1.2);
			$scope.newExpense.vat = $filter('number')(vat, 2);
		} else {
			$scope.newExpense.vat = null;
		}
	}

	// Initialise scope variables
	loadExpenses();
	$scope.clearExpense();
}]);
