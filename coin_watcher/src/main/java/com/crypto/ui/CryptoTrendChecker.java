package com.crypto.ui;

import java.util.List;

import com.crypto.domain.Crypto;
import com.crypto.domain.Crypto.Trend;

public class CryptoTrendChecker {
    
    private CryptoViewModel viewModel;

    CryptoTrendChecker(CryptoViewModel viewModel) {
        
        this.viewModel = viewModel;
    }

    public boolean setTrends(List<Crypto> oldCryptoRates) {

        List<Crypto> cryptoRates = viewModel.getCryptoRates();

        if (cryptoRates.isEmpty()) {
            return false;
        }

        for (int i = 0; i < cryptoRates.size(); i++) {
            if (oldCryptoRates.get(i).getPriceUsd() < cryptoRates.get(i).getPriceUsd()) {
                cryptoRates.get(i).setTrend(Trend.UP);
            }
            else if (oldCryptoRates.get(i).getPriceUsd() > cryptoRates.get(i).getPriceUsd()) {
                cryptoRates.get(i).setTrend(Trend.DOWN);
            }
        }

        viewModel.setCryptoRates(cryptoRates);
        return true;
    }

}
