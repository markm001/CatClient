package com.ccat.annotation;

import com.ccat.util.CGsonHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses Annotations to the respective AnnotationData
 */
public class AnnotationParser {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationParser.class);

    private final static Pattern templatePattern = Pattern.compile("\\{(\\w+)}");
    private static final Map<Class<? extends Annotation>, String> httpAnnotations = new HashMap<>();
    private static final CGsonHandler gsonHandler = CGsonHandler.getInstance();

    static {
        httpAnnotations.put(GET.class, "GET");
        httpAnnotations.put(DELETE.class, "DELETE");
        httpAnnotations.put(POST.class, "POST");
        httpAnnotations.put(PUT.class, "PUT");
        httpAnnotations.put(PATCH.class, "PATCH");
        httpAnnotations.put(UPDATE.class, "UPDATE");
    }

    /**
     * Parses the provided Data to an AnnotationData Object, which is used within the Proxy Method.
     * @param method GET, DELETE, POST, PUT, PATCH, UPDATE
     * @param baseUrl Address where the request is sent to
     * @param args Arguments that will be parsed and put set into the URL
     * @return AnnotationData Object containing the necessary information
     */
    public static AnnotationData parseAnnotationData(Method method, String baseUrl, Object[] args) {
        Annotation annotation;
        try{
            annotation = httpAnnotations.keySet().stream()
                .map(method::getAnnotation)
                .filter(Objects::nonNull)
                .map(a -> (Annotation)a)
                .findFirst()
                .orElseThrow();
        } catch (NoSuchElementException e) {
            logger.warn("Could not match HTTP request method to available key set. {}", e.getMessage());
            throw new RuntimeException(e);
        }

        String httpMethod = httpAnnotations.get(annotation.annotationType());


        String urlTemplate = extractParameter(annotation, "value");
        String bodyTemplate = extractParameter(annotation, "body");

        URI uri = parseUriTemplate(baseUrl, urlTemplate, args);

        if(!bodyTemplate.isEmpty()) {
            Object body = args[args.length - 1];
            String jsonBody = gsonHandler.toJson(body, body.getClass());

            return new AnnotationData(httpMethod,uri, Optional.of(jsonBody));
        }

        return new AnnotationData(httpMethod,uri,Optional.empty());
    }

    private static String extractParameter(Annotation annotation, String name) {
        try {
            Method valueMethod = annotation.annotationType().getMethod(name);
            return (String) valueMethod.invoke(annotation);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | SecurityException e) {
            logger.warn("Could not extract parameter value from annotation. {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static URI parseUriTemplate(String baseUrl, String template, Object[] args) {
        Matcher argMatcher = templatePattern.matcher(template);

        int argIndex = 0;
        while (argMatcher.find() && argIndex < args.length) {
            template = template.replace("{" + argMatcher.group(1) + "}", args[argIndex].toString());
            argIndex++;
        }

        return URI.create(baseUrl + template);
    }
}
