package com.github.aparnachaudhary.jaxrs.depot.examples.feedproducer;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointInfo;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * @author Aparna Chaudhary
 */
@ApplicationScoped
public class ResourceRegistry {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceRegistry.class);

    private static final String NODE_NAME = "jboss.node.name";
    private static final String HTTP_PORT = "jboss.http.port";
    private static final String ENDPOINT_NAME = "producer";
    private static final String APP_NAME = "feed-producer";

    @Inject
    private EndpointRegistry endpointRegistry;

    /**
     * @param init
     */
    public void onInitialize(@Observes @Initialized(ApplicationScoped.class) Object init) {
        LOG.info("Started : {}", init);
        register();
    }

    /**
     * @param init
     */
    public void onDestroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        LOG.info("Destroyed : {}", init);
        unregister();
    }

    public void register() {
        EndpointInfo endpointInfo = getLocalEndpoint();
        endpointRegistry.addEndpoint(getLocalEndpoint());
    }

    private void unregister() {
        EndpointInfo endpointInfo = endpointRegistry.getEndpoint(getLocalEndpoint().getEndpointId());
        if (endpointInfo != null) {
            endpointRegistry.removeEndpoint(endpointInfo.getEndpointId());
        }
    }

    private EndpointInfo getLocalEndpoint() {
        EndpointId endpointId = EndpointId.EndpointIdBuilder.newBuilder().setNodeName(System.getProperty(NODE_NAME)).setAppName(APP_NAME)
                .setEndpointName(ENDPOINT_NAME)
                .createEndpointId();
        return EndpointInfo.EndpointInfoBuilder.newBuilder().setEndpointId(endpointId).setBaseUri(getBaseUri("feed-producer/rest/"))
                .setServiceRoot("producer").createEndpointInfo();
    }

    private String getBaseUri(String contextRoot) {
        String baseUri = null;
        try {
            Object port = System.getProperty(HTTP_PORT);
            Object host = ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName("jboss.as:interface=public"), "inet-address");
            baseUri = String.format("http:/%s:%s/%s", host, port, contextRoot);
        } catch (MBeanException | AttributeNotFoundException | InstanceNotFoundException | ReflectionException | MalformedObjectNameException e) {
            e.printStackTrace();
        }
        return baseUri;
    }

}
