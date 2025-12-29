package com.route.eip.interceptEIP;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class InterceptEIP extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /* This is global Intercept EIP
        intercept()
                .log("GLOBAL INTERCEPT ${body}");

        from("timer:p1?repeatCount=1")
                .setBody(simple(" Message from Producer-1"))
                .to("direct:service1");

        from("direct:service1")
                .log("SERVICE-1 -> ${body}");
         */
//       -------------------------------------------------------------------------------------
        /* Intercept from a specific producer
        interceptFrom("timer:p2*")
                .setHeader("source", constant("TIMER"))
                .log("INTERCEPT FROM -> ${body}");

        from("timer:p2?period=1000")
                .setBody(simple("Message from Producer-2"))
                .to("direct:service2");

        from("direct:service2")
                .log("SERVICE-2 -> body= ${body}, source= ${header.source}");
         */
//       -------------------------------------------------------------------------------------

        // Intercept Sending to a Specific Endpoint
        interceptSendToEndpoint("direct:learning")
                .log("BEFORE EXTERNAL CALL -> ${body}")
                .setHeader("intercepted", constant(true));

        // Producer
        from("timer:p3?period=4000")
                .setBody(simple("Order Event"))
                .to("direct:learning");

        // Consumer
        from("direct:learning")
                .log("EXTERNAL SERVICE -> ${body}, intercepted=${header.intercepted}");

    }
}
