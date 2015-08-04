package com.github.aparnachaudhay.feedproducer;

import com.github.aparnachaudhay.registry.EndpointRegistry;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Created by Aparna on 8/2/15.
 */
@Singleton
@Startup
public class ResourceRegistry {

    @Inject
    private EndpointRegistry endpointRegistry;

    @PostConstruct
    public void register() {
        endpointRegistry.addEndpoint("producer", "http://localhost:8080/feed-producer/rest/producer/");
    }
}
