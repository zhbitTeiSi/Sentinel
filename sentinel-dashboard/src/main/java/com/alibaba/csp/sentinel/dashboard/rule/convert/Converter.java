package com.alibaba.csp.sentinel.dashboard.rule.convert;

public interface Converter<S, T> {

    S serialize(T t);

    T deserialize(S s);
}
