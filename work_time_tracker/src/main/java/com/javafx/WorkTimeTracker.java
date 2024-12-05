package com.javafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    // db variables
    private String url = "jdbc:sqlite:work_time_tracker/work_time_tracker.db";

    // Timer variables
    // Start times
    private long workingStartTime;
    private long breakStartTime;

    // Actual time
    private long totalWorkingTime;
    private long workingTime;
    private long totalBreakTime;
    private long breakTime;

    // Sessions counter
    // private int sessionsCounter;

    // // Min max variables
    // private long minWorkTime;
    // private long maxWorkTime;
    // private long minBreakTime;
    // private long maxBreakTime;
    
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

        // Initializing database
        initializeDataBase();

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
                setTimerOnDefault();

                workingStartTime = System.currentTimeMillis();
                isWorking = true;
                isOnBreak = false;

                // sessionsCounter += 1;
            }
        });

        // Take break button action
        takeBreakButton.setOnAction(event -> {
            // Break if working and not on break
            if (isWorking && !isOnBreak) {
                totalWorkingTime += workingTime;
                // maxWorkTime = maxTimeChecker(workingTime, maxWorkTime);

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

                // sessionsCounter += 1;
            }
        });

        // End work button action
        endWorkButton.setOnAction(event -> {
            // You can end work only while working
            if (isWorking) {
                totalWorkingTime += workingTime;

                isWorking = false;
                isOnBreak = false;
    
                // [dbg]
                saveTimeToDatabase();
                viewDatabase();
            }
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

    // Min time checker
    // private long minTimeChecker(long time, long minTime) {
    //     if (time < minTime) return time;

    //     return minTime;
    // }

    // // Max time checker
    // private long maxTimeChecker(long time, long maxTime) {
    //     if (time > maxTime) return time;

    //     return maxTime;
    // }

    // Set timer on default
    private void setTimerOnDefault() {
        // Set variables on zero
        totalWorkingTime = 0;
        workingTime = 0;
        totalBreakTime = 0;
        breakTime = 0;

        // sessionsCounter = 0;

        // minWorkTime = 0;
        // maxWorkTime = 0;
        // minBreakTime = 0;
        // maxBreakTime = 0;

        // Update labels
        workingTimeLabel.setText("Working Time: " + formatTime(workingTime));
        totalWorkingTimeLabel.setText("Total Working Time: " + formatTime(totalWorkingTime));
        breakTimeLabel.setText("Break Time: " + formatTime(breakTime));
        totalBreakTimeLabel.setText("Total Break Time: " + formatTime(totalBreakTime));
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

    // Function that format milliseconds to 1h 1minutes format

    // Time parser (h:m:s:m)
    private long parseTime(String time) {
        String[] parts = time.split("[:,]");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        int milliseconds = Integer.parseInt(parts[3]);

        return hours * 3600000L + minutes * 60000L + seconds * 1000L + milliseconds * 10L;
    }

    // Initializing database
    private void initializeDataBase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS time_log (" +
                                    "Date TEXT PRIMARY KEY, " +
                                    "Sessions TEXT," +
                                    "WorkTime TEXT," +
                                    "BreakTime TEXT," +
                                    "AvgWorkTime TEXT," +
                                    "AvgBreakTime TEXT," +
                                    "MinWorkTime TEXT," +
                                    "MaxWorkTime TEXT," +
                                    "MinBreakTime TEXT," +
                                    "MaxBreakTime TEXT)";
            try (PreparedStatement pstmt = conn.prepareStatement(createTableSQL)) {
                pstmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add info to database
    private void saveTimeToDatabase() {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        
        // int savSessions = 0;
        long savWorkTime = 0;
        long savBreakTime = 0;
        // long savAvgWorkTime = 0;
        // long savAvgBreakTime = 0;
        // long savMinWorkTime = 0;
        // long savMaxWorkTime = 0;
        // long savMinBreakTime = 0;
        // long savMaxBreakTime = 0;
    
        // Check if there is an entry for the current date
        String selectSQL = "SELECT WorkTime, BreakTime FROM time_log WHERE Date = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
    
            selectStmt.setString(1, date);
            var rs = selectStmt.executeQuery();
    
            if (rs.next()) {
                savWorkTime = parseTime(rs.getString("WorkTime"));
                savBreakTime = parseTime(rs.getString("BreakTime"));
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // Sum the new times with the existing ones
        long newTotalWorkTime = savWorkTime + totalWorkingTime;
        long newTotalBreakTime = savBreakTime + totalBreakTime;
    
        // Save the updated times to the database
        String insertSQL = "INSERT OR REPLACE INTO time_log (Date, WorkTime, BreakTime) " +
                           "VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
    
            insertStmt.setString(1, date);
            insertStmt.setString(2, formatTime(newTotalWorkTime));
            insertStmt.setString(3, formatTime(newTotalBreakTime));
            insertStmt.executeUpdate();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Print info from database
    private void viewDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            String selectSQL = "SELECT * FROM time_log";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
                var rs = selectStmt.executeQuery();
                while (rs.next()) {
                    String date = rs.getString("Date");
                    String workTime = rs.getString("WorkTime");
                    String breakTime = rs.getString("BreakTime");
                    System.out.println("Date: " + date + ", Work Time: " + workTime + ", Break Time: " + breakTime); // [dbg]
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
