package com.github.aparnachaudhary.jaxrs.depot.core.registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Aparna Chaudhary
 */
public class EndpointInfo implements Serializable {

    private EndpointId endpointId;
    private String baseUri;
    private String serviceRoot;
    private EndpointStatus status;

    private Set<EndpointId> dependencies;

    public EndpointInfo() {
    }

    public EndpointInfo(EndpointId endpointId, String baseUri, String serviceRoot, EndpointStatus status, Set<EndpointId> dependencies) {
        this.endpointId = endpointId;
        this.baseUri = baseUri;
        this.serviceRoot = serviceRoot;
        this.status = status;
        this.dependencies = dependencies;
    }

    public EndpointId getEndpointId() {
        return endpointId;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public String getServiceRoot() {
        return serviceRoot;
    }

    public EndpointStatus getStatus() {
        return status;
    }

    public Set<EndpointId> getDependencies() {
        return dependencies;
    }

    @Override public String toString() {
        return "EndpointInfo{" +
                "endpointId=" + endpointId +
                ", baseUri='" + baseUri + '\'' +
                ", serviceRoot='" + serviceRoot + '\'' +
                ", status=" + status +
                ", dependencies=" + dependencies +
                '}';
    }
}
