package util;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public record ResponseBuilder(
        int statusCode,
        HttpRequest request,
        HttpHeaders headers,
        String body,
        URI uri
) implements HttpResponse<String> {
    @Override
    public Optional<HttpResponse<String>> previousResponse() {
        return Optional.empty();
    }

    @Override
    public Optional<SSLSession> sslSession() {
        return Optional.empty();
    }

    @Override
    public HttpClient.Version version() {
        return null;
    }
}
