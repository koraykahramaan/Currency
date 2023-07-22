package com.koray.currency.controller;

import com.koray.currency.model.Currency;
import com.koray.currency.service.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public String getCurrencyList(Model model) {
        model.addAttribute("currencies",currencyService.getCurrencyList(false));
        return "currencies";
    }

    @GetMapping("/refresh")
    public String refreshCurrencyList(Model model) {
        model.addAttribute("currencies",currencyService.refreshCurrencyList());
        return "currencies";
    }
    @GetMapping("/currencyToTry")
    public String currencyToTry(Model model) {
        model.addAttribute("currencies",currencyService.getCurrencyList(true));
        return "currencies_toTry";
    }
}
