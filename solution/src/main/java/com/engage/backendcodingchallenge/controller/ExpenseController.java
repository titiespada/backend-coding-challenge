package com.engage.backendcodingchallenge.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engage.backendcodingchallenge.dto.CurrencyRateDto;
import com.engage.backendcodingchallenge.dto.ExpenseDto;
import com.engage.backendcodingchallenge.model.Expense;
import com.engage.backendcodingchallenge.service.ExchangerateApiService;
import com.engage.backendcodingchallenge.service.ExpenseService;

@RestController
@RequestMapping(path="/app/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);
	
	private static final String GBP_CURRENCY = "GBP";
	private static final String EUR_CURRENCY = "EUR";
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private ExchangerateApiService exchangeApiService;

	@GetMapping
	public ResponseEntity<List<ExpenseDto>> getAll() {
		logger.debug("Search all expenses");
		
		List<Expense> expenses = expenseService.findAll();
		List<ExpenseDto> expensesDto = expenses.stream()
				.map(ExpenseDto::createExpenseDto)
				.collect(Collectors.toList());

		logger.debug("Returning " + expensesDto.size() + " expenses");
		return new ResponseEntity<>(expensesDto, HttpStatus.OK);
	}
    
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> get(@PathVariable(value="id", required=true) Integer id) {
    		logger.debug("Search for expense with id " + id);
    		
    		Expense expense = expenseService.findById(id);
    		if (expense == null) {
    			logger.debug("Expense not found for id " + id);
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
    		
    		ExpenseDto expenseDto = ExpenseDto.createExpenseDto(expense);
    		
    		logger.debug("Returning expense with id " + id + ": " + expenseDto.toString());
        return new ResponseEntity<>(expenseDto, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ExpenseDto> save(@Valid @RequestBody(required=true) ExpenseDto expenseDto) {
    		logger.debug("Save expense:" + expenseDto.toString());
    		
    		expenseDto = parseValueWithCurrency(expenseDto);
    		
    		Expense expense = Expense.createExpense(expenseDto);
    		expense = expenseService.save(expense);
    		if (expense == null) {
    			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		}
    		
    		expenseDto = ExpenseDto.createExpenseDto(expense);
    		
    		logger.debug("Returning saved expense: " + expenseDto.toString());
        return new ResponseEntity<>(expenseDto, HttpStatus.CREATED);
    }
    
    private ExpenseDto parseValueWithCurrency(ExpenseDto expenseDto) {
    		String value = expenseDto.getValue();
    	
    		if (value.contains(EUR_CURRENCY)) {
    			BigDecimal valueWithoutCurrency = new BigDecimal(value.replace(EUR_CURRENCY, "").trim());
    			
    			CurrencyRateDto currencyRateDto = exchangeApiService.callRestService(EUR_CURRENCY, GBP_CURRENCY);
    			
    			expenseDto.setGbpValue(valueWithoutCurrency.multiply(BigDecimal.valueOf(currencyRateDto.getRate())));
    		} else {
    			expenseDto.setGbpValue(new BigDecimal(value));	
    		}
    		
    		return expenseDto;
    }
    
}
