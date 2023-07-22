package com.koray.currency.service;

import com.koray.currency.mapper.CurrencyMapper;
import com.koray.currency.model.Currency;
import com.koray.currency.repository.CurrencyRepository;
import com.koray.currency.util.CurrencyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    private final APICallService apiCallService;
    private final CurrencyRepository currencyRepository;

    CurrencyMapper currencyMapper = new CurrencyMapper();

    public CurrencyServiceImpl(APICallService apiCallService, CurrencyRepository currencyRepository) {
        this.apiCallService = apiCallService;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> getCurrencyList(Boolean isReverse) {
        logger.info("getCurrencyList started");
        List<Currency> currencies = currencyRepository.findAll();

        if(currencies.size() == 0) {
            logger.info("Currencies list size is 0.");
            CurrencyResponse currencyResponse = apiCallService.getCurrencyListFromApi();
            currencies = currencyMapper.currencyResponseToCurrencyList(currencyResponse);
            currencyRepository.saveAll(currencies);
            currencies = currencyRepository.findAll();
            if(isReverse) {
                return currenciesToTry(currencies);
            }
            return currencies;
        }
        else {
            if(isDateInThePast(currencies.get(0).getTimeNextUpdateUtc())) {
                logger.info("Currencies update time has came.");
                CurrencyResponse currencyResponse = apiCallService.getCurrencyListFromApi();
                List<Currency> newCurrencies = currencyMapper.currencyResponseToCurrencyList(currencyResponse);
                currencyRepository.saveAll(newCurrencies);
                currencies = currencyRepository.findAll();
                if(isReverse) {
                    return currenciesToTry(currencies);
                }
                return currencies;
            }
            else {
                logger.info("Update date has not come and list size is not 0, so returning from directly database.");
                if(isReverse) {
                    return currenciesToTry(currencies);
                }
                return currencies;
            }
        }
    }

    private List<Currency> currenciesToTry(List<Currency> currencies) {
        logger.info("Currency values are reversing.");
        for(Currency currency : currencies) {
            currency.setCurrencyValue(1/currency.getCurrencyValue()); // 1 dolar 30 tl -- 1 tl 0.
        }
        return currencies;
    }

    @Override
    public List<Currency> refreshCurrencyList() {
        CurrencyResponse currencyResponse = apiCallService.getCurrencyListFromApi();
        List<Currency> currencies = currencyMapper.currencyResponseToCurrencyList(currencyResponse);
        currencies = currencyRepository.saveAll(currencies);
        return currencies;
    }

    private boolean isDateInThePast(Date timeNextUpdateUtc) {
        Date timeNextUpdateDate = parseDateString(timeNextUpdateUtc);

        if(timeNextUpdateDate.before(new Date())) {
            return true;
        }
        return false;
    }

    private Date parseDateString(Date timeNextUpdateUtc) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String date = sdf.format(timeNextUpdateUtc);
            return sdf.parse(date);
        }
        catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }
}
