package com.crypto.app;

import com.crypto.ui.CryptoUpdater;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Application class
 */

public class App extends Application{
    
    @Override
    public void start(Stage stage) {

        Injector injector = Guice.createInjector(new CryptoModule());
        CryptoUpdater cryptoUpdater = injector.getInstance(CryptoUpdater.class);

        cryptoUpdater.startUpdating();

        Scene scene = new Scene(cryptoUpdater.getCryptoView(), 650, 700);
        stage.setTitle("Coin Watcher");
        stage.setScene(scene);
        stage.show();

        // Stop timer on app close
        stage.setOnCloseRequest(event -> cryptoUpdater.stopUpdating());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
