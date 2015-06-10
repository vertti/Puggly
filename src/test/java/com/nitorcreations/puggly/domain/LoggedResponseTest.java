package com.nitorcreations.puggly.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoggedResponseTest {

    @Test
    public void to_string() {
        LoggedResponse loggedResponse = new LoggedResponse();
        loggedResponse.body = "body";
        loggedResponse.contentType = "foo/bar";

        assertThat(loggedResponse.toString(), is("LoggedResponse[contentType=foo/bar,body=body]"));
    }

}