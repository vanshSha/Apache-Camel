package com.component;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class HelloJavaDsl extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        rest("api/")
                .get("/hello/{name}")
                .to("direct:say-hello");

        from("direct:say-hello")
                .process(exchange -> {

                /*
                It reads a value from the HTTP request headers (or any Camel message header) and converts it to a String.
                When you expect data to come from a HEADER, not from the body.
                 */
                    String name = exchange.getIn()
                            .getHeader("name", String.class);

                    exchange.getMessage()
                            .setBody("Hello " + name.toUpperCase());
                });
    }
}
