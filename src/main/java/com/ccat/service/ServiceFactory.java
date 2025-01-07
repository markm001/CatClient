package com.ccat.service;

import com.ccat.annotation.AnnotationData;
import com.ccat.annotation.AnnotationParser;
import com.ccat.executor.Call;
import com.ccat.response.Response;
import com.ccat.util.HttpRequestBuilder;

import java.lang.reflect.Proxy;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static com.ccat.util.ApiHttpClient.getHttpResponse;

/***
 * Proxy Method
 */
public class ServiceFactory {
    private final String baseUrl;

    /**
     * Set the baseUrl for the AnnotationParser to use.
     * @param baseUrl String URL address you want to send the request to
     */
    public ServiceFactory(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    @SuppressWarnings({"unchecked"})
    public <T> T createService(Map<String,String> headers, Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass},
                (proxy, method, args) -> {
                    AnnotationData annotationData = AnnotationParser.parseAnnotationData(method, baseUrl, args);

                    return new Call<>(() -> {
                        HttpRequest request = HttpRequestBuilder.build(
                                headers,
                                annotationData.httpMethod(),
                                annotationData.uriTemplate(),
                                annotationData.requestBody().orElse(null));

                        HttpResponse<String> response = getHttpResponse(request);

                        return new Response<>(
                                response.statusCode(),
                                response.headers().map(),
                                response.body()
                        );
                    }
                    );
                });
    }
}
