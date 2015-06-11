package com.nitorcreations.puggly;

import com.nitorcreations.puggly.domain.tranforms.ExchangeCondition;
import org.apache.commons.lang3.StringUtils;

public class Conditions {

    public static ExchangeCondition hasRequestContentType(String mimeType) {
        return exchange -> StringUtils.contains(exchange.request.contentType, mimeType);
    }

    public static ExchangeCondition hasResponseContentType(String mimeType) {
        return exchange -> StringUtils.contains(exchange.response.contentType, mimeType);
    }

    public static ExchangeCondition uriContains(String uripart) {
        return exchange -> StringUtils.contains(exchange.request.uri, uripart);
    }

    public static ExchangeCondition responseStatusOk() {
        return exchange -> exchange.response.status >= 200 && exchange.response.status < 300;
    }

    protected Conditions() {
        // empty private constructor for util class
    }
}
