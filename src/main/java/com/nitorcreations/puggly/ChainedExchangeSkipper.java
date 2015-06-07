package com.nitorcreations.puggly;

import com.nitorcreations.puggly.domain.LoggedExchange;
import com.nitorcreations.puggly.domain.tranforms.ConditionalTransform;
import com.nitorcreations.puggly.domain.tranforms.ExchangeCondition;
import com.nitorcreations.puggly.domain.tranforms.ExchangeTransform;

import java.util.ArrayList;
import java.util.List;

public class ChainedExchangeSkipper implements ExchangeCondition {

    private List<ExchangeCondition> transforms = new ArrayList<>();

    public void registerSkipCondition(ExchangeCondition condition) {
        transforms.add(condition);
    }

    @Override
    public boolean test(LoggedExchange loggedExchange) {
        return transforms.stream().reduce(false, (o, t) -> t.test(loggedExchange) || o, (m1, m2) -> m2);
    }
}
