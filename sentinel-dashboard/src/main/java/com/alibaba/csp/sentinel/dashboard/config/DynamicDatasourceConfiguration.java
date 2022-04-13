package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.csp.sentinel.dashboard.cluster.nacos.NacosClusterHandler;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.convert.RuleJsonConverter;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosDynamicRuleHandler;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(SentinelDashboardProperties.class)
public class DynamicDatasourceConfiguration {

    private final NacosDataSourceProperties properties;

    public DynamicDatasourceConfiguration(SentinelDashboardProperties dashboardProperties) {
        this.properties = dashboardProperties.getDatasource().getNacos();
    }

    @Bean
    public ConfigService nacosConfigService() throws NacosException {
        return NacosFactory.createConfigService(properties.getServerAddr());
    }

    @Bean
    public NacosDynamicRuleHandler<FlowRule, FlowRuleEntity> flowRuleHandler(ConfigService configService) {
        return new NacosDynamicRuleHandler<FlowRule, FlowRuleEntity>(configService,
                properties.getGroupId(), properties.getRule().getFlowRulePostfix(), new RuleJsonConverter<>(FlowRule.class)) {

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
                properties.getGroupId(), properties.getRule().getParamFlowRulePostfix(), new RuleJsonConverter<>(ParamFlowRule.class)) {

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

    @Bean
    public NacosClusterHandler clusterHandler(ConfigService configService) {
        return new NacosClusterHandler(configService, properties.getGroupId(), properties.getCluster().getClusterMapPostfix());
    }

}
