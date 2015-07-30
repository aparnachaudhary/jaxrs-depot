package com.github.aparnachaudhay.feedconsumer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * REST Endpoint for feed consumer
 *
 * @author Aparna Chaudhary
 */
@Path("/")
public interface FeedConsumerResource {

    @Path("goodbye")
    @GET
    @Produces({ "application/json" })
    String sayBye();

}
