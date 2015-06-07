package com.nitorcreations.puggly.domain;

public class LoggedExchange extends PugglyValue {
    public final LoggedRequest request;
    public final LoggedResponse response;

    public LoggedExchange(LoggedRequest request, LoggedResponse response) {
        this.request = request;
        this.response = response;
    }
}
