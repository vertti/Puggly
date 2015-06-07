package com.nitorcreations.puggly.domain;

import javax.servlet.http.HttpServletResponse;

public class LoggedResponse extends PugglyValue {
    public final String contentType;
    public String body;

    public LoggedResponse(HttpServletResponse response, String body) {
        this.body = body;
        this.contentType = response.getContentType();
    }
}
