package com.alibaba.csp.sentinel.dashboard.rule;

import java.util.List;

public interface DynamicRuleEntityHandler<E> {

    List<E> loadEntityList(String appName, String ip, Integer port);

    void publishEntityList(String appName, List<E> entityList);

}
