package com.github.aparnachaudhay.feedproducer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * REST Endpoint for feed producer
 *
 * @author Aparna Chaudhary
 */
@Path("/")
public interface FeedProducerResource {

    @Path("greet")
    @GET
    @Produces({ "application/json" }) String sayHi();

}
