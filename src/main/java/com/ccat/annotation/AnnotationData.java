package com.ccat.annotation;

import java.net.URI;
import java.util.Optional;

public record AnnotationData(
        String httpMethod,
        URI uriTemplate,
        Optional<String> requestBody
) {  }