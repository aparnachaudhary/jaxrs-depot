package com.github.aparnachaudhary.jaxrs.depot.examples.feedconsumer;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointInfo;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointRegistry;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointAdded;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointRemoved;
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
    private static final String PRODUCER_APP_NAME = "feed-producer";
    private static final String PRODUCER_ENDPOINT_NAME = "producer";
    private static final String APP_NAME = "feed-consumer";
    private static final String ENDPOINT_NAME = "consumer";

    @Inject
    private EndpointRegistry endpointRegistry;

    /**
     *
     * @param init
     */
    public void onInitialize(@Observes @Initialized(ApplicationScoped.class) Object init) {
        LOG.debug("CDI: started : {}", init);
        register();
    }

    /**
     *
     * @param endpointAdded
     */
    public void onEndpointAdded(@Observes EndpointAdded endpointAdded) {
        EndpointId endpointId = endpointAdded.getEndpointId();
        LOG.info("=========== endpointAdded: {} ===========", endpointId);
        if (isProducerEndpoint(endpointId)) {
            register();
        }
    }

    /**
     *
     * @param endpointRemoved
     */
    public void onEndpointRemoved(@Observes EndpointRemoved endpointRemoved) {
        EndpointId endpointId = endpointRemoved.getEndpointId();
        LOG.info("=========== endpointRemoved: {} ===========", endpointId);
        if (isProducerEndpoint(endpointId)) {
            if (!dependenciesExist()) {
                unregister();
            }
        }
    }

    /**
     *
     * @param init
     */
    public void onDestroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        LOG.info("CDI: destroyed : {}", init);
    }

    private void register() {
        EndpointId producerEndpointId = EndpointId.EndpointIdBuilder.newBuilder().setAppName(PRODUCER_APP_NAME).setEndpointName(PRODUCER_ENDPOINT_NAME)
                .createEndpointId();
        if (endpointRegistry.existsEndpoint(producerEndpointId.getAppName(), producerEndpointId.getEndpointName())) {
            EndpointId consumerEndpointId = getLocalEndpointId();
            if (!endpointRegistry.existsEndpoint(consumerEndpointId.getAppName(), consumerEndpointId.getEndpointName())) {
                EndpointInfo endpointInfo = EndpointInfo.EndpointInfoBuilder.newBuilder().setEndpointId(consumerEndpointId)
                        .setBaseUri(getBaseUri("feed-consumer/rest/"))
                        .setServiceRoot("consumer").addDependency(producerEndpointId).createEndpointInfo();
                endpointRegistry.addEndpoint(endpointInfo);
            }
        }
    }

    private void unregister() {
        EndpointInfo consumerEndpointInfo = endpointRegistry.getEndpoint(getLocalEndpointId());
        if (consumerEndpointInfo != null) {
            endpointRegistry.removeEndpoint(getLocalEndpointId());
        }
    }

    private boolean isProducerEndpoint(EndpointId endpointId) {
        return endpointId.getAppName().equalsIgnoreCase(PRODUCER_APP_NAME) && endpointId.getEndpointName().equalsIgnoreCase(PRODUCER_ENDPOINT_NAME);
    }

    private boolean dependenciesExist() {
        EndpointId producerEndpointId = EndpointId.EndpointIdBuilder.newBuilder().setAppName(PRODUCER_APP_NAME).setEndpointName(PRODUCER_ENDPOINT_NAME)
                .createEndpointId();
        return endpointRegistry.existsEndpoint(producerEndpointId.getAppName(), producerEndpointId.getEndpointName());
    }

    private EndpointId getLocalEndpointId() {
        return EndpointId.EndpointIdBuilder.newBuilder().setNodeName(System.getProperty(NODE_NAME)).setAppName(APP_NAME)
                .setEndpointName(ENDPOINT_NAME)
                .createEndpointId();
    }

    private String getBaseUri(String contextRoot) {
        String baseUri = null;
        try {
            Object port = System.getProperty(HTTP_PORT);
            Object host = ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName("jboss.as:interface=public"), "inet-address");
            baseUri = String.format("http:/%s:%s/%s", host, port, contextRoot);
        } catch (MBeanException | AttributeNotFoundException | ReflectionException | InstanceNotFoundException | MalformedObjectNameException e) {
            e.printStackTrace();
        }
        return baseUri;
    }

}
