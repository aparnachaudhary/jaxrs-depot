package com.github.aparnachaudhay.feedconsumer;

import com.github.aparnachaudhay.registry.EndpointRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Default service implementation for {@link FeedConsumerResource}
 *
 * @author Aparna Chaudhary
 */
@Stateless
public class FeedConsumerService implements FeedConsumerResource {
    private static final Logger
            LOG = LoggerFactory.getLogger(FeedConsumerService
            .class);

    @Inject
    private EndpointRegistry endpointRegistry;

    @Override
    public String sayBye() {
        for (String endpoint : endpointRegistry.getEndpoints()) {
            LOG.info("Endpoint Name {}", endpoint);
        }
        return "FeedConsumer";
    }
}
