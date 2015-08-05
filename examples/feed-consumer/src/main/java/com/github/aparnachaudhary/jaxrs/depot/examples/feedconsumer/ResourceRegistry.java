package com.github.aparnachaudhary.jaxrs.depot.examples.feedconsumer;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointId;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointInfo;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.EndpointRegistry;
import com.github.aparnachaudhary.jaxrs.depot.core.registry.event.EndpointAdded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created by Aparna on 8/2/15.
 */
@Singleton
@Startup
public class ResourceRegistry {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceRegistry.class);

    private static final String NODE_NAME = "jboss.node.name";
    private static final String HTTP_PORT = "jboss.http.port";

    @Inject
    private EndpointRegistry endpointRegistry;

    @PostConstruct
    public void register() {
        EndpointId endpointId = EndpointId.EndpointIdBuilder.newBuilder().setNodeId(System.getProperty(NODE_NAME)).setAppId("feed-consumer")
                .setEndpointId("consumer")
                .createEndpointId();
        EndpointId producerEndpointId = EndpointId.EndpointIdBuilder.newBuilder().setAppId("feed-producer").setEndpointId("producer")
                .createEndpointId();
        EndpointInfo endpointInfo = EndpointInfo.EndpointInfoBuilder.newBuilder().setEndpointId(endpointId).setBaseUri(getBaseUri("feed-consumer/rest/"))
                .setServiceRoot("consumer").addDependency(producerEndpointId).createEndpointInfo();
        endpointRegistry.addEndpoint(endpointInfo);
    }

    private String getBaseUri(String contextRoot) {
        String baseUri = null;
        try {
            Object port = System.getProperty(HTTP_PORT);
            Object host = ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName("jboss.as:interface=public"), "inet-address");
            baseUri = String.format("http:/%s:%s/%s", host, port, contextRoot);
        } catch (MBeanException e) {
            e.printStackTrace();
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        return baseUri;
    }

}
