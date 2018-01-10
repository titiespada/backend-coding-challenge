package com.engage.backendcodingchallenge.service;

import com.engage.backendcodingchallenge.dto.CurrencyRateDto;

public interface ExchangerateApiService {
	
	CurrencyRateDto callRestService(String from, String to);

}
