package com.crypto.app;

import com.crypto.data.CryptoApi;
import com.crypto.data.CryptoApiImpl;
import com.crypto.domain.GetCryptoUseCase;
import com.crypto.ui.CryptoView;
import com.crypto.ui.CryptoViewModel;
import com.google.inject.AbstractModule;

/*
 * Class wich confidure dependencies
 */

public class CryptoModule extends AbstractModule {
    
    @Override
    protected void configure() {
        bind(CryptoApi.class).to(CryptoApiImpl.class);
        bind(GetCryptoUseCase.class);
        bind(CryptoViewModel.class);
        bind(CryptoView.class);
    }

}
