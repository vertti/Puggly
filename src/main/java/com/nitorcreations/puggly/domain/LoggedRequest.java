package com.nitorcreations.puggly.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoggedRequest extends PugglyValue {
    public String uri;
    public String body;
    public String contentType;
    public String sessionId;

    public LoggedRequest() {
    }

    public LoggedRequest(HttpServletRequest request, String body) {
        this.body = body;
        contentType = request.getContentType();
        HttpSession session = request.getSession(false);
        if (session != null) {
            sessionId = session.getId();
        }
        uri = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }

    @Override
    public String toString() {
        return "[uri=" + (uri == null ? "<null>" : uri) +
                ", contentType=" + (contentType == null ? "<null>" : contentType) +
                ", sessionId=" + (sessionId == null ? "<null>" : sessionId) +
                ", body=" + (body == null ? "<null>" : body) +
                "]";
    }
}
