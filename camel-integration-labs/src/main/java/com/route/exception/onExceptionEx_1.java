package com.route.exception;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class onExceptionEx_1 extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .continued(true);
        // true ignore an exception and keep going
        // false notice and stop

        from("timer:produce?period=1000")
                .to("direct:consumer");

        from("direct:consumer")
                .log("Step 1")
                .bean(SimpleBean.class, "fail")   // exception here
                .log("Step 2");                // WILL run

    }
}
