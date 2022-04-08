package com.alibaba.csp.sentinel.dashboard.rule;

import java.util.List;

public interface DynamicRuleHandler<R> {

    List<R> loadRules(String appName);

    void publishRules(String appName, List<R> rules);
}
