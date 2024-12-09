package com.crypto.app;

import com.crypto.ui.CryptoView;
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
        CryptoView cryptoView = injector.getInstance(CryptoView.class);

        Scene scene = new Scene(cryptoView, 650, 700);
        stage.setTitle("Coin Watcher");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
