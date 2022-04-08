package com.alibaba.csp.sentinel.dashboard.rule.convert;

import java.util.Collection;

public abstract class RuleConverter<T extends Object> implements Converter<String, Collection<T>> {

    protected final Class<T> ruleClass;

    public RuleConverter(Class<T> ruleClass) {
        this.ruleClass = ruleClass;
    }
}
