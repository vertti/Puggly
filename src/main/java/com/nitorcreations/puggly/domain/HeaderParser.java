package com.nitorcreations.puggly.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.list;
import static java.util.stream.Collectors.toMap;

class HeaderParser {

    public static Map<String, List<String>> getHeaders(HttpServletRequest request) {
        final Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            return list(request.getHeaderNames()).stream().collect(toMap(m -> m, m -> list(request.getHeaders(m))));
        }
        return new HashMap<>();
    }

    public static Map<String, List<String>> getHeaders(HttpServletResponse response) {
        final Collection<String> headerNames = response.getHeaderNames();
        if (headerNames != null) {
            return response.getHeaderNames().stream().collect(toMap(m -> m, m -> new ArrayList<>(response.getHeaders(m))));
        }
        return new HashMap<>();
    }

    public static String headerString(Map<String, List<String>> headers) {
        return headers.entrySet().stream().map(e ->
                "> " + e.getKey() + ": " +
                        e.getValue().stream().collect(Collectors.joining(", ")))
                .collect(Collectors.joining("\n"));
    }
}
