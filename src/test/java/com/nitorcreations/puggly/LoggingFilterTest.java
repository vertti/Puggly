package com.nitorcreations.puggly;

import com.nitorcreations.puggly.domain.LoggedExchange;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LoggingFilterTest {

    FilterTestingServer jetty;

    CloseableHttpClient httpclient;

    @Mock
    Logger logger;

    @InjectMocks
    private LoggingFilter filterToTest;

    @Before
    public void setup() throws Exception {
        httpclient = HttpClients.createDefault();

        filterToTest.registerTransform(Conditions.hasResponseContentType("application/json"), Transforms.prettyPrintJsonResponseBody());
        filterToTest.registerSkipCondition(Conditions.hasRequestContentType("foobar/yeah"));

        jetty = new FilterTestingServer();
        jetty.start(filterToTest);
    }

    @Test
    public void testGetJson() throws IOException, InterruptedException {
        HttpGet httpget = new HttpGet("http://localhost:8080/foobar");
        httpget.addHeader("accept", "application/json");
        httpclient.execute(httpget);

        ArgumentCaptor<LoggedExchange> captor = ArgumentCaptor.forClass(LoggedExchange.class);

        verify(logger, timeout(1000).times(1)).debug(eq("{}"), captor.capture());
        assertThat(captor.getValue().response.body, is("[\n  {\n    \"message\": \"test1\"\n  },\n  {\n    \"message\": \"test2\"\n  }\n]"));
    }

    @Test
    public void testGet() throws IOException {
        HttpGet httpget = new HttpGet("http://localhost:8080/foobar");

        httpclient.execute(httpget);

        verify(logger).debug(eq("{}"), any(LoggedExchange.class));
    }

    @Test
    public void testSkipping() throws IOException {
        HttpGet httpget = new HttpGet("http://localhost:8080/foobar");
        httpget.setHeader("Content-Type", "foobar/yeah");
        httpclient.execute(httpget);

        verifyZeroInteractions(logger);
    }

    @Test
    public void testPost() throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8080/foobar");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("username", "vip"));
        nvps.add(new BasicNameValuePair("password", "secret"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        final CloseableHttpResponse response = httpclient.execute(httpPost);

        assertThat(StreamUtils.copyToString(response.getEntity().getContent(), StandardCharsets.UTF_8), is("response to post"));
        verify(logger).debug(eq("{}"), any(LoggedExchange.class));
    }

    @After
    public void teardown() throws Exception {
        jetty.stop();
    }

}