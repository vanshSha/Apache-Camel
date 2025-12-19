package com.seda;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SedaRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:producer?period=1000")
                .setBody(simple("Message at ${date:now:HH:mm:ss}"))
                .log("Producer sent : ${body}")
                // .to("direct:slowProcessor"); this part is before seda
                .to("seda:process?multipleConsumers=true");

        from("seda:process?multipleConsumers=true")
                .to("direct:complexProcessor");


        from("direct:complexProcessor")
                .log("Start processing: ${body}")
                .delay(5000)
//              .log("End processing: ${body}");
                .end(); // means end of the block

                 /*
                 multipleConsumers=true  ---> allows multiple Camel routes to consume messages from the same SEDA endpoint.

                 concurrentConsumers=2 -> threads  ---> (decides how many threads consume)
                 controls how many threads process messages in parallel within a single route
                 Means -> same route, two parallel worker thread,  concurrentConsumers

                 from is --> consumer end point
                 to is --> producer end point
                  */

    }
}
