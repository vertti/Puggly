package com.nitorcreations.puggly;

import com.nitorcreations.puggly.domain.LoggedExchange;
import com.nitorcreations.puggly.domain.LoggedRequest;
import com.nitorcreations.puggly.domain.LoggedResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConditionsTest extends Conditions {

    @Test
    public void mimetype_condition_matches_correct_request() {
        LoggedExchange exchange = new LoggedExchange(new LoggedRequest(), new LoggedResponse());
        exchange.request.contentType = "match;fuu=bar";
        assertThat(Conditions.hasRequestContentType("match").test(exchange), is(true));
    }

    @Test
    public void mimetype_condition_does_not_match_incorrect_request() {
        LoggedExchange exchange = new LoggedExchange(new LoggedRequest(), new LoggedResponse());
        exchange.request.contentType = "wrong;fuu=bar";
        assertThat(Conditions.hasRequestContentType("match").test(exchange), is(false));
    }

    @Test
    public void mimetype_condition_matches_correct_response() {
        LoggedExchange exchange = new LoggedExchange(new LoggedRequest(), new LoggedResponse());
        exchange.response.contentType = "match;fuu=bar";
        assertThat(Conditions.hasResponseContentType("match").test(exchange), is(true));
    }

    @Test
    public void mimetype_condition_does_not_match_incorrect_response() {
        LoggedExchange exchange = new LoggedExchange(new LoggedRequest(), new LoggedResponse());
        exchange.response.contentType = "wrong;fuu=bar";
        assertThat(Conditions.hasResponseContentType("match").test(exchange), is(false));
    }

    @Test
    public void uri_contains() {
        LoggedExchange exchange = new LoggedExchange(new LoggedRequest(), new LoggedResponse());
        exchange.request.uri = "/foo/bar/baz";
        assertThat(Conditions.uriContains("/bar/").test(exchange), is(true));
    }

}