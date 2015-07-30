package com.github.aparnachaudhay.feedproducer;

/**
 * Default service implementation for {@link FeedProducerResource}
 *
 * @author Aparna Chaudhary
 */
public class FeedProducerService implements FeedProducerResource {

    @Override
    public String sayHi() {
        return "FeedProducer";
    }
}
