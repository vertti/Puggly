package com.nitorcreations.puggly.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoggedRequest extends PugglyValue {
    public String method;
    public String uri;
    public String body;
    public String contentType;
    public String sessionId;

    public LoggedRequest() {
    }

    public LoggedRequest(HttpServletRequest request, String body) {
        this.body = body;
        contentType = request.getContentType();
        this.method = request.getMethod();
        HttpSession session = request.getSession(false);
        if (session != null) {
            sessionId = session.getId();
        }
        uri = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
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
