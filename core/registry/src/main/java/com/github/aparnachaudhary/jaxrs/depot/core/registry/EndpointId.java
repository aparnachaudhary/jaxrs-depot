package com.github.aparnachaudhary.jaxrs.depot.core.registry;

import java.io.Serializable;

public class EndpointId implements Serializable {

    private String nodeName;
    private String appName;
    private String endpointName;

    private EndpointId() {
    }

    public EndpointId(String nodeName, String appName, String endpointName) {
        this.nodeName = nodeName;
        this.appName = appName;
        this.endpointName = endpointName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getAppName() {
        return appName;
    }

    public String getEndpointName() {
        return endpointName;
    }

    @Override
    public String toString() {
        return "EndpointId{" +
                "nodeName='" + nodeName + '\'' +
                ", appName='" + appName + '\'' +
                ", endpointName='" + endpointName + '\'' +
                '}';
    }

    public static final class EndpointIdBuilder {

        private String nodeName;
        private String appName;
        private String endpointName;

        private EndpointIdBuilder() {
        }

        public static EndpointIdBuilder newBuilder() {
            return new EndpointIdBuilder();
        }

        public EndpointIdBuilder setNodeName(String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public EndpointIdBuilder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public EndpointIdBuilder setEndpointName(String endpointName) {
            this.endpointName = endpointName;
            return this;
        }

        public EndpointId createEndpointId() {
            return new EndpointId(nodeName, appName, endpointName);
        }
    }
}