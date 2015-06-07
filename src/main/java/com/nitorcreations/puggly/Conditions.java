package com.nitorcreations.puggly;

import com.nitorcreations.puggly.domain.tranforms.ExchangeCondition;
import org.apache.commons.lang3.StringUtils;

public class Conditions {

    public static ExchangeCondition hasRequestContentType(String mimeType) {
        return exchange -> {
            System.out.println("BOOM: wanted " + mimeType + " but got " + exchange.request.contentType);
            return StringUtils.contains(exchange.request.contentType, mimeType);
        };
    }

    public static ExchangeCondition hasResponseContentType(String mimeType) {
        return exchange -> StringUtils.contains(exchange.response.contentType, mimeType);
    }

}
