package com.engage.backendcodingchallenge.controller;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.engage.backendcodingchallenge.dto.CurrencyRateDto;
import com.engage.backendcodingchallenge.model.Expense;
import com.engage.backendcodingchallenge.service.ExchangerateApiService;
import com.engage.backendcodingchallenge.service.ExpenseService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ExpenseController.class, secure = false)
public class ExpenseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ExpenseService expenseService;
	
	@MockBean
	private ExchangerateApiService exchangeApiService;
	
	List<Expense> mockExpenses = new ArrayList<>(
			Arrays.asList(
					new Expense(1, LocalDate.now(), BigDecimal.valueOf(12.45), "Dinner"),
					new Expense(2, LocalDate.now(), BigDecimal.valueOf(5.99), "Pharmacy"),
					new Expense(3, LocalDate.now(), BigDecimal.valueOf(678.68), "House")
			));
	Expense mockExpense = new Expense(1, LocalDate.now(), BigDecimal.valueOf(12.45), "Dinner");
	
	@Test
	public void getAll() throws Exception {
		Mockito.when(expenseService.findAll()).thenReturn(mockExpenses);

		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get("/app/expenses").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expected = "[{\"id\":1,\"date\":\"10/01/2018\",\"vat\":2.0749999999999993,\"reason\":\"Dinner\",\"gbpValue\":12.45,\"amount\":\"12.45\"},{\"id\":2,\"date\":\"10/01/2018\",\"vat\":0.9983333333333331,\"reason\":\"Pharmacy\",\"gbpValue\":5.99,\"amount\":\"5.99\"},{\"id\":3,\"date\":\"10/01/2018\",\"vat\":113.11333333333334,\"reason\":\"House\",\"gbpValue\":678.68,\"amount\":\"678.68\"}]";

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void get() throws Exception {
		Mockito.when(expenseService.findById(Mockito.anyInt())).thenReturn(mockExpense);

		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get("/app/expenses/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expected = "{\"id\":1,\"date\":\"10/01/2018\",\"vat\":2.0749999999999993,\"reason\":\"Dinner\",\"gbpValue\":12.45,\"amount\":\"12.45\"}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void saveGBP() throws Exception {
		Expense newExpenseMock = new Expense();
		newExpenseMock.setId(4);
		newExpenseMock.setDate(LocalDate.now());
		newExpenseMock.setValue(BigDecimal.valueOf(1.87));
		newExpenseMock.setReason("Coffe");

		Mockito.when(expenseService.save(Mockito.any(Expense.class))).thenReturn(newExpenseMock);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/app/expenses")
				.accept(MediaType.APPLICATION_JSON).content("{\"date\":\"10/01/2018\",\"amount\":\"1.87\",\"reason\":\"Coffe\"}")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expected = "{\"id\":4,\"date\":\"10/01/2018\",\"vat\":0.31166666666666654,\"reason\":\"Coffe\",\"gbpValue\":1.87,\"amount\":\"1.87\"}";

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void saveEUR() throws Exception {
		Expense newExpenseMock = new Expense();
		newExpenseMock.setId(4);
		newExpenseMock.setDate(LocalDate.now());
		newExpenseMock.setValue(BigDecimal.valueOf(1.87));
		newExpenseMock.setReason("Coffe");
		
		CurrencyRateDto currencyRateMock = new CurrencyRateDto();
		currencyRateMock.setResult("success");
		currencyRateMock.setFrom("EUR");
		currencyRateMock.setTo("GBP");
		currencyRateMock.setRate(0.89);

		Mockito.when(expenseService.save(Mockito.any(Expense.class))).thenReturn(newExpenseMock);
		Mockito.when(exchangeApiService.callRestService("EUR", "GBP")).thenReturn(currencyRateMock);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/app/expenses")
				.accept(MediaType.APPLICATION_JSON).content("{\"date\":\"10/01/2018\",\"amount\":\"1.87 EUR\",\"reason\":\"Coffe\"}")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expected = "{\"id\":4,\"date\":\"10/01/2018\",\"vat\":0.31166666666666654,\"reason\":\"Coffe\",\"gbpValue\":1.87,\"amount\":\"1.87\"}";

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
}
