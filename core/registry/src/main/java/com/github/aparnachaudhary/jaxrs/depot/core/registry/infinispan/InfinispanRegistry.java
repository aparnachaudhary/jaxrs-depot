package com.github.aparnachaudhary.jaxrs.depot.core.registry.infinispan;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointInfo;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointRegistry;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointEvent;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.util.PojoMapper;
import org.infinispan.manager.CacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.event.Event;
import javax.inject.Inject;
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

    @Inject
    private Event<EndpointEvent> event;

    @PostConstruct
    public void init() {
        this.cache = cc.getCache();
        cc.getCache().getCacheManager().addListener(new MemberDropListener(cache));
        cc.getCache().addListener(new ClusteredCacheListener(event));
        LOG.info("Cache {}", cc.getCache().keySet());
    }

    @Override
    public void addEndpoint(EndpointInfo endpointInfo) {
        LOG.info("Adding endpoint '{}' to registry with base URI {} and service root {}", endpointInfo.getEndpointId(), endpointInfo.getBaseUri(),
                endpointInfo.getServiceRoot());
        try {
            cache.put(PojoMapper.toJson(endpointInfo.getEndpointId(), false), PojoMapper.toJson(endpointInfo, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEndpoint(EndpointId endpointId) {
        LOG.info("Removing endpoint '{}' from registry", endpointId);
        try {
            cache.remove(PojoMapper.toJson(endpointId, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<EndpointInfo> getEndpoints() {
        List<EndpointInfo> endpointInfoList = new ArrayList<EndpointInfo>();
        for (String value : cache.values()) {
            try {
                endpointInfoList.add(PojoMapper.fromJson(value, EndpointInfo.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return endpointInfoList;
    }

    @Override
    public boolean existsEndpoint(String appId, String endpointName) {
        boolean endpointExists = false;
        EndpointId endpointId;
        for (String key : cache.keySet()) {
            try {
                endpointId = PojoMapper.fromJson(key, EndpointId.class);
                if (endpointId.getAppName().equalsIgnoreCase(appId) && endpointId.getEndpointName().equalsIgnoreCase(endpointName)) {
                    endpointExists = true;
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return endpointExists;
    }

    @Override
    public EndpointInfo getEndpoint(EndpointId endpointId) {
        try {
            String value = cache.get(PojoMapper.toJson(endpointId, false));
            if (value != null) {
                return PojoMapper.fromJson(value, EndpointInfo.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
