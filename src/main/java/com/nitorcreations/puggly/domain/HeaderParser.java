package com.nitorcreations.puggly.domain;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static String headerString(Map<String, List<String>> headers) {
        return headers.entrySet().stream().map(e ->
                "> " + e.getKey() + ": " +
                        e.getValue().stream().collect(Collectors.joining(", ")))
                .collect(Collectors.joining("\n"));
    }

}
