package com.nitorcreations.puggly.domain;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;

public class LoggedResponse extends PugglyValue {
    public HttpHeaders headers;
    public int status;
    public String contentType;
    public String body;

    public LoggedResponse() {
        headers = new HttpHeaders();
    }

    public LoggedResponse(HttpServletResponse response, String body) {
        this.body = body;
        this.contentType = response.getContentType();
        this.status = response.getStatus();
        this.headers = HeaderParser.getHeaders(response);
    }

    @Override
    public String toString() {
        return "[status=" + status +
                (headers.isEmpty() ? "" : "\n" + HeaderParser.headerString(headers)) +
                "\n> contentType=" + (contentType == null ? "<null>" : contentType) +
                "\n> body=" + (body == null ? "<null>" : body)
                + "]";
    }
}
