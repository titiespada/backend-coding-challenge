package com.engage.backendcodingchallenge.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.engage.backendcodingchallenge.dto.CurrencyRateDto;

@Service("exchangerateApiService")
public class ExchangerateApiServiceImpl implements ExchangerateApiService {
	
	private static final String EXCHANGERATE_API_URL= "https://v3.exchangerate-api.com/pair/{key}/{from}/{to}";
	
	private RestTemplate exchangerateApiRestTemplate;
	
	public ExchangerateApiServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		exchangerateApiRestTemplate = restTemplateBuilder.build();
    }
	
	@Value("${exchangerate.api.key}")
	private String exchangerateApiKey;

	@Override
	public CurrencyRateDto callRestService(String from, String to) {
		return exchangerateApiRestTemplate.getForObject(EXCHANGERATE_API_URL, CurrencyRateDto.class, exchangerateApiKey, from, to);
	}

}
