package com.crypto.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * Work with API Util
 */

public class JsonUtils {
    
    public static String getCryptoDataFromApi(String apiURL) {
        try {
            // Make API connection
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Check answer code
            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                System.out.println("Failed to fetch data: HTTP " + responseCode);
                return null;
            }

            // Read API data
            StringBuilder result = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } catch (IOException e) {
                System.err.println("Failed to read data: " + e.getMessage());
            }
        
            return result.toString();

        } catch (IOException e) {
            System.err.println("Error during API request: " + e.getMessage());
        }

        return null;
    }

}
