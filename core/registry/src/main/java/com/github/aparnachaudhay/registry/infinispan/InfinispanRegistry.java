package com.github.aparnachaudhay.registry.infinispan;

import com.github.aparnachaudhay.registry.EndpointRegistry;
import org.infinispan.manager.CacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Map;
import java.util.Set;

/**
 * @author Aparna Chaudhary
 */
@Startup
@Singleton
public class InfinispanRegistry implements EndpointRegistry {

    private static final Logger LOG = LoggerFactory.getLogger(InfinispanRegistry.class);

    private static final String NODE_NAME_KEY = "jboss.node.name";

    @Resource(lookup = "java:jboss/infinispan/container/myCache")
    private CacheContainer cc;

    private Map<String, String> cache;

    @PostConstruct
    public void init() {
        LOG.info("Cache {}", cc.getCache().keySet());
        this.cache = cc.getCache();
        cc.getCache().getCacheManager().addListener(new MemberDropListener(cache));
    }

    @Override
    public void addEndpoint(String endpointId, String uri) {
        String cacheKey = createCacheKey(endpointId);
        LOG.info("Adding endpoint '{}' to registry with URI {}", cacheKey, uri);
        cache.put(createCacheKey(endpointId), uri);
    }

    @Override
    public void removeEndpoint(String endpointId, String uri) {
        String cacheKey = createCacheKey(endpointId);
        LOG.info("Removing endpoint '{}' from registry with URI {}", cacheKey, uri);
        cache.remove(cacheKey, uri);
    }

    @Override
    public Set<String> getEndpoints() {
        return cache.keySet();
    }

    private String createCacheKey(String endpointId) {
        String nodeId = System.getProperty(NODE_NAME_KEY);
        return nodeId.concat("/").concat(endpointId);
    }

}
