package com.ccat.annotation;

import java.net.URI;
import java.util.Optional;

/**
 * Contains information about the request
 * @param httpMethod Method String
 * @param uriTemplate URI address
 * @param requestBody Optional request body
 */
public record AnnotationData(
        String httpMethod,
        URI uriTemplate,
        Optional<String> requestBody
) {  }