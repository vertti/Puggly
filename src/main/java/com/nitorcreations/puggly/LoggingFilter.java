package com.nitorcreations.puggly;

import com.nitorcreations.puggly.domain.LoggedExchange;
import com.nitorcreations.puggly.domain.LoggedRequest;
import com.nitorcreations.puggly.domain.LoggedResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                response.setCharacterEncoding("UTF-8"); // Or whatever default. UTF-8 is good for World Domination.
            }

            ResettableStreamHttpServletRequest resettableRequest = new ResettableStreamHttpServletRequest((HttpServletRequest) request);
            HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse) response);

            LoggedRequest loggedRequest = new LoggedRequest((HttpServletRequest) request, IOUtils.toString(resettableRequest.getReader()));
            resettableRequest.resetInputStream();

            try {
                chain.doFilter(resettableRequest, responseCopier);
                responseCopier.flushBuffer();
            } finally {
                LoggedResponse loggedResponse = new LoggedResponse(responseCopier, new String(responseCopier.getCopy(), response.getCharacterEncoding()));
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


    public void setTransformer(ChainedExchangeTransformer transformer) {
        this.transformer = transformer;
    }

    public void setSkipper(ChainedExchangeSkipper skipper) {
        this.skipper = skipper;
    }
}