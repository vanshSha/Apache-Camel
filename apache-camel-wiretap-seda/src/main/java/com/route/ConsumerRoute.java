package com.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class ConsumerRoute extends RouteBuilder {


    // ActiveMQ -> System
    @Override
    public void configure() throws Exception {
        from("activemq:queue:demo.queue")
                .routeId("consumer-route")
                .log("Received message: ${body}");
    }


    // this is simple Apache camel consumer
//    @Override
//    public void configure() throws Exception {
//
//        from("direct:process")
//                .routeId("consumer-route")
//                .log("Consumed: ${body}");
//    }
}
