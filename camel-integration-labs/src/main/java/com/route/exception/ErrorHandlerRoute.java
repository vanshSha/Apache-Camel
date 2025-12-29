package com.route.exception;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.camel.LoggingLevel.INFO;

//@Component
public class ErrorHandlerRoute extends RouteBuilder {

    final static AtomicInteger counter = new AtomicInteger(1);

    @Override
    public void configure() throws Exception {
        errorHandler(
                deadLetterChannel("direct:exceptionHandler") // deadLetterChannel - I will handle route don't kill route
                // The URI inside deadLetterChannel() tells Camel where to send messages that permanently fail.
                //The URI inside deadLetterChannel() tells Camel where to send the failed Exchange
                        // after retries are exhausted. The exception is attached to that Exchange.
                        .maximumRedeliveries(2));

        from("timer:time?period=1000")
                .process(exchange -> exchange.getIn().setBody(new Date()))
                .choice()
                .when(e -> counter.incrementAndGet() % 2 == 0)
                .bean(HelloBean.class, "callBad")
                .otherwise()
                .bean(HelloBean.class, "callGood")
                .end()
                .log(INFO, ">> ${header.firedTime} >> ${body}")
                .to("log:reply");


//        from("direct:exceptionHandler")
//                .log("Exception class   : ${exception.class}")
//                .log("Exception message : ${exception.message}")
//                .log("Failed body       : ${body}");

    }

}
