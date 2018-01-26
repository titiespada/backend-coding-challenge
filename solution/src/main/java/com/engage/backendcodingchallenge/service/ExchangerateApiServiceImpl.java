package com.engage.backendcodingchallenge.service;

import java.util.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.engage.backendcodingchallenge.dto.CurrencyRateDto;
import com.engage.backendcodingchallenge.exception.CurrencyCodeNotSupportedException;

@Service("exchangerateApiService")
public class ExchangerateApiServiceImpl implements ExchangerateApiService {
	
	private static final String EXCHANGERATE_API_URL = "https://v3.exchangerate-api.com/pair/{key}/{from}/{to}";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${exchangerate.api.key}")
	private String exchangerateApiKey;

	@Override
	public CurrencyRateDto callRestService(String from, String to) {
	    try {
	        Currency.getInstance(from);
	    } catch (IllegalArgumentException iae) {
	        throw new CurrencyCodeNotSupportedException(String.format("The currency %s is not a valid currency code.", from));
	    }
		return restTemplate.getForObject(EXCHANGERATE_API_URL, CurrencyRateDto.class, exchangerateApiKey, from, to);
	}

}
