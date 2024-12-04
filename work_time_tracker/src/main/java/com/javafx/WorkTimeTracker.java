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

    // Timer variables
    // Start times
    private long workingStartTime;
    private long breakStartTime;

    // Actual time
    private long workingTime = 0;
    
    // Status flags
    private boolean isWorking = false;
    private boolean isOnBreak = false;
    
    // Create labels for display
    private Label workingTimeLabel = new Label("Total Working Time: 00:00:00,00");
    private Label breakTimeLabel = new Label("Break Time: 00:00:00,00");
    
    @Override
    public void start(Stage stage) {

        // Interface elements
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
        Scene scene = new Scene(root, 210, 270);
        stage.setTitle("Work Time Tracker");
        stage.setScene(scene);
        stage.show();

        // Button actions
        // Start button action
        startWorkButton.setOnAction(event -> {
            workingStartTime = System.currentTimeMillis();
            isWorking = true;
            isOnBreak = false;
        });

        // Take break button action
        takeBreakButton.setOnAction(event -> {
            if (isWorking && !isOnBreak) {
                breakStartTime = System.currentTimeMillis();
                isWorking = false;
                isOnBreak = true;
            }
        });

        // Continue working button action
        continueWorkingButton.setOnAction(event -> {
            if (isOnBreak && !isWorking) {
                workingStartTime += System.currentTimeMillis() - breakStartTime;
                isOnBreak = false;
                isWorking = true;
            }
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

    // Update function, which executes 100 times per second
    private void update() {
        if (isWorking) {
            workingTime = System.currentTimeMillis() - workingStartTime;
            workingTimeLabel.setText("Total Working Time: " + formatTime(workingTime));
        }
    }

    // Function that format milliseconds to h:m:s:m format
    private String formatTime(long time) {
        // Separated values for timer
        int milliseconds = (int) (time % 1000) / 10;
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) (time / 60000) % 60;
        int hours = (int) time / 360000;

        return String.format("%02d:%02d:%02d,%02d", hours, minutes, seconds, milliseconds);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
