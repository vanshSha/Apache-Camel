package com.testroute;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class Producer extends RouteBuilder {

    // From Apache Camel to ActiveMQ
    @Override
    public void configure() throws Exception {

        from("timer:producer?repeatCount=1")
                .routeId("producer-route")
                .setBody(constant("Hello ActiveMQ"))
                .log("System to ActiveMQ: ${body}")
                .to("activemq:queue:mac.queue");
    }
}
