package com.github.aparnachaudhary.jaxrs.depot.core.registry.infinispan;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointAdded;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointRemoved;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointStatusChanged;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.util.PojoMapper;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.spi.CDI;
import java.io.IOException;

/**
 * @author Aparna Chaudhary
 */
@Listener
public class ClusteredCacheListener {

    private static final Logger LOG = LoggerFactory.getLogger(ClusteredCacheListener.class);

    @CacheEntryModified
    public void onCacheEntryModifiedEvent(CacheEntryModifiedEvent event) {
        if (event.isOriginLocal()) {
            LOG.info("entry '{}' modified", event.getKey());

            try {
                CDI.current().getBeanManager().fireEvent(new EndpointStatusChanged(PojoMapper.fromJson(event.getKey().toString(), EndpointId.class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @CacheEntryCreated
    public void onCacheEntryCreatedEvent(CacheEntryCreatedEvent event) {
        LOG.info("entry '{}' created", event.getKey());
        try {
            CDI.current().getBeanManager().fireEvent(new EndpointAdded(PojoMapper.fromJson(event.getKey().toString(), EndpointId.class)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @CacheEntryRemoved
    public void onCacheEntryRemovedEvent(CacheEntryRemovedEvent event) {
        LOG.info("entry '{}' removed", event.getKey());
        try {
            CDI.current().getBeanManager().fireEvent(new EndpointRemoved(PojoMapper.fromJson(event.getKey().toString(), EndpointId.class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
