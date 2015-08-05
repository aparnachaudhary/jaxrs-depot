package com.github.aparnachaudhary.jaxrs.depot.core.registry;

import java.util.HashSet;
import java.util.Set;

public class EndpointInfoBuilder {
    private EndpointId endpointId;
    private String baseUri;
    private String serviceRoot;
    private EndpointStatus status;
    private Set<EndpointId> dependencies;

    public EndpointInfoBuilder setEndpointId(EndpointId endpointId) {
        this.endpointId = endpointId;
        return this;
    }

    public EndpointInfoBuilder setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public EndpointInfoBuilder setServiceRoot(String serviceRoot) {
        this.serviceRoot = serviceRoot;
        return this;
    }

    public EndpointInfoBuilder setStatus(EndpointStatus status) {
        this.status = status;
        return this;
    }

    public EndpointInfoBuilder addDependency(EndpointId dependency) {
        if (this.dependencies == null) {
            this.dependencies = new HashSet<>();
        }
        this.dependencies.add(dependency);
        return this;
    }

    public EndpointInfoBuilder setDependencies(Set<EndpointId> dependencies) {
        this.dependencies = dependencies;
        return this;
    }

    public EndpointInfo createEndpointInfo() {
        return new EndpointInfo(endpointId, baseUri, serviceRoot, status, dependencies);
    }
}