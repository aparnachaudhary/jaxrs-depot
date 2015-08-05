package com.github.aparnachaudhary.jaxrs.depot.examples.feedconsumer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * REST Endpoint for feed consumer
 *
 * @author Aparna Chaudhary
 */
@Path("/consumer")
public interface FeedConsumerResource {

    @Path("goodbye")
    @GET
    @Produces({ "application/json" })
    String sayBye();

}
