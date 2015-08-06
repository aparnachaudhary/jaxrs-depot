package com.github.aparnachaudhary.jaxrs.depot.core.registry.event;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;

/**
 * @author Aparna Chaudhary
 */
public interface EndpointEvent {

    EndpointId getEndpointId();

    void setEndpointId(EndpointId endpointId);
}
