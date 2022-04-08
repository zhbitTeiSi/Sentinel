package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.config.NacosDataSourceProperties;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.convert.JsonRuleConverter;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class NacosDynamicRuleConfig {

    @Resource
    NacosDataSourceProperties nacosDataSourceProperties;

    @Bean
    public ConfigService nacosConfigService() throws NacosException {
        return NacosFactory.createConfigService(nacosDataSourceProperties.getServerAddr());
    }

    @Bean
    public NacosDynamicRuleHandler<FlowRule, FlowRuleEntity> flowRuleHandler(ConfigService configService) {
        return new NacosDynamicRuleHandler<FlowRule, FlowRuleEntity>(configService,
                Optional.ofNullable(nacosDataSourceProperties.getGroupId()).orElse(NacosConstatns.DEFAULT_GROUP_ID),
                NacosConstatns.FLOW_RULE, new JsonRuleConverter<>(FlowRule.class)) {

            @Override
            public List<FlowRuleEntity> loadEntityList(String appName, String ip, Integer port) {
                return loadRules(appName).stream().map(rule -> FlowRuleEntity.fromFlowRule(appName, ip, port, rule)).collect(Collectors.toList());
            }

            @Override
            public void publishEntityList(String appName, List<FlowRuleEntity> entityList) {
                List<FlowRule> rules = entityList.stream().map(FlowRuleEntity::toRule).collect(Collectors.toList());
                publishRules(appName, rules);
            }
        };
    }

    @Bean
    public NacosDynamicRuleHandler<ParamFlowRule, ParamFlowRuleEntity> paramFlowRuleHandler(ConfigService configService) {
        return new NacosDynamicRuleHandler<ParamFlowRule, ParamFlowRuleEntity>(configService,
                Optional.ofNullable(nacosDataSourceProperties.getGroupId()).orElse(NacosConstatns.DEFAULT_GROUP_ID),
                NacosConstatns.PARAM_FLOW_RULE, new JsonRuleConverter<>(ParamFlowRule.class)) {

            @Override
            public List<ParamFlowRuleEntity> loadEntityList(String appName, String ip, Integer port) {
                return loadRules(appName).stream().map(rule -> ParamFlowRuleEntity.fromParamFlowRule(appName, ip, port, rule)).collect(Collectors.toList());
            }

            @Override
            public void publishEntityList(String appName, List<ParamFlowRuleEntity> entityList) {
                List<ParamFlowRule> rules = entityList.stream().map(ParamFlowRuleEntity::toRule).collect(Collectors.toList());
                publishRules(appName, rules);
            }
        };
    }

}
