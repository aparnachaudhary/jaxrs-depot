package com.github.aparnachaudhary.jaxrs.depot.core.registry.event;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;

/**
 * @author Aparna Chaudhary
 */
public class EndpointAdded implements EndpointEvent{

    private EndpointId endpointId;

    public EndpointAdded(EndpointId endpointId) {
        this.endpointId = endpointId;
    }

    public EndpointId getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(EndpointId endpointId) {
        this.endpointId = endpointId;
    }
}
