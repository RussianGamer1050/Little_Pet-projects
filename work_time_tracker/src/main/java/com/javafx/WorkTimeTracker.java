package com.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WorkTimeTracker extends Application {
    
    @Override
    public void start(Stage stage) {

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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
