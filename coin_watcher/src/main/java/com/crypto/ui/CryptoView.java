package com.crypto.ui;

import java.util.ArrayList;
import java.util.List;

import com.crypto.domain.Crypto;
import com.crypto.domain.Crypto.Trend;
import com.google.inject.Inject;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/*
 * Class which contains cryptocurrency cards
 */

public class CryptoView extends ScrollPane {
    
    private CryptoViewModel viewModel;
    private final VBox root;

    @Inject
    public CryptoView(CryptoViewModel viewModel) {

        this.viewModel = viewModel;

        root = new VBox(10);
        root.setPadding(new Insets(20));

        // Get cryptoRates from viewModel and display it
        getCryptoRates();

        // Set size limit
        this.setContent(root);
        this.setFitToWidth(true);
        this.setMaxWidth(600);
        this.setMaxHeight(600);

        // Set scroll if too many content
        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    // Bad name (maybe) [dbg]
    private void getCryptoRates() {

        List<Crypto> cryptoRates = viewModel.getCryptoRates();

        if (cryptoRates != null && !cryptoRates.isEmpty()) {
            for (Crypto crypto : cryptoRates) {
                root.getChildren().add(createCryptoCard(crypto));
            }
        } else {
            root.getChildren().add(new Text("No data avaible"));
        }
    }

    public void updateCryptoRates() {
        // Clear all content before update
        root.getChildren().clear();

        // List of old cryptoRates
        
        List<Crypto> oldCryptoRates = new ArrayList<>();
        oldCryptoRates = viewModel.getCryptoRates();
        
        viewModel.updateCryptoRates();

        checkTrend(oldCryptoRates);

        getCryptoRates();
    }

    private void checkTrend(List<Crypto> oldCryptoRates) {

        List<Crypto> cryptoRates = viewModel.getCryptoRates();

        if (cryptoRates != null && !cryptoRates.isEmpty()) {
            for (int i = 0; i < cryptoRates.size(); i++) {
                if (oldCryptoRates.get(i).getPriceUsd() < cryptoRates.get(i).getPriceUsd()) {
                    cryptoRates.get(i).setTrend(Trend.UP);
                }
                else if (oldCryptoRates.get(i).getPriceUsd() > cryptoRates.get(i).getPriceUsd()) {
                    cryptoRates.get(i).setTrend(Trend.DOWN);
                }
            }
        } else {
            root.getChildren().add(new Text("No data avaible"));
        }

        viewModel.setCryptoRates(cryptoRates);
    }

    private HBox createCryptoCard(Crypto crypto) {
        // Create card
        HBox card = new HBox(20);
        card.setPadding(new Insets(20));
        card.setSpacing(10);
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        card.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));

        // Add shadow
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        dropShadow.setRadius(5);
        card.setEffect(dropShadow);

        // Add cryptocurrency name
        Text name = new Text(crypto.getName());
        name.setFont(Font.font("Arial", 18));
        name.setFill(Color.BLACK);

        // Add cryptocurrency price
        Text price = new Text(String.format("%.2f USD", crypto.getPriceUsd()));
        price.setFont(Font.font("Arial", 16));
        switch (crypto.getTrend()) {
            case UP:
                price.setFill(Color.DARKGREEN);
                break;
            case DOWN:
                price.setFill(Color.DARKRED);
                break;
            default:
                price.setFill(Color.GRAY);
        }

        card.getChildren().addAll(name, price);

        return card;
    }

}
