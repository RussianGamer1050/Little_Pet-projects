package com.crypto.ui;

import java.util.List;

import com.crypto.domain.Crypto;
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
    
    private final VBox root;

    @Inject
    public CryptoView(CryptoViewModel viewModel) {

        root = new VBox(10);
        root.setPadding(new Insets(20));

        // Get cryptoRates from viewModel and display it
        List<Crypto> cryptoRates = viewModel.getCryptoRates();

        if (cryptoRates != null && !cryptoRates.isEmpty()) {
            for (Crypto crypto : cryptoRates) {
                root.getChildren().add(createCryptoCard(crypto));
            }
        } else {
            root.getChildren().add(new Text("No data avaible"));
        }

        // Set size limit
        this.setContent(root);
        this.setFitToWidth(true);
        this.setMaxWidth(600);
        this.setMaxHeight(600);

        // Set scroll if too many content
        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
        price.setFill(Color.DARKGREEN);

        card.getChildren().addAll(name, price);

        return card;
    }

}
