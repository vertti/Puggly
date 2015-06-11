package com.nitorcreations.puggly;

import com.nitorcreations.puggly.domain.LoggedExchange;
import com.nitorcreations.puggly.domain.LoggedRequest;
import com.nitorcreations.puggly.domain.LoggedResponse;
import com.nitorcreations.puggly.domain.tranforms.ExchangeCondition;
import com.nitorcreations.puggly.domain.tranforms.ExchangeTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoggingFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    private ChainedExchangeTransformer transformer = new ChainedExchangeTransformer();
    private ChainedExchangeSkipper skipper = new ChainedExchangeSkipper();

    @Override
    public void init(FilterConfig config) throws ServletException {
        // NOOP.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request instanceof HttpServletRequest) {
            if (response.getCharacterEncoding() == null) {
                response.setCharacterEncoding("UTF-8");
            }

            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

            try {
                chain.doFilter(requestWrapper, responseWrapper);
            } finally {
                LoggedRequest loggedRequest = new LoggedRequest(requestWrapper, new String(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding()));
                LoggedResponse loggedResponse = new LoggedResponse(responseWrapper, new String(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding()));
                LoggedExchange loggedExchange = new LoggedExchange(loggedRequest, loggedResponse);

                if (!skipper.test(loggedExchange)) {
                    logger.debug("{}", transformer.apply(loggedExchange));
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // NOOP.
    }

    public void registerSkipCondition(ExchangeCondition condition) {
        skipper.registerSkipCondition(condition);
    }

    public void registerTransform(ExchangeCondition condition, ExchangeTransform transform) {
        transformer.registerTransform(condition, transform);
    }

}