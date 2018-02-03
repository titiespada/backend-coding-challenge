package com.engage.backendcodingchallenge.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engage.backendcodingchallenge.dto.CurrencyRateDto;
import com.engage.backendcodingchallenge.dto.ExpenseDto;
import com.engage.backendcodingchallenge.exception.FormValidationException;
import com.engage.backendcodingchallenge.model.Expense;
import com.engage.backendcodingchallenge.service.ExchangerateApiService;
import com.engage.backendcodingchallenge.service.ExpenseService;
import com.engage.backendcodingchallenge.util.ExpenseUtil;

@RestController
@RequestMapping(path = "/app/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseController.class);
	
	private static final String GBP_CURRENCY = "GBP";
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private ExchangerateApiService exchangeApiService;

	@GetMapping
	public ResponseEntity<List<ExpenseDto>> getAll() {
		LOGGER.debug("Search all expenses");
		
		List<Expense> expenses = expenseService.findAll();
		List<ExpenseDto> expensesDto = expenses.stream()
				.map(ExpenseUtil::createDto)
				.collect(Collectors.toList());

		LOGGER.debug("Returning {} expenses", expensesDto.size());
		return new ResponseEntity<>(expensesDto, HttpStatus.OK);
	}
    
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> get(@PathVariable(value = "id", required = true) Integer id) {
    		LOGGER.debug("Search for expense with id {}", id);
    		
    		Expense expense = expenseService.findById(id);
    		ExpenseDto expenseDto = ExpenseUtil.createDto(expense);
    		
    		LOGGER.debug("Returning expense with id {}: {}", id, expenseDto);
        return new ResponseEntity<>(expenseDto, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ExpenseDto> save(@Valid @RequestBody(required = true) ExpenseDto expenseDto, BindingResult bindingResult) {
    		LOGGER.debug("Save expense: {}", expenseDto);
    		
    		if (bindingResult.hasErrors()) {
    		    StringBuilder errorMessage = new StringBuilder();
    		    bindingResult.getAllErrors().stream().forEach(e -> {
    		        if (e instanceof FieldError) {
    		            errorMessage.append(((FieldError) e).getField()).append(": ");
    		        } else {
    		            errorMessage.append(e.getObjectName()).append(": ");
    		        }
    		        errorMessage.append(e.getDefaultMessage()).append("\n");
    		    });
    		    throw new FormValidationException(errorMessage.toString());
    		}
    		
    		Expense expense  = expenseService.save(
    		        ExpenseUtil.createModel(parseValueWithCurrency(expenseDto))
    		        );
    		
    		expenseDto = ExpenseUtil.createDto(expense);
    		
    		LOGGER.debug("Returning saved expense: {}", expenseDto);
        return new ResponseEntity<>(expenseDto, HttpStatus.CREATED);
    }
    
    private ExpenseDto parseValueWithCurrency(ExpenseDto expenseDto) {
    		if (expenseDto.getCurrency() == null) {
    		    expenseDto.setGbpValue(expenseDto.getValue());
    		} else {
            CurrencyRateDto currencyRateDto = exchangeApiService.callRestService(expenseDto.getCurrency(), GBP_CURRENCY);
            expenseDto.setGbpValue(ExpenseUtil.applyRate(expenseDto.getValue(), currencyRateDto.getRate()));
    		}
    		
    		return expenseDto;
    }
    
}
