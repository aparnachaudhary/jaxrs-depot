package com.github.aparnachaudhary.jaxrs.depot.core.registry.event;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;

/**
 * Created by Aparna on 8/5/15.
 */
public interface EndpointEvent {

    EndpointId getEndpointId();

    void setEndpointId(EndpointId endpointId);
}
