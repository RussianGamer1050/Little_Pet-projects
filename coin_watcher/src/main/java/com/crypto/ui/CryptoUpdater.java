package com.crypto.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.google.inject.Inject;

import javafx.application.Platform;
import javafx.scene.Group;

public class CryptoUpdater extends Group {

    private final Timer timer;
    private CryptoView cryptoView;

    @Inject
    public CryptoUpdater(CryptoViewModel viewModel) {
        
        this.cryptoView = new CryptoView(viewModel);
        this.getChildren().add(cryptoView);

        this.timer = new Timer(true); // Timer running as daemon
    }

    public void startUpdating() {

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> cryptoView.updateCryptoRates());
            }
        }, 0, 10000); // Schedule task with 10-second intervals
    }

    public void stopUpdating() {
        timer.cancel();
    }

}
