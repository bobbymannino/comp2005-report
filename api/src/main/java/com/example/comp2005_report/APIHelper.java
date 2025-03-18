package com.example.comp2005_report;

public class APIHelper {

    private static String BASE =
            "https://web.socem.plymouth.ac.uk/COMP2005/api";

    public static String get(String path) throws ApiError {
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
            throw new ApiError("Failed to reach the API");
        }
    }
}
