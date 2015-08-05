package com.github.aparnachaudhary.jaxrs.depot.core.registry.infinispan;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.util.PojoMapper;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachemanagerlistener.annotation.ViewChanged;
import org.infinispan.notifications.cachemanagerlistener.event.ViewChangedEvent;
import org.infinispan.remoting.transport.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Aparna Chaudhary
 */
@Listener
public class MemberDropListener {

    private static final Logger LOG = LoggerFactory.getLogger(MemberDropListener.class);

    private final Map<String, String> cache;

    public MemberDropListener(Map<String, String> cache) {
        this.cache = cache;
    }

    /**
     * Triggered when a view is changed, signaling that a member has joined or dropped from the cluster.
     *
     * @param event change details
     */
    @ViewChanged
    public void viewChanged(ViewChangedEvent event) {
        List<Address> newMembers = event.getNewMembers();
        List<Address> oldMembers = event.getOldMembers();
        LOG.info("Membership changed. New cluster size is " + newMembers.size());

        List<Address> dropped = minus(oldMembers, newMembers);
        List<Address> joined = minus(newMembers, oldMembers);
        for (Address address : dropped) {
            LOG.info("Drop Address {}", address);
            dropAllServices(address);
        }

        for (Address address : joined) {
            LOG.info("Joined Address {}", address);
        }
    }

    private List<Address> minus(List<Address> source, List<Address> remove) {
        List<Address> result = new ArrayList(source);
        result.removeAll(remove);
        return result;
    }

    void dropAllServices(Address address) {
        if (cache == null) {
            LOG.warn("Cache is null");
        } else {
            cache.keySet().stream().filter(
                    cacheKey -> {
                        try {
                            return PojoMapper.fromJson(cacheKey, EndpointId.class).getNodeId().equalsIgnoreCase(address.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }).forEach(cache::remove);
        }
    }
}


