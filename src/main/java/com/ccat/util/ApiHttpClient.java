package com.ccat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiHttpClient.class);

    public static HttpResponse<String> getHttpResponse(HttpRequest request) {
        try (java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient()) {
            logger.debug("Sending {}-Request to URI: {}", request.method(), request.uri().toString());

            return client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            logger.warn("{} - Unable to process request. - {}", e.getClass(), e.getMessage());
            throw new RuntimeException("Request failed: " + e.getMessage(), e);
        }
    }
}
