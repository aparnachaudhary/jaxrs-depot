package com.github.aparnachaudhary.jaxrs.depot.core.registry.infinispan;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointInfo;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointRegistry;
import org.infinispan.manager.CacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Regarding use of {@link ManagedBean} - See https://developer.jboss.org/message/872450
 *
 * @author Aparna Chaudhary
 */
//@ApplicationScoped
@ManagedBean
public class InfinispanRegistry implements EndpointRegistry {

    private static final Logger LOG = LoggerFactory.getLogger(InfinispanRegistry.class);

    @Resource(lookup = "java:jboss/infinispan/container/myCache")
    private CacheContainer cc;

    private Map<String, String> cache;

    @PostConstruct
    public void init() {
        this.cache = cc.getCache();
        cc.getCache().getCacheManager().addListener(new MemberDropListener(cache));
        LOG.info("Cache {}", cc.getCache().keySet());
    }

    @Override
    public void addEndpoint(EndpointInfo endpointInfo) {
        LOG.info("Adding endpoint '{}' to registry with base URI {} and service root {}", endpointInfo.getEndpointId(), endpointInfo.getBaseUri(),
                endpointInfo.getServiceRoot());
        try {
            cache.put(JsonUtil.toJson(endpointInfo.getEndpointId(), false), JsonUtil.toJson(endpointInfo, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEndpoint(EndpointId endpointId) {
        LOG.info("Removing endpoint '{}' from registry", endpointId);
        cache.remove(endpointId);
    }

    @Override
    public Collection<EndpointInfo> getEndpoints() {
        List<EndpointInfo> endpointInfoList = new ArrayList<EndpointInfo>();
        for (String value : cache.values()) {
            try {
                endpointInfoList.add(JsonUtil.fromJson(value, EndpointInfo.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return endpointInfoList;
    }

}
