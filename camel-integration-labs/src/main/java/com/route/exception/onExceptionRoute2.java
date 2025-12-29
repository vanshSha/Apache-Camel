package com.route.exception;

import static org.apache.camel.LoggingLevel.ERROR;
import static org.apache.camel.LoggingLevel.INFO;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

//@Component
public class onExceptionRoute2 extends RouteBuilder {

    public final static AtomicInteger counter = new AtomicInteger();

    @Override
    public void configure() throws Exception {
        // onException this is a rule
        onException(Exception.class) // THIS TYPE OF EXCEPTION OCCURS IF ANY ROUTE HANDLE
                .log(ERROR, "JSS: ${exception.message}")
                .handled(true) // IF THIS METHOD IS TRUE IT MEANS I WILL HIDE THE EXCEPTION
                .maximumRedeliveries(4)  // // This method means retry 4 times.
                .onRedelivery(exchange -> { // run on the first failure, exists ONLY to customize behavior during retries.
                    System.out.println("Try → FAIL → give up");
                });

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


/*
        //  Global exception handling with I am using that exception end points
        onException(RuntimeException.class)
                .log(ERROR, "ERROR HANDLED: ${exception.message}")
                .handled(true)
                .to("direct:errorHandler");   // endpoint IS USED correctly

        // Main route
        from("timer:test?period=2000")
                .log("Calling risky method")
                .bean(SimpleBean.class, "fail")
                .log("This line will NOT run if exception happens");

      //  Exception handling endpoint
        from("direct:errorHandler")
                .log("Inside error handler route");

 */
    }

}

