package com.nitorcreations.puggly.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.*;
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
    public void session_id_from_httpservletrequest() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession(null, "123");
        servletRequest.setSession(session);
        LoggedRequest loggedRequest = new LoggedRequest(servletRequest, "body content");

        assertThat(loggedRequest.sessionId, is("123"));
    }

    @Test
    public void method_from_httpservletrequest() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setMethod("POST");
        LoggedRequest loggedRequest = new LoggedRequest(servletRequest, null);

        assertThat(loggedRequest.method, is("POST"));
    }

    @Test
    public void contentType_from_httpservletrequest() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setContentType("foo/bar");
        LoggedRequest loggedRequest = new LoggedRequest(servletRequest, "body content");

        assertThat(loggedRequest.contentType, is("foo/bar"));
    }

    @Test
    public void uri_from_httpservletrequest() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRequestURI("foo/bar");
        servletRequest.setQueryString("baz=qux");
        LoggedRequest loggedRequest = new LoggedRequest(servletRequest, "body content");

        assertThat(loggedRequest.uri, is("foo/bar?baz=qux"));
    }

    @Test
    public void to_string() {
        LoggedRequest loggedRequest = new LoggedRequest();
        loggedRequest.method = "GET";
        loggedRequest.uri = "http://foobar.com";
        loggedRequest.contentType = "text/html";
        loggedRequest.sessionId = "123";
        loggedRequest.body = "body";

        assertThat(loggedRequest.toString(), is("[GET http://foobar.com\n> contentType=text/html\n> sessionId=123\n> body=body]"));
    }

    @Test
    public void to_string_with_header() {
        LoggedRequest loggedRequest = new LoggedRequest();
        loggedRequest.method = "POST";
        loggedRequest.uri = "http://foobar.com";
        loggedRequest.contentType = "text/html";
        loggedRequest.headers.add("header1", "headerValue1");
        assertThat(loggedRequest.toString(), is("[POST http://foobar.com\n> header1: headerValue1\n> contentType=text/html\n> sessionId=<null>\n> body=<null>]"));
    }

    @Test
    public void to_string_with_nulls() {
        LoggedRequest loggedRequest = new LoggedRequest();
        loggedRequest.method = "POST";
        loggedRequest.uri = "http://foobar.com";
        loggedRequest.contentType = "text/html";

        assertThat(loggedRequest.toString(), is("[POST http://foobar.com\n> contentType=text/html\n> sessionId=<null>\n> body=<null>]"));
    }

    @Test
    public void simple_header() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.addHeader("foo", "bar");
        LoggedRequest loggedRequest = new LoggedRequest(servletRequest, "body content");

        assertThat(loggedRequest.headers.get("foo"), hasItem("bar"));
    }

    @Test
    public void multi_value_header() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.addHeader("foo", "bar");
        servletRequest.addHeader("foo", "baz");
        LoggedRequest loggedRequest = new LoggedRequest(servletRequest, "body content");

        assertThat(loggedRequest.headers.get("foo"), hasItems("bar", "baz"));
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(LoggedExchange.class).withRedefinedSuperclass().suppress(Warning.STRICT_INHERITANCE).suppress(Warning.NONFINAL_FIELDS).verify();
    }

}