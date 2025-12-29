package com.route.eip;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessagingBusEIP extends RouteBuilder {

    @Override
    public void configure() throws Exception {
//period=1000
        // Producer
        from("timer:messagingBus?repeatCount=1")
                .setBody(constant(new Date()))
                .log("PUBLISHED -> ${body}")

                // Publish to the BUS
                .to("seda:messageBus");

        from("seda:messageBus?multipleConsumers=true")
                .routeId("service-A")
                .log("SERVICE-A received -> ${body}");

        from("seda:messageBus?multipleConsumers=true")
                .routeId("service-B")
                .log("SERVICE-B received -> ${body}");

        from("seda:messageBus?multipleConsumers=true")
                .routeId("service-C")
                .log("SERVICE-C received -> ${body}");
    }
}




