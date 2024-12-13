package com.crypto.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.google.inject.Inject;

import javafx.application.Platform;

public class CryptoUpdater {

    private final Timer timer;
    private CryptoView cryptoView;

    @Inject
    public CryptoUpdater(CryptoViewModel viewModel) {
        
        this.timer = new Timer(true);
        this.cryptoView = new CryptoView(viewModel);
    }

    public CryptoView getCryptoView() {
        return cryptoView;
    }

    public void startUpdating() {

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> cryptoView.updateCryptoRates());
            }
        }, 0, 35000); // API update rate around 35 sec.
    }

    public void stopUpdating() {
        timer.cancel();
    }

}
