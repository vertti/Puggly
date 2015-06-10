package com.nitorcreations.puggly.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.servlet.http.HttpServletResponse;

public class LoggedResponse extends PugglyValue {
    public String contentType;
    public String body;

    public LoggedResponse() {
    }

    public LoggedResponse(HttpServletResponse response, String body) {
        this.body = body;
        this.contentType = response.getContentType();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("contentType", contentType)
                .append("body", body)
                .toString();
    }
}
