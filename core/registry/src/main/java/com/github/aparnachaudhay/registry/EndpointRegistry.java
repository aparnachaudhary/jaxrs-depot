package com.github.aparnachaudhay.registry;

import java.util.Set;

/**
 * @author Aparna Chaudhary
 */
public interface EndpointRegistry {

    /**
     * Add an endpoint to the registry.  If the endpoint already exists then nothing is added.
     *
     * @param uri the endpoint to add
     */
    public void addEndpoint(String name, String uri);

    /**
     * Removes an endpoint from the registry if it exists.
     *
     * @param uri the endpoint to remove.
     */
    public void removeEndpoint(String name, String uri);

    /**
     * Returns a list of all registered endpoints.
     *
     * @return list of registered endpoints
     */
    public Set<String> getEndpoints();
}
