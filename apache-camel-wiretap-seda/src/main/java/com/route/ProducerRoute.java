package com.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class ProducerRoute extends RouteBuilder {

    // Apache Camel -> Active MQ
    @Override
    public void configure() throws Exception {
        from("timer:producer?repeatCount=1")
                .routeId("producer-route")
                .setBody(simple("Hello ActiveMQ"))
                .log("sending message: ${body}")
                .to("activemq:queue:demo.queue");
    }

    // this is simple Apache Camel producer
//    @Override
//    public void configure() throws Exception {
//        from("timer:producer?period=3000")
//                .routeId("producer-route")
//                .setBody(simple("Message from Producer"))
//                .log("Produced: ${body}")
//                .to("direct:process");
//
//    }
}
