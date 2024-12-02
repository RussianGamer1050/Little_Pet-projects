package com.javafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WorkTimeTracker extends Application {
    
    // Status flags
    private boolean isWorking = false;
    private boolean isOnBreak = false;

    private void update() {
        System.out.println("isWorking: " + isWorking + " | isOnBreak: " + isOnBreak);
    }

    @Override
    public void start(Stage stage) {

        // Interface elements
        // Create labels for display
        Label workingTimeLabel = new Label("Total Working Time: 00:00:00");
        Label breakTimeLabel = new Label("Break Time: 00:00:00");
        VBox labelsVBox = new VBox(15, workingTimeLabel, breakTimeLabel);

        // Buttons to control the application
        Button startWorkButton = new Button("Start Work");
        Button takeBreakButton = new Button("Take Break");
        Button continueWorkingButton = new Button("Continue Working");
        Button endWorkButton = new Button("End Work");
        VBox buttonsVBox = new VBox(10, startWorkButton, takeBreakButton, continueWorkingButton, endWorkButton);

        // Layout setup
        VBox root = new VBox(30, labelsVBox, buttonsVBox);
        root.setPadding(new Insets(20));

        // Scene and stage setup
        Scene scene = new Scene(root, 200, 270);
        stage.setTitle("Work Time Tracker");
        stage.setScene(scene);
        stage.show();

        // Button actions
        // Start button action
        startWorkButton.setOnAction(event -> {
            isWorking = true;
        });

        // Take break button action
        takeBreakButton.setOnAction(event -> {
            isWorking = false;
            isOnBreak = true;
        });

        // Continue working button action
        continueWorkingButton.setOnAction(event -> {
            isOnBreak = false;
            isWorking = true;
        });

        // End work button action
        endWorkButton.setOnAction(event -> {
            isWorking = false;
            isOnBreak = false;
        });

        // Timeline which update time every millisecond
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1), event -> update());
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
