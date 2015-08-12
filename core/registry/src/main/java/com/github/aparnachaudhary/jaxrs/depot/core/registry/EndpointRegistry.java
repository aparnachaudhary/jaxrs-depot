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

    /**
     * Returns endpoint matching the given application id and endpoint id
     *
     * @param appId      application id
     * @param endpointId endpoint id
     * @return true if endpoint is registered in the cache; false otherwise
     */
    boolean existsEndpoint(String appId, String endpointId);

    /**
     * Returns endpoint info for the given endpoint id.
     *
     * @param endpointId endpoint id
     * @return {@link EndpointInfo}; null if none found
     */
    EndpointInfo getEndpoint(EndpointId endpointId);

    /**
     * Verifies if the endpoints for dependencies are already registered.
     *
     * @param endpointInfo endpoint for which dependencies are verified
     * @return true if dependencies are registred; false otherwise
     */
    boolean existsDependencies(EndpointInfo endpointInfo);
}
