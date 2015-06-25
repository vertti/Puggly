package com.nitorcreations.puggly.domain;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HeaderParserTest {

    @Test
    public void simple_header() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.addHeader("foo", "bar");

        assertThat(HeaderParser.getHeaders(servletRequest).get("foo"), hasItem("bar"));
    }

    @Test
    public void multi_value_header() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.addHeader("foo", "bar");
        servletRequest.addHeader("foo", "baz");

        assertThat(HeaderParser.getHeaders(servletRequest).get("foo"), hasItems("bar", "baz"));
    }

    @Test
    public void toString_multi_value_header() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.addHeader("foo", "bar");
        servletRequest.addHeader("foo", "baz");
        final Map<String, List<String>> headers = HeaderParser.getHeaders(servletRequest);
        assertThat(HeaderParser.headerString(headers), is("> foo: bar, baz"));
    }

}