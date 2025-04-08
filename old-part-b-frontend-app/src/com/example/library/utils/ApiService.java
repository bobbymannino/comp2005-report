package com.example.library.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiService {
    private static URL toUrl(ApiBase base, String path) throws ApiError {
        String url = base.getValue() + path;
        try {
          return new URL(url);
        } catch (MalformedURLException e) {
            throw new ApiError("Invalid URL " + url);
        }
    }

    private static HttpURLConnection connect(URL url) throws ApiError {
        try {
            return (java.net.HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new ApiError("Unable to connect to " + url.toString());
        }
    }

    private static String readBufferAsString(HttpURLConnection connection) throws ApiError {
        java.io.BufferedReader in = null;
        try {
            in = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            throw new ApiError("Unable to read response from " + connection.getURL().toString());
        }

        StringBuilder response = new StringBuilder();

        String line;

        while (true) {
            try {
                if ((line = in.readLine()) == null) break;
            } catch (IOException e) {
                throw new ApiError("Unable to read text from " + connection.getURL().toString());
            }
            response.append(line);
        }

        return response.toString();
    }

    public static String get(ApiBase base, String path) throws ApiError {
        URL url = toUrl(base, path);
        HttpURLConnection connection =  connect(url);
        return readBufferAsString(connection);
    }
}
