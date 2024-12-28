package com.ccat.response;

import java.util.List;
import java.util.Map;

public record Response<T> (
    int statusCode,
    Map<String, List<String>> headers,
    T body
){ }
