package com.testroute;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class Consumer extends RouteBuilder {

    @Override
    public void configure() throws Exception {
            from("activemq:queue:mac.queue")
                    .routeId("consumer-route")
                    .log("Getting message from ActiveMQ to Receiving System: ${body}");
    }
}
