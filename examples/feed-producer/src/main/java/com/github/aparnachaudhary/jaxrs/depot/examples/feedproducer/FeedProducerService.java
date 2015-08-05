package com.github.aparnachaudhary.jaxrs.depot.examples.feedproducer;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointInfo;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Default service implementation for {@link FeedProducerResource}
 *
 * @author Aparna Chaudhary
 */
@Stateless
public class FeedProducerService implements FeedProducerResource {

    private static final Logger LOG = LoggerFactory.getLogger(FeedProducerService.class);

    @Inject
    private EndpointRegistry endpointRegistry;

    @Override
    public String sayHi() {
        for (EndpointInfo endpoint : endpointRegistry.getEndpoints()) {
            LOG.info("Endpoint Name {}", endpoint);
        }
        return "FeedProducer";
    }

}
