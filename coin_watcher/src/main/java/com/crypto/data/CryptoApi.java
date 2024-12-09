package com.crypto.data;

import java.util.List;

import com.crypto.domain.Crypto;

/*
 * Interface to get data from API
 */

public interface CryptoApi {

    List<Crypto> fetchCryptoRates();
    
}
