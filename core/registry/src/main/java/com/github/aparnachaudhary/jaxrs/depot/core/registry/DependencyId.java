package com.github.aparnachaudhary.jaxrs.depot.core.registry;

import java.io.Serializable;

/**
 * @author Aparna Chaudhary
 */
public class DependencyId implements Serializable {

    private String appName;
    private String endpointName;

    private DependencyId() {
    }

    public DependencyId(String appName, String endpointName) {
        this.appName = appName;
        this.endpointName = endpointName;
    }

    public String getAppName() {
        return appName;
    }

    public String getEndpointName() {
        return endpointName;
    }

    @Override public String toString() {
        return "DependencyId{" +
                "appName='" + appName + '\'' +
                ", endpointName='" + endpointName + '\'' +
                '}';
    }

    public static final class DependencyIdBuilder {
        private String appName;
        private String endpointName;

        private DependencyIdBuilder() {
        }

        public static DependencyIdBuilder newBuilder() {
            return new DependencyIdBuilder();
        }

        public DependencyIdBuilder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public DependencyIdBuilder setEndpointName(String endpointName) {
            this.endpointName = endpointName;
            return this;
        }

        public DependencyId createDependencyId() {
            return new DependencyId(appName, endpointName);
        }
    }
}
