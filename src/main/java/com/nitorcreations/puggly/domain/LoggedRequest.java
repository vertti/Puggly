package com.nitorcreations.puggly.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.list;
import static java.util.stream.Collectors.toMap;

public class LoggedRequest extends PugglyValue {
    public String method;
    public String uri;
    public String body;
    public String contentType;
    public String sessionId;
    public Map<String, List<String>> headers = new HashMap<>();

    public LoggedRequest() {
    }

    public LoggedRequest(HttpServletRequest request, String body) {
        this.body = body;
        this.contentType = request.getContentType();
        this.method = request.getMethod();
        HttpSession session = request.getSession(false);
        if (session != null) {
            sessionId = session.getId();
        }
        this.uri = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        this.headers = getHeaders(request);
    }

    private Map<String, List<String>> getHeaders(HttpServletRequest request) {
        final Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            return list(request.getHeaderNames()).stream().collect(toMap(m -> m, m -> list(request.getHeaders(m))));
        }
        return new HashMap<>();
    }

    @Override
    public String toString() {
        return "[" + method + " " + (uri == null ? "<null>" : uri) +
                "\n> contentType=" + (contentType == null ? "<null>" : contentType) +
                "\n> sessionId=" + (sessionId == null ? "<null>" : sessionId) +
                "\n> body=" + (body == null ? "<null>" : body) +
                "]";
    }
}
