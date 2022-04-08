package com.alibaba.csp.sentinel.dashboard.apollo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApolloMeta {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApolloMeta.class);

    public static void init() {
        String metaUrl = System.getProperty("meta-url");
        if (StringUtils.isEmpty(metaUrl)) {
            return;
        }
        System.setProperty("apollo.meta", metaUrl);
        LOGGER.info(">>>>> ApolloMeta initByMetaUrl by metaUrl:{}", metaUrl);
    }

}
