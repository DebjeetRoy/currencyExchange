package com.test.currencyExchange.service;

import com.test.currencyExchange.model.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyExchangeService {
    private static final String EXCHANGE_RATE_API_URL = "https://open.er-api.com/v6/latest/{base_currency}?apikey={api_key}";

    private final RestTemplate restTemplate;

    @Value("${currency.api.key}")
    private String apiKey;

    public CurrencyExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("exchangeRates")
    public double getExchangeRate(String fromCurrency, String toCurrency) {
        String url = EXCHANGE_RATE_API_URL.replace("{base_currency}", fromCurrency).replace("{api_key}", apiKey);
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

        if (response.getRates().containsKey(toCurrency)) {
            return response.getRates().get(toCurrency);
        } else {
            throw new RuntimeException("Unable to fetch exchange rates");
        }
    }
}