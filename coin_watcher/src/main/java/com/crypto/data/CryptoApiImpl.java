package com.crypto.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.crypto.domain.Crypto;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CryptoApiImpl implements CryptoApi {
    
    private static final String API_URL = "https://api.coincap.io/v2/assets";

    @Override
    public List<Crypto> fetchCryptoRates() {
        try {
            // Make API connection
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            // Check answer code
            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                System.out.println("Failed to fetch data: HTTP " + responseCode);
                return List.of();
            }

            // Read data
            StringBuilder jsonResponce = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResponce.append(line);
                }
            } catch (IOException e) {
                System.err.println("Failed to read data: " + e.getMessage());
            }

            // Parse JSON using Gson
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponce.toString(), JsonObject.class);
            String data = jsonObject.getAsJsonArray("data").toString();

            System.out.println("Data successfully uploaded " + responseCode);

            // Transform JSON into Crypto List
            return gson.fromJson(data, new TypeToken<List<Crypto>>() {}.getType());

        } catch (IOException e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }

        return List.of();
    }
    
}
