package com.nitorcreations.puggly.domain;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.list;

class HeaderParser {

    public static HttpHeaders getHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        final Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            for (String headerName : Collections.list(headerNames)) {
                list(request.getHeaders(headerName)).stream().forEach(v -> headers.add(headerName, v));
            }
        }
        return headers;
    }

    public static HttpHeaders getHeaders(HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        final Collection<String> headerNames = response.getHeaderNames();
        if (headerNames != null) {
            for (String headerName : headerNames) {
                response.getHeaders(headerName).stream().forEach(v -> headers.add(headerName, v));
            }
        }
        return headers;
    }

    public static String headerString(HttpHeaders headers) {
        return headers.entrySet().stream().map(e ->
                "> " + e.getKey() + ": " +
                        e.getValue().stream().collect(Collectors.joining(", ")))
                .collect(Collectors.joining("\n"));
    }
}
