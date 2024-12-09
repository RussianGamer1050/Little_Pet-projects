package com.crypto.domain;

import java.util.List;

import com.crypto.data.CryptoApi;
import com.google.inject.Inject;

/*
 * Class which interact with API to get data
*/

public class GetCryptoUseCase {
    
    private final CryptoApi cryptoApi;

    @Inject
    public GetCryptoUseCase(CryptoApi cryptoApi) {
        this.cryptoApi = cryptoApi;
    }

    public List<Crypto> getCryptoRates() {
        return cryptoApi.fetchCryptoRates();
    }
    
}
