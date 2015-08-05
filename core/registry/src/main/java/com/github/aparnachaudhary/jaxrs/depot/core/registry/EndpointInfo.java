package com.github.aparnachaudhary.jaxrs.depot.core.registry;

import java.io.Serializable;
import java.util.HashSet;
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

    public static class EndpointInfoBuilder {
        private EndpointId endpointId;
        private String baseUri;
        private String serviceRoot;
        private EndpointStatus status;
        private Set<EndpointId> dependencies;

        public EndpointInfoBuilder() {
        }

        public static EndpointInfoBuilder newBuilder() {
            return new EndpointInfoBuilder();
        }

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
}
