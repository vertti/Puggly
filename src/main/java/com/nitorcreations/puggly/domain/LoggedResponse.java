package com.nitorcreations.puggly.domain;

import javax.servlet.http.HttpServletResponse;

public class LoggedResponse extends PugglyValue {
    public int status;
    public String contentType;
    public String body;

    public LoggedResponse() {
    }

    public LoggedResponse(HttpServletResponse response, String body) {
        this.body = body;
        this.contentType = response.getContentType();
        this.status = response.getStatus();
    }

    @Override
    public String toString() {
        return "[status=" + status +
                ", contentType=" + (contentType == null ? "<null>" : contentType) +
                ", body=" + (body == null ? "<null>" : body)
                + "]";
    }
}
