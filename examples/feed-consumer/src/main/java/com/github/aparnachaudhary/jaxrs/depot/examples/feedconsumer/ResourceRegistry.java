package com.github.aparnachaudhary.jaxrs.depot.examples.feedconsumer;

import com.github.aparnachaudhary.jaxrs.depot.core.registry.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created by Aparna on 8/2/15.
 */
@Singleton
@Startup
public class ResourceRegistry {

    private static final String NODE_NAME = "jboss.node.name";
    private static final String HTTP_PORT = "jboss.http.port";

    @Inject
    private EndpointRegistry endpointRegistry;

    @PostConstruct
    public void register() {
        EndpointId endpointId = new EndpointIdBuilder().setNodeId(System.getProperty(NODE_NAME)).setAppId("feed-consumer").setEndpointId("consumer")
                .createEndpointId();
        EndpointId producerEndpointId = new EndpointIdBuilder().setAppId("feed-producer").setEndpointId("producer")
                .createEndpointId();
        EndpointInfo endpointInfo = new EndpointInfoBuilder().setEndpointId(endpointId).setBaseUri(getBaseUri("feed-consumer/rest/"))
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
