package com.nitorcreations.puggly;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.nitorcreations.puggly.domain.tranforms.ExchangeTransform;

public class Transforms {

    public static ExchangeTransform prettyPrintJsonRequestBody() {
        return exchange -> {
            exchange.request.body = new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(exchange.request.body));
            return exchange;
        };
    }

    public static ExchangeTransform prettyPrintJsonResponseBody() {
        return exchange -> {
            System.out.println("BOOM BICTHES: ");
            exchange.response.body = new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(exchange.response.body));
            return exchange;
        };
    }

}
