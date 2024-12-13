package com.crypto.ui;

import java.util.List;

import com.crypto.domain.Crypto;
import com.crypto.domain.GetCryptoUseCase;
import com.google.inject.Inject;

/*
 * View Model to represent crypto rates
 */

public class CryptoViewModel {
    
    private final GetCryptoUseCase getCryptoUseCase;
    private List<Crypto> cryptoRates;

    @Inject
    public CryptoViewModel(GetCryptoUseCase getCryptoUseCase) {
        this.getCryptoUseCase = getCryptoUseCase;
        loadCryptoRates();
    }

    private void loadCryptoRates() {
        cryptoRates = getCryptoUseCase.getCryptoRates();
        System.out.println("Loaded crypto rates: " + cryptoRates);
    }

    public List<Crypto> getCryptoRates() {
        return cryptoRates;
    }

    public void setCryptoRates(List<Crypto> cryptoRates) {
        this.cryptoRates = cryptoRates;
    }

    public void updateCryptoRates() {
        cryptoRates = getCryptoUseCase.getCryptoRates();
        System.out.println("Updated crypto rates: " + cryptoRates);
    }

}
