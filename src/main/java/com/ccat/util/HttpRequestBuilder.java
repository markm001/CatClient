package com.ccat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpRequest;

/**
 * Creates an HttpRequest Object from the provided annotation data
 */
public class HttpRequestBuilder {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestBuilder.class);

    public static HttpRequest build(String auth, String method, URI uri, String body) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", auth)
                .header("Content-Type", "application/json");

        setRequestMethod(method, body, requestBuilder);

        logger.debug("Constructing {}-Request with body {} and header {}", method, body, auth);

        return requestBuilder.build();
    }

    private static void setRequestMethod(String method, String body, HttpRequest.Builder requestBuilder) {
        switch (method.toUpperCase()) {
            case "GET" -> requestBuilder.GET();
            case "POST" -> requestBuilder.POST(body != null
                    ? HttpRequest.BodyPublishers.ofString(body)
                    : HttpRequest.BodyPublishers.noBody());
            case "PUT" -> requestBuilder.PUT(body != null
                    ? HttpRequest.BodyPublishers.ofString(body)
                    : HttpRequest.BodyPublishers.noBody());
            case "PATCH", "UPDATE" -> requestBuilder.method("PATCH", body != null
                    ? HttpRequest.BodyPublishers.ofString(body)
                    : HttpRequest.BodyPublishers.noBody());
            case "DELETE" -> requestBuilder.DELETE();
            case null, default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }
}
