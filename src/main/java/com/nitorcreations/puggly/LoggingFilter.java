package com.nitorcreations.puggly;

import com.nitorcreations.puggly.domain.LoggedExchange;
import com.nitorcreations.puggly.domain.LoggedRequest;
import com.nitorcreations.puggly.domain.LoggedResponse;
import com.nitorcreations.puggly.domain.tranforms.ExchangeCondition;
import com.nitorcreations.puggly.domain.tranforms.ExchangeTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoggingFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    private ChainedExchangeTransformer transformer = new ChainedExchangeTransformer();
    private ChainedExchangeSkipper skipper = new ChainedExchangeSkipper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            final byte[] body = responseWrapper.getContentAsByteArray();


            HttpServletResponse rawResponse = (HttpServletResponse) responseWrapper.getResponse();

            if (body.length > 0) {
                rawResponse.setContentLength(body.length);
                StreamUtils.copy(body, rawResponse.getOutputStream());
            }

            LoggedRequest loggedRequest = new LoggedRequest(requestWrapper, new String(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding()));
            LoggedResponse loggedResponse = new LoggedResponse(responseWrapper, new String(body, responseWrapper.getCharacterEncoding()));
            LoggedExchange loggedExchange = new LoggedExchange(loggedRequest, loggedResponse);

            if (!skipper.test(loggedExchange)) {
                logger.debug("{}", transformer.apply(loggedExchange));
            }
        }
    }

    public void registerSkipCondition(ExchangeCondition condition) {
        skipper.registerSkipCondition(condition);
    }

    public void registerTransform(ExchangeCondition condition, ExchangeTransform transform) {
        transformer.registerTransform(condition, transform);
    }

}