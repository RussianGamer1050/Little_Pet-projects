package com.javafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseManager {

    // Time formatter
    private TimeFormatter timeFormatter = new TimeFormatter();

    private final String url;

    public DatabaseManager(String databaseUrl) {
        this.url = databaseUrl;
        initializeDataBase();
    }

    // Initialize database and create table if it doesn't exist
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

    // Save or update time log in the database
    public void saveTimeToDatabase(long totalWorkingTime, long totalBreakTime) {
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
                savWorkTime = parseHourMinute(rs.getString("WorkTime"));
                savBreakTime = parseHourMinute(rs.getString("BreakTime"));
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
            insertStmt.setString(2, timeFormatter.formatToHourMinute(newTotalWorkTime));
            insertStmt.setString(3, timeFormatter.formatToHourMinute(newTotalBreakTime));
            insertStmt.executeUpdate();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Print all data from the database
    public void viewDatabase() {
        String selectSQL = "SELECT * FROM time_log";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
            var rs = selectStmt.executeQuery();
            while (rs.next()) {
                String date = rs.getString("Date");
                String workTime = rs.getString("WorkTime");
                String breakTime = rs.getString("BreakTime");
                System.out.println("Date: " + date + ", Work Time: " + workTime + ", Break Time: " + breakTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to parse "1h 1minutes" format into milliseconds
    public long parseHourMinute(String time) {
        String[] parts = time.split(" ");
        long hours = 0;
        long minutes = 0;
    
        for (String part : parts) {
            if (part.endsWith("h")) {
                hours = Long.parseLong(part.substring(0, part.length() - 1)); // Remove "h" and parse
            } else if (part.endsWith("minutes")) {
                minutes = Long.parseLong(part.substring(0, part.length() - "minutes".length())); // Remove "minutes" and parse
            }
        }
        
        return (hours * 3600000L) + (minutes * 60000L);
    }
}
