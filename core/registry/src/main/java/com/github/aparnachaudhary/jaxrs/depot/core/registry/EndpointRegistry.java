package com.github.aparnachaudhary.jaxrs.depot.core.registry;

import java.util.Collection;

/**
 * @author Aparna Chaudhary
 */
public interface EndpointRegistry {

    /**
     * Add an endpoint to the registry.  If the endpoint already exists then nothing is added.
     *
     * @param endpointInfo the endpoint to add
     */
    void addEndpoint(EndpointInfo endpointInfo);

    /**
     * Removes an endpoint from the registry if it exists.
     *
     * @param endpointId the endpoint to remove.
     */
    void removeEndpoint(EndpointId endpointId);

    /**
     * Returns a list of all registered endpoints.
     *
     * @return list of registered endpoints
     */
    Collection<EndpointInfo> getEndpoints();
}
