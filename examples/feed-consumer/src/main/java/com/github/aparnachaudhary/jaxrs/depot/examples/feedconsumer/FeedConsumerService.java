package com.github.aparnachaudhary.jaxrs.depot.examples.feedconsumer;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointInfo;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointRegistry;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointAdded;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointRemoved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Default service implementation for {@link FeedConsumerResource}
 *
 * @author Aparna Chaudhary
 */
@Stateless
public class FeedConsumerService implements FeedConsumerResource {

    private static final Logger LOG = LoggerFactory.getLogger(FeedConsumerService.class);

    @Inject
    private EndpointRegistry endpointRegistry;

    @Override
    public String sayBye() {
        for (EndpointInfo endpoint : endpointRegistry.getEndpoints()) {
            LOG.info("Endpoint Name {}", endpoint);
        }
        return "FeedConsumer";
    }

}
