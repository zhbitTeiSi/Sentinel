package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.rule.AbstractDynamicRuleHandler;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleEntityHandler;
import com.alibaba.csp.sentinel.dashboard.rule.convert.RuleConverter;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

import static com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConstatns.DEFAULT_TIMEOUT;

public abstract class NacosDynamicRuleHandler<R, E> extends AbstractDynamicRuleHandler<R> implements DynamicRuleEntityHandler<E> {

    protected final String groupId;
    protected final int timeout;
    protected final ConfigService configService;
    protected final String dataIdPostfix;

    public NacosDynamicRuleHandler(final ConfigService configService,
                                   final String groupId,
                                   final String dataIdPostfix,
                                   final RuleConverter<R> converter) {
        this(configService, groupId, dataIdPostfix, DEFAULT_TIMEOUT, converter);
    }

    public NacosDynamicRuleHandler(final ConfigService configService,
                                   final String groupId,
                                   final String dataIdPostfix,
                                   final int timeout,
                                   final RuleConverter<R> converter) {
        super(converter);
        Preconditions.checkArgument(configService != null, "configService can not be null.");
        Preconditions.checkArgument(StringUtils.isNotEmpty(groupId), "groupId can not be null.");
        Preconditions.checkArgument(StringUtils.isNotEmpty(groupId), "dataIdPostfix can not be null.");
        this.configService = configService;
        this.groupId = groupId;
        this.dataIdPostfix = dataIdPostfix;
        this.timeout = timeout;
    }

    @Override
    public List<R> loadRules(String appName) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(appName), "appName cannot be empty.");
        try {
            String rules = configService.getConfig(NacosConstatns.getRuleDataId(appName, dataIdPostfix), groupId, timeout);
            if (StringUtils.isEmpty(rules)) {
                return Collections.emptyList();
            }
            return Lists.newArrayList(converter.deserialize(rules));
        } catch (NacosException e) {
            throw new RuntimeException("[Nacos] loadRules error.", e);
        }
    }

    @Override
    public void publishRules(String appName, List<R> rules) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(appName), "appName cannot be empty.");
        if (rules == null) {
            return;
        }
        try {
            configService.publishConfig(NacosConstatns.getRuleDataId(appName, dataIdPostfix), groupId, converter.serialize(rules));
        } catch (NacosException e) {
            throw new RuntimeException("[Nacos] publishRules error.", e);
        }
    }
}
