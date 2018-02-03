package com.engage.backendcodingchallenge.service;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.engage.backendcodingchallenge.dto.CurrencyRateDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@RestClientTest(ExchangerateApiServiceImpl.class)
public class ExchangerateApiServiceImplTest {

    @Autowired
    private ExchangerateApiService exchangerateApiService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        String currencyRateStr = 
                objectMapper.writeValueAsString(new CurrencyRateDto("success", 1517618436, "EUR", "GBP", Double.valueOf(0.87981323)));

        this.server.expect(requestTo("https://v3.exchangerate-api.com/pair/680783f86b171dffac7b9f4e/EUR/GBP"))
        .andRespond(withSuccess(currencyRateStr, MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenCallingGetCurrencyRate_thenClientMakesCorrectCall() 
            throws Exception {

        CurrencyRateDto currencyRate = this.exchangerateApiService.callRestService("EUR", "GBP");

        assertEquals("EUR", currencyRate.getFrom());
        assertEquals("GBP", currencyRate.getTo());
        assertEquals("success", currencyRate.getResult());
        assertEquals(Double.valueOf(0.87981323), currencyRate.getRate());
    }

}
