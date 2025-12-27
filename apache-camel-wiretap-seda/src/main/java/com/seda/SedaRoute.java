package com.seda;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SedaRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // producer route
        from("timer:producer?period=1000")
                .setBody(simple("Message at ${date:now:HH:mm:ss}"))
                .log("Producer sent : ${body}")
                // .to("direct:slowProcessor"); here I want to sync
                .to("seda:process?multipleConsumers=true"); // Here I want to send message into in-memory

        // consumer route
        from("seda:process?multipleConsumers=true")
                .to("direct:complexProcessor");


        from("direct:complexProcessor")
                .log("Start processing: ${body}")
                .delay(5000)
//              .log("End processing: ${body}");
                .end(); // means end of the block

                 /*
                 multipleConsumers=true  ---> allows more than one consumer to read messages
                 from the same endpoint in parallel.

                 concurrentConsumers=2 -> threads  ---> (decides how many threads consume)
                 controls how many threads process messages in parallel within a single route
                 Means -> same route, two parallel worker thread,  concurrentConsumers

                 from is --> consumer end point
                 to is --> producer end point
                  */

    }
}
