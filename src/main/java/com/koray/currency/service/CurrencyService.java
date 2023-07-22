package com.koray.currency.service;

import com.koray.currency.model.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> getCurrencyList();

    List<Currency> refreshCurrencyList();
}
