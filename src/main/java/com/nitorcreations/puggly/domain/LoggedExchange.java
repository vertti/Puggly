package com.nitorcreations.puggly.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.concurrent.atomic.AtomicLong;

public class LoggedExchange extends PugglyValue {
    private final AtomicLong counter = new AtomicLong(1);

    public final long id;

    public final LoggedRequest request;
    public final LoggedResponse response;

    public LoggedExchange(LoggedRequest request, LoggedResponse response) {
        this.id = counter.getAndIncrement();
        this.request = request;
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, "id");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id");
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("request", request)
                .append("response", response)
                .toString();
    }
}
