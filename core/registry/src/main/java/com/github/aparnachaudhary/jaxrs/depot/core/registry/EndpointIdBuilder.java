package com.github.aparnachaudhary.jaxrs.depot.core.registry;

public class EndpointIdBuilder {
    private String nodeId;
    private String appId;
    private String endpointId;

    public EndpointIdBuilder setNodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    public EndpointIdBuilder setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public EndpointIdBuilder setEndpointId(String endpointId) {
        this.endpointId = endpointId;
        return this;
    }

    public EndpointId createEndpointId() {
        return new EndpointId(nodeId, appId, endpointId);
    }
}