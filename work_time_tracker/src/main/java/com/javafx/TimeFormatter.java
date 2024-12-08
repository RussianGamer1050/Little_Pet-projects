package com.javafx;

public class TimeFormatter {
    
    // Format milliseconds to h:m:s:m format
    public String formatTime(long time) {
        // Separated values for timer
        int milliseconds = (int) (time % 1000) / 10;
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) (time / 60000) % 60;
        int hours = (int) time / 360000;

        return String.format("%02d:%02d:%02d,%02d", hours, minutes, seconds, milliseconds);
    }

    // Format milliseconds to 1h 1minutes format
    public String formatToHourMinute(long milliseconds) {
        long totalMinutes = milliseconds / 60000; // Convert to total minutes
        int hours = (int) totalMinutes / 60;
        int minutes = (int) totalMinutes % 60;
        
        if (hours == 0) return String.format("%dminutes", minutes);
        return String.format("%dh %dminutes", hours, minutes);
    }
}
