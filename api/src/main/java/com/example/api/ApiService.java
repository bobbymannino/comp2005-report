package com.example.api;

public class ApiService {

    private static String BASE =
        "https://web.socem.plymouth.ac.uk/COMP2005/api";

    public static String get(String path) {
        try {
            java.net.URL url = new java.net.URL(BASE + path);
            java.net.HttpURLConnection conn =
                (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (
                java.io.BufferedReader in = new java.io.BufferedReader(
                    new java.io.InputStreamReader(conn.getInputStream())
                )
            ) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new Error("Failed to get employees");
    }
}
