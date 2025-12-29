package com.route.jms;

import lombok.extern.slf4j.Slf4j;
import static org.apache.camel.LoggingLevel.INFO;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JMSRoute extends RouteBuilder {
// Java Message System
    @Override
    public void configure() throws Exception {
        from("jms:queue:orders")
                .log(INFO, "Got a message : ${body}");
    }
}
