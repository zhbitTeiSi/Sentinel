package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.rule.AbstractDynamicRuleHandler;
import com.alibaba.csp.sentinel.dashboard.rule.RuleConverter;
import com.alibaba.nacos.api.config.ConfigService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

public abstract class NacosDynamicRuleHandler<T> extends AbstractDynamicRuleHandler<String, T> {

    private static final String DEFAULT_GROUP_ID = "SENTINEL_GROUP";
    private static final int DEFAULT_TIMEOUT = 3000;

    protected String groupId;
    protected int timeout;
    protected ConfigService configService;

    public NacosDynamicRuleHandler(final ConfigService configService, RuleConverter<String, List<T>> converter) {
        this(configService, DEFAULT_GROUP_ID, DEFAULT_TIMEOUT, converter);
    }

    public NacosDynamicRuleHandler(final ConfigService configService, String groupId,
                                   RuleConverter<String, List<T>> converter) {
        this(configService, groupId, DEFAULT_TIMEOUT, converter);
    }

    public NacosDynamicRuleHandler(final ConfigService configService, final String groupId, int timeout, RuleConverter<String, List<T>> converter) {
        super(converter);
        Preconditions.checkArgument(configService != null, "configService can not be null.");
        Preconditions.checkArgument(StringUtils.isNotEmpty(groupId), "groupId can not be null.");
        this.configService = configService;
        this.groupId = groupId;
        this.timeout = timeout;
    }

    @Override
    public List<T> loadRules(String appName) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotEmpty(appName), "appName cannot be empty.");
        String rules = configService.getConfig(getDataId(appName), groupId, timeout);
        if (StringUtils.isEmpty(rules)) {
            return Collections.emptyList();
        }
        return converter.deserialize(rules);
    }

    @Override
    public void publishRules(String appName, List<T> rules) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotEmpty(appName), "appName cannot be empty.");
        if (rules == null) {
            return;
        }
        configService.publishConfig(getDataId(appName), groupId, converter.serialize(rules));
    }

    abstract String getRuleId();

    private String getDataId(String appName) {
        return String.format("%s-%s", appName, getRuleId());
    }
}
