package com.nitorcreations.puggly;

import com.nitorcreations.puggly.domain.LoggedExchange;
import com.nitorcreations.puggly.domain.LoggedRequest;
import com.nitorcreations.puggly.domain.LoggedResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TransformsTest {

    @Test
    public void testPrettyPrintJsonRequestBody() throws Exception {
        LoggedExchange exchange = new LoggedExchange(new LoggedRequest(), new LoggedResponse());
        exchange.request.body = "{'message':'foo','next':'bar'}";
        assertThat(Transforms.prettyPrintJsonRequestBody().apply(exchange).request.body, is("{\n  \"message\": \"foo\",\n  \"next\": \"bar\"\n}"));
    }

    @Test
    public void testPrettyPrintJsonResponseBody() throws Exception {
        LoggedExchange exchange = new LoggedExchange(new LoggedRequest(), new LoggedResponse());
        exchange.response.body = "{'message':'foo','next':'bar'}";
        assertThat(Transforms.prettyPrintJsonResponseBody().apply(exchange).response.body, is("{\n  \"message\": \"foo\",\n  \"next\": \"bar\"\n}"));
    }

    @Test
    public void testReplaceResponseBody() throws Exception {
        LoggedExchange exchange = new LoggedExchange(new LoggedRequest(), new LoggedResponse());
        exchange.response.body = "foo";
        assertThat(Transforms.replaceResponseBody("bar").apply(exchange).response.body, is("bar"));
    }

    @Test
    public void testReplaceRequestBody() throws Exception {
        LoggedExchange exchange = new LoggedExchange(new LoggedRequest(), new LoggedResponse());
        exchange.request.body = "foo";
        assertThat(Transforms.replaceRequestBody("bar").apply(exchange).request.body, is("bar"));
    }
}