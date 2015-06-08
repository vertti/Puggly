package com.nitorcreations.puggly.domain;

import javax.servlet.http.HttpServletRequest;

public class LoggedRequest extends PugglyValue {
    public String body;
    public String contentType;

    public LoggedRequest() {
    }

    public LoggedRequest(HttpServletRequest request, String body) {
        this.body = body;
        contentType = request.getContentType();
    }
}
