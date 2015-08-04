package com.github.aparnachaudhay.feedproducer;

import com.github.aparnachaudhay.registry.EndpointRegistry;
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
        for (String endpoint : endpointRegistry.getEndpoints()) {
            LOG.info("Endpoint Name {}", endpoint);
        }
        return "FeedProducer";
    }
}
