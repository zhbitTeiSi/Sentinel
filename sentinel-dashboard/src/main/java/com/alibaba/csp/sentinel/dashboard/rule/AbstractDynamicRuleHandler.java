package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.rule.convert.RuleConverter;
import com.google.common.base.Preconditions;

public abstract class AbstractDynamicRuleHandler<T> implements DynamicRuleHandler<T> {

    protected final RuleConverter<T> converter;

    public AbstractDynamicRuleHandler(RuleConverter<T> converter) {
        Preconditions.checkArgument(converter != null, "convert can not be null.");
        this.converter = converter;
    }

}
