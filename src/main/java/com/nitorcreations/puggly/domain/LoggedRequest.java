package com.nitorcreations.puggly.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoggedRequest extends PugglyValue {
    public String method;
    public String uri;
    public String body;
    public String contentType;
    public String sessionId;
    public Map<String, List<String>> headers;

    public LoggedRequest() {
        headers = new HashMap<>();
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
        this.headers = HeaderParser.getHeaders(request);
    }

    @Override
    public String toString() {
        return "[" + method + " " + (uri == null ? "<null>" : uri) +
                (headers.isEmpty() ? "" : "\n" + HeaderParser.headerString(headers)) +
                "\n> contentType=" + (contentType == null ? "<null>" : contentType) +
                "\n> sessionId=" + (sessionId == null ? "<null>" : sessionId) +
                "\n> body=" + (body == null ? "<null>" : body) +
                "]";
    }
}
