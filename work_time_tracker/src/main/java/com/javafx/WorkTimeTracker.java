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
    private long totalWorkingTime;
    private long workingTime;
    private long totalBreakTime;
    private long breakTime;
    
    // Status flags
    private boolean isWorking = false;
    private boolean isOnBreak = false;
    
    // Labels for display
    private Label totalWorkingTimeLabel = new Label("Total Working Time: 00:00:00,00");
    private Label workingTimeLabel = new Label("Working Time: 00:00:00,00");
    private Label totalBreakTimeLabel = new Label("Total Break Time: 00:00:00,00");
    private Label breakTimeLabel = new Label("Break Time: 00:00:00,00");
    
    @Override
    public void start(Stage stage) {

        // Interface elements
        VBox workingLabelsVBox = new VBox(5, totalWorkingTimeLabel, workingTimeLabel);
        VBox breakLabelsVBox = new VBox(5, totalBreakTimeLabel, breakTimeLabel);
        VBox labelsVBox = new VBox(15, workingLabelsVBox, breakLabelsVBox);

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
        Scene scene = new Scene(root, 225, 300);
        stage.setTitle("Work Time Tracker");
        stage.setScene(scene);
        stage.show();

        // Button actions
        // Start button action
        startWorkButton.setOnAction(event -> {
            // Can be pressed only if not started or ended
            if (!isWorking && !isOnBreak) {
                // Set all timers to zero
                totalWorkingTime = 0;
                totalBreakTime = 0;

                workingStartTime = System.currentTimeMillis();
                isWorking = true;
                isOnBreak = false;
            }
        });

        // Take break button action
        takeBreakButton.setOnAction(event -> {
            // Break if working and not on break
            if (isWorking && !isOnBreak) {
                totalWorkingTime += workingTime;

                breakStartTime = System.currentTimeMillis();
                isWorking = false;
                isOnBreak = true;
            }
        });

        // Continue working button action
        continueWorkingButton.setOnAction(event -> {
            // Continue if on break and not working
            if (isOnBreak && !isWorking) {
                totalBreakTime += breakTime;

                workingStartTime = System.currentTimeMillis();
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
            workingTimeLabel.setText("Working Time: " + formatTime(workingTime));

            totalWorkingTimeLabel.setText("Total Working Time: " + formatTime(totalWorkingTime + workingTime));

        }
        else if (isOnBreak) {
            breakTime = System.currentTimeMillis() - breakStartTime;
            breakTimeLabel.setText("Break Time: " + formatTime(breakTime));

            totalBreakTimeLabel.setText("Total Break Time: " + formatTime(totalBreakTime + breakTime));
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
