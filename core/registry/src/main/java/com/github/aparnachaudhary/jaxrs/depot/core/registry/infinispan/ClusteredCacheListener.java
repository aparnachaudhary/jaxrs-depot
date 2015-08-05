package com.github.aparnachaudhary.jaxrs.depot.core.registry.infinispan;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointAdded;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointEvent;
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

import javax.enterprise.event.Event;
import java.io.IOException;

/**
 * @author Aparna Chaudhary
 */
@Listener
public class ClusteredCacheListener {

    private static final Logger LOG = LoggerFactory.getLogger(ClusteredCacheListener.class);

    private Event<EndpointEvent> event;

    public ClusteredCacheListener(Event<EndpointEvent> event) {
        this.event = event;
    }

    @CacheEntryModified
    public void onCacheEntryModifiedEvent(CacheEntryModifiedEvent modifiedEvent) {
        if (modifiedEvent.isOriginLocal()) {
            LOG.info("entry '{}' modified", modifiedEvent.getKey());
            try {
                event.fire(new EndpointStatusChanged(PojoMapper.fromJson(modifiedEvent.getKey().toString(), EndpointId.class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @CacheEntryCreated
    public void onCacheEntryCreatedEvent(CacheEntryCreatedEvent createdEvent) {
        LOG.info("entry '{}' created", createdEvent.getKey());
        try {
            event.fire(new EndpointAdded(PojoMapper.fromJson(createdEvent.getKey().toString(), EndpointId.class)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @CacheEntryRemoved
    public void onCacheEntryRemovedEvent(CacheEntryRemovedEvent removedEvent) {
        LOG.info("entry '{}' removed", removedEvent.getKey());
        try {
            event.fire(new EndpointRemoved(PojoMapper.fromJson(removedEvent.getKey().toString(), EndpointId.class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
