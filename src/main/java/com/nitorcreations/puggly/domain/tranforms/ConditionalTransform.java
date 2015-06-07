package com.nitorcreations.puggly.domain.tranforms;

import com.nitorcreations.puggly.domain.LoggedExchange;

import java.util.function.UnaryOperator;

public class ConditionalTransform implements UnaryOperator<LoggedExchange> {
    public final ExchangeCondition condition;
    public final ExchangeTransform transform;

    public ConditionalTransform(ExchangeCondition condition, ExchangeTransform transform) {
        this.condition = condition;
        this.transform = transform;
    }

    @Override
    public LoggedExchange apply(LoggedExchange loggedExchange) {
        return condition.test(loggedExchange) ? transform.apply(loggedExchange) : loggedExchange;
    }
}
