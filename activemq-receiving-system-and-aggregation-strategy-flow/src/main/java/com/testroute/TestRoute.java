package com.testroute;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class TestRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:produce?period=3000")
                .setBody(constant("I am testing"))
                .log("Sending : ${body}")
                .to("direct:access");
    }
}
