package com.alibaba.csp.sentinel.dashboard.rule;

public interface RuleConverter<S, T> {

    S serialize(T t) throws Exception;

    T deserialize(S s) throws Exception;

}
