package com.example.comp2005_report;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public static ResponseEntity<ObjectNode> httpErrorResponse() {
        HttpErrorResponse error = new HttpErrorResponse(500, "The API is not working at the moment, please try again later.");

        return new ResponseEntity<>(error.toObjectNode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
