package com.koray.currency.mapper;

import com.koray.currency.model.Currency;
import com.koray.currency.util.CurrencyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrencyMapper {

    Logger logger = LoggerFactory.getLogger(CurrencyMapper.class);
    public List<Currency> currencyResponseToCurrencyList(CurrencyResponse currencyResponse) {
        List<Currency> currencyList = new ArrayList<>();
        logger.info("currencyResponseToCurrencyList started.");
        int amountOfCurrencies = currencyResponse.getConversion_rates().size();

        for(Map.Entry<String,Float> pair : currencyResponse.getConversion_rates().entrySet()) {
            Currency currency = new Currency();
            logger.info("CurrencyResponse is mapping to currency object for " + pair.getKey() + " currency.");
            currency.setCurrencyCode(pair.getKey());
            currency.setCurrencyValue(pair.getValue());
            currency.setTimeNextUpdateUtc(currencyResponse.getTime_next_update_utc());
            currency.setTimeLastUpdateUtc(currencyResponse.getTime_last_update_utc());
            currency.setBaseCode(currencyResponse.getBase_code());
            currencyList.add(currency);
        }
        return currencyList;
    }
}
