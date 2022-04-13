package com.alibaba.csp.sentinel.dashboard.cluster.nacos;

import com.alibaba.csp.sentinel.dashboard.cluster.ClusterHandler;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterAppAssignMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NacosClusterHandler implements ClusterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NacosClusterHandler.class);

    private final ConfigService configService;
    private final String groupId;
    private final String dataIdPostfix;

    public NacosClusterHandler(final ConfigService configService,
                               final String groupId,
                               final String dataIdPostfix) {
        this.configService = configService;
        this.groupId = groupId;
        this.dataIdPostfix = dataIdPostfix;
    }

    @Override
    public void publishClusterMap(String app, List<ClusterAppAssignMap> clusterAppAssignMap) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(app), "app cannot be empty.");
        if (clusterAppAssignMap == null) {
            return;
        }
        try {
            configService.publishConfig(getDataId(app, dataIdPostfix), groupId, JSON.toJSONString(clusterAppAssignMap));
        } catch (NacosException e) {
            LOGGER.error("[Nacos cluster]: publish config failed.", e);
        }
    }

    private String getDataId(String app, String dataIdPostfix) {
        return String.format("%s-%s", app, dataIdPostfix);
    }
}
