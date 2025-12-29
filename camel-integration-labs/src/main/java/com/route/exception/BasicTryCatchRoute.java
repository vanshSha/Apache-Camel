package com.route.exception;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.camel.LoggingLevel.INFO;

//@Component
public class BasicTryCatchRoute extends RouteBuilder {

    final static AtomicInteger counter = new AtomicInteger();

    @Override
    public void configure() throws Exception {
        from("timer:time?period=1000")
                .process(exchange -> exchange.getIn().setBody(new Date()))
                .doTry() // this is try block handel risky block
                .bean(HelloBean.class, "callBad")
                .doCatch(Exception.class) // this method handling code
                .to("direct:exceptionHandler")
                .end()
                .log(INFO, ">> ${header.firedTime} >> ${body}")
                .to("log:reply");


    }
}
