package com.github.aparnachaudhary.jaxrs.depot.examples.feedconsumer;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointAdded;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointRemoved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class EndpointEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(EndpointEventListener.class);

    public void endpointRemoved(@Observes EndpointRemoved endpointRemoved) {
        LOG.info("=========== endpointRemoved: {} ", endpointRemoved.getEndpointId());
    }

    public void endpointAdded(@Observes EndpointAdded endpointAdded) {
        LOG.info("=========== endpointAdded: {} ", endpointAdded.getEndpointId());
    }

}