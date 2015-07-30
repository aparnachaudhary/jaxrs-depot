package com.github.aparnachaudhay.feedconsumer;

/**
 * Default service implementation for {@link FeedConsumerResource}
 *
 * @author Aparna Chaudhary
 */
public class FeedConsumerService implements FeedConsumerResource {

    @Override
    public String sayBye() {
        return "FeedConsumer";
    }
}
