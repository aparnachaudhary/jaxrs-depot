package com.github.aparnachaudhary.jaxrs.depot.examples.feedproducer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * REST Endpoint for feed producer
 *
 * @author Aparna Chaudhary
 */
@Path("/producer")
public interface FeedProducerResource {

    @Path("greet")
    @GET
    @Produces({ "application/json" }) String sayHi();

}
