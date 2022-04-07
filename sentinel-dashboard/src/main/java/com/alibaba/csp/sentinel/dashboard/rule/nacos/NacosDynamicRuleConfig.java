package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.RuleConstants;
import com.alibaba.csp.sentinel.dashboard.rule.RuleConverter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class NacosDynamicRuleConfig {

    @Bean
    public ConfigService nacosConfigService() throws NacosException {
        return NacosFactory.createConfigService("172.17.1.125:10466");
    }

    @Bean
    public NacosDynamicRuleHandler<FlowRuleEntity> flowRuleHandler(ConfigService configService) {
        return new NacosDynamicRuleHandler<FlowRuleEntity>(configService, new RuleConverter<String, List<FlowRuleEntity>>() {
            @Override
            public String serialize(List<FlowRuleEntity> flowRuleEntities) throws Exception {
                return JSON.toJSONString(flowRuleEntities);
            }

            @Override
            public List<FlowRuleEntity> deserialize(String s) throws Exception {
                return JSON.parseObject(s, new TypeReference<List<FlowRuleEntity>>() {
                });
            }
        }) {
            @Override
            String getRuleId() {
                return RuleConstants.FLOW_RULE;
            }
        };
    }

    @Bean
    public NacosDynamicRuleHandler<ParamFlowRuleEntity> paramFlowRuleHandler(ConfigService configService) {
        return new NacosDynamicRuleHandler<ParamFlowRuleEntity>(configService, new RuleConverter<String, List<ParamFlowRuleEntity>>() {
            @Override
            public String serialize(List<ParamFlowRuleEntity> flowRuleEntities) throws Exception {
                return JSON.toJSONString(flowRuleEntities);
            }

            @Override
            public List<ParamFlowRuleEntity> deserialize(String s) throws Exception {
                return JSON.parseObject(s, new TypeReference<List<ParamFlowRuleEntity>>() {
                });
            }
        }) {
            @Override
            String getRuleId() {
                return RuleConstants.PARAM_FLOW_RULE;
            }
        };
    }

}
