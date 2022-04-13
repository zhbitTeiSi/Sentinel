package com.alibaba.csp.sentinel.dashboard.rule.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RuleJsonConverter<T> extends RuleConverter<T> {

    public RuleJsonConverter(Class<T> ruleClass) {
        super(ruleClass);
    }

    @Override
    public String serialize(Collection<T> collection) {
        return JSON.toJSONString(collection);
    }

    @Override
    public Collection<T> deserialize(String s) {
        if (StringUtils.isEmpty(s)) {
            return Collections.emptyList();
        }
        return JSON.parseObject(s, new TypeReference<List<JSONObject>>() {
                }).stream()
                .map(jsonObject -> jsonObject.toJavaObject(ruleClass))
                .collect(Collectors.toList());
    }
}
