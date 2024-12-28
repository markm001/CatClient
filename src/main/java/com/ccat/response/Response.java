package com.ccat.response;

import java.util.List;
import java.util.Map;

/**
 * Response containing the HTTP-Code, headers & response body
 * @param statusCode
 * @param headers
 * @param body
 * @param <T> Response-Type
 */
public record Response<T> (
    int statusCode,
    Map<String, List<String>> headers,
    T body
){ }
