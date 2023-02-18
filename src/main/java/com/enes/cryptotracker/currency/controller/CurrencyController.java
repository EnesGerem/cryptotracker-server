package com.enes.cryptotracker.currency.controller;

import com.enes.cryptotracker.currency.CurrencyService;
import com.enes.cryptotracker.currency.controller.dto.CreateCurrencyDTO;
import com.enes.cryptotracker.currency.controller.dto.CurrencyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public List<CurrencyDTO> getEnabledCurrencies() {
        return currencyService.retrieveEnabledCurrencies().stream().map(CurrencyDTO::new).toList();
    }

    @GetMapping("/{symbol}")
    public CurrencyDTO getEnabledCurrency(@PathVariable String symbol) {
        return new CurrencyDTO(currencyService.retrieveEnabledCurrencyBySymbol(symbol));
    }

    @PostMapping(path = "/create")
    public void createCurrency(@Valid @RequestBody CreateCurrencyDTO currencyDTO) {
        currencyService.addCurrency(currencyDTO.getDomainObject());
    }

    @PutMapping(path = "/enable/{symbol}")
    public void enableCurrency(@PathVariable String symbol) {
        currencyService.enableCurrency(symbol);
    }
}
