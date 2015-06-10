package com.nitorcreations.puggly.domain;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoggedRequestTest {

    @Test
    public void uri_should_contain_query_parameters() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getQueryString()).thenReturn("yes=true");
        when(request.getRequestURI()).thenReturn("http://foobar.com");
        LoggedRequest loggedRequest = new LoggedRequest(request, "");

        assertThat(loggedRequest.uri, is("http://foobar.com?yes=true"));
    }

    @Test
    public void uri_should_not_contain_query_parameters_if_they_are_null() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getQueryString()).thenReturn(null);
        when(request.getRequestURI()).thenReturn("http://foobar.com");
        LoggedRequest loggedRequest = new LoggedRequest(request, "");

        assertThat(loggedRequest.uri, is("http://foobar.com"));
    }

    @Test
    public void to_string() {
        LoggedRequest loggedRequest = new LoggedRequest();
        loggedRequest.uri = "http://foobar.com";
        loggedRequest.contentType = "text/html";
        loggedRequest.sessionId = "123";
        loggedRequest.body = "body";

        assertThat(loggedRequest.toString(), is("LoggedRequest[uri=http://foobar.com,contentType=text/html,sessionId=123,body=body]"));
    }

    @Test
    public void to_string_with_nulls() {
        LoggedRequest loggedRequest = new LoggedRequest();
        loggedRequest.uri = "http://foobar.com";
        loggedRequest.contentType = "text/html";

        assertThat(loggedRequest.toString(), is("LoggedRequest[uri=http://foobar.com,contentType=text/html,sessionId=<null>,body=<null>]"));
    }

}