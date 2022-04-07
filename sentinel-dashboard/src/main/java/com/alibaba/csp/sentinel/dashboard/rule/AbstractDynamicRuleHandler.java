package com.alibaba.csp.sentinel.dashboard.rule;

import com.google.common.base.Preconditions;

import java.util.List;

public abstract class AbstractDynamicRuleHandler<S, T> implements DynamicRuleHandler<T> {

    protected final RuleConverter<S, List<T>> converter;

    public AbstractDynamicRuleHandler(RuleConverter<S, List<T>> converter) {
        Preconditions.checkArgument(converter != null, "convert can not be null.");
        this.converter = converter;
    }

}
