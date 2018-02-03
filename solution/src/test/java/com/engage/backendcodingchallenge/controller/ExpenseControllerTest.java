package com.engage.backendcodingchallenge.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;

import com.engage.backendcodingchallenge.dto.CurrencyRateDto;
import com.engage.backendcodingchallenge.model.Expense;
import com.engage.backendcodingchallenge.service.ExchangerateApiService;
import com.engage.backendcodingchallenge.service.ExpenseService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ExpenseController.class, secure = false)
@AutoConfigureWebClient
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @MockBean
    private ExchangerateApiService exchangeApiService;

    @Autowired
    private ResourceLoader resourceLoader;

    private List<Expense> mockExpenses;
    private Expense mockExpense;
    private Date date;

    @Before
    public void init() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date = formatter.parse("27/02/2017");

        mockExpenses = new ArrayList<>(
                Arrays.asList(
                        new Expense(1, date, BigDecimal.valueOf(12.45), "Dinner"),
                        new Expense(2, date, BigDecimal.valueOf(5.99), "Pharmacy"),
                        new Expense(3, date, BigDecimal.valueOf(678.68), "House")
                        ));
        mockExpense = new Expense(1, date, BigDecimal.valueOf(12.45), "Dinner");
    }

    @Test
    public void getAll() throws Exception {
        Mockito.when(expenseService.findAll()).thenReturn(mockExpenses);

        RequestBuilder requestBuilder = 
                MockMvcRequestBuilders.get("/app/expenses").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String expected = readJsonFile("test_getAll_response.json");;

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
        String expected = readJsonFile("test_get_response.json");

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void saveGBP() throws Exception {
        Expense newExpenseMock = new Expense();
        newExpenseMock.setId(4);
        newExpenseMock.setDate(date);
        newExpenseMock.setValue(BigDecimal.valueOf(1.87));
        newExpenseMock.setReason("Coffe");

        Mockito.when(expenseService.save(Mockito.any(Expense.class))).thenReturn(newExpenseMock);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/app/expenses")
                .accept(MediaType.APPLICATION_JSON).content(readJsonFile("test_saveGBP_request.json"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String expected = readJsonFile("test_saveGBP_response.json");

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void saveEUR() throws Exception {
        Expense newExpenseMock = new Expense();
        newExpenseMock.setId(4);
        newExpenseMock.setDate(date);
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
                .accept(MediaType.APPLICATION_JSON).content(readJsonFile("test_saveEUR_request.json"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String expected = readJsonFile("test_saveEUR_response.json");

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    private String readJsonFile(String filename) throws IOException {
        try (InputStream is = resourceLoader.getResource("classpath:test/"+filename).getInputStream()) {
            return StreamUtils.copyToString(is, Charset.defaultCharset());
        }
    }

}
