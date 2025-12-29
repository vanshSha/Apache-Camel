package com.route.eip;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicInteger;

//@Component
public class ThrottlerEIP extends RouteBuilder {

    final AtomicInteger sent = new AtomicInteger();
    final AtomicInteger received = new AtomicInteger();


    @Override
    public void configure() throws Exception {

        // producer
        /*
        messages per second = 1000 / period
        Here I am sending   0.1sec = 1 message  . it means 1sec = 10 messages
        direct - synchronous, blocking
        seda - async, queued

        Mental Model

        Fast Producer  ───────►  [ THROTTLER ]  ───────►  Slow Consumer
                     controls rate here

         */
        from("timer:Throttler?period=100")
                .process(exchange -> {
                    int s = sent.incrementAndGet();
                    System.out.println("PRODUCED -> Sent [" + s + "]");;})
                .to("seda:throttlerEIP");


        // consumer
        /*
        Here I am consuming message pre sec - 1 message
         */
        from("seda:throttlerEIP")
                .throttle(1)  // consume 10 message
                .timePeriodMillis(1000) // in 1 seconds
                .process(exchange -> {
                    int r = received.incrementAndGet();
                    System.out.println("CONSUMED -> Received [" + r + "]");
                });




    }
}
