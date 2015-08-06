package com.github.aparnachaudhary.jaxrs.depot.examples.feedconsumer;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointAdded;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointRemoved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class EndpointEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(EndpointEventListener.class);

    @Inject
    ResourceRegistry resourceRegistry;

    public void endpointRemoved(@Observes EndpointRemoved endpointRemoved) {
        EndpointId endpointId = endpointRemoved.getEndpointId();
        LOG.info("=========== endpointRemoved: {} ", endpointId);
        if (endpointId.getAppName().equalsIgnoreCase("feed-producer") && endpointId.getEndpointName().equalsIgnoreCase("producer")) {
            if (!resourceRegistry.dependenciesExist()) {
                resourceRegistry.unregister();
            }
        }
    }

    public void endpointAdded(@Observes EndpointAdded endpointAdded) {
        EndpointId endpointId = endpointAdded.getEndpointId();
        LOG.info("=========== endpointAdded: {} ", endpointId);
        if (endpointId.getAppName().equalsIgnoreCase("feed-producer") && endpointId.getEndpointName().equalsIgnoreCase("producer")) {
            resourceRegistry.register();
        }
    }

}