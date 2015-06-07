package com.nitorcreations.puggly.domain.tranforms;

import com.nitorcreations.puggly.domain.LoggedExchange;

import java.util.function.Predicate;

@FunctionalInterface
public interface ExchangeCondition extends Predicate<LoggedExchange> {
}
