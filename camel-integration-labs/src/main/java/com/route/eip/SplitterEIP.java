package com.route.eip;

import org.apache.camel.builder.RouteBuilder;

import static org.apache.camel.LoggingLevel.INFO;

//@Component
//@Slf4j
public class SplitterEIP extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // producer
        from("timer:time?repeatCount=1")
                .setBody(constant("Apple,Banana,Orange"))
                .to("direct:orders1");

        // consumer
        from("direct:orders1")
                .split(body().tokenize(","))
                .log(INFO , "Processing item: ${body}")
                .to("mock:items")
                .end();
    }
}
