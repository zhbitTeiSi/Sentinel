package com.alibaba.csp.sentinel.dashboard.cluster;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterAppAssignMap;

import java.util.List;

public interface ClusterHandler {

    void publishClusterMap(String app, List<ClusterAppAssignMap> clusterAppAssignMap);

}
