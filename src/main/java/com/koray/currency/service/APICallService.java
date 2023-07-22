package com.koray.currency.service;

import com.koray.currency.util.CurrencyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@PropertySource("classpath:token.properties")
public class APICallService {

    Logger logger = LoggerFactory.getLogger(APICallService.class);

    @Value("${token}")
    String token;

    private String URL;
    public CurrencyResponse getCurrencyListFromApi() {
        URL = "https://v6.exchangerate-api.com/v6/"+ token + "/latest/TRY";
        logger.info(URL);
        WebClient.Builder builder = WebClient.builder();

        CurrencyResponse currencyResponse = builder.build()
                .get()
                .uri(URL)
                .retrieve()
                .bodyToMono(CurrencyResponse.class).block();

        return currencyResponse;
    }
}
