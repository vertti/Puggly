package com.nitorcreations.puggly;

import com.nitorcreations.puggly.domain.LoggedExchange;
import com.nitorcreations.puggly.domain.tranforms.ConditionalTransform;
import com.nitorcreations.puggly.domain.tranforms.ExchangeCondition;
import com.nitorcreations.puggly.domain.tranforms.ExchangeTransform;

import java.util.ArrayList;
import java.util.List;

public class ChainedExchangeTransformer implements ExchangeTransform {

    private List<ConditionalTransform> transforms = new ArrayList<>();

    public void registerTransform(ExchangeCondition condition, ExchangeTransform transform) {
        transforms.add(new ConditionalTransform(condition, transform));
    }

    @Override
    public LoggedExchange apply(LoggedExchange original) {
        return transforms.stream().reduce(original, (o, t) -> t.apply(o), (m1, m2) -> m2);
    }
}
