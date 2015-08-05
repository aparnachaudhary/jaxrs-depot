package com.github.aparnachaudhary.jaxrs.depot.core.registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class EndpointId implements Serializable {

    private String nodeId;
    private String appId;
    private String endpointId;

    public EndpointId() {
    }

    public EndpointId(String nodeId, String appId, String endpointId) {
        this.nodeId = nodeId;
        this.appId = appId;
        this.endpointId = endpointId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getAppId() {
        return appId;
    }

    public String getEndpointId() {
        return endpointId;
    }

    public boolean startsWith(String s) {
        return false;
    }

    public String createCacheKey() {
        return nodeId.concat("_").concat(appId).concat("_").concat(endpointId);
    }

    @Override public String toString() {
        return "EndpointId{" +
                "nodeId='" + nodeId + '\'' +
                ", appId='" + appId + '\'' +
                ", endpointId='" + endpointId + '\'' +
                '}';
    }
}