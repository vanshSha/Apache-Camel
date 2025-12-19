package com.wiretab;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

//@Component
public class WireTabRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("activemq:queue:sender.queue")
                .routeId("sender-route")
                .unmarshal().json(JsonLibrary.Jackson, TransactionDto.class)
                // this is wireTab
                .wireTap("direct:audit-transaction")
                .marshal().json(JsonLibrary.Jackson, TransactionDto.class)
                .to("activemq:queue:receiver.queue")
                .log(LoggingLevel.INFO, "Money Transferred: ${body}");


        // this is use for read from direct
        from("direct:audit-transaction")
                .marshal().json(JsonLibrary.Jackson, TransactionDto.class)
                // send into audit-transaction.queue
                .to("activemq:queue:audit-transaction.queue");

/*
- unmarshal() - data format (JSON / XML / bytes) —> convert into Java Object
- marshal()- Java object —>   convert into  →  data format (JSON / XML / bytes)
		        - wireTap("direct:audit-transaction");
				direct -> is NOT a queue, is NOT async by default, works only inside the same Camel context If no matching from("direct:...") → runtime error, In-memory route call
                direct  -> is used to call another Camel route in memory.
				rest of the part ->  is just a developer-defined endpoint name used to connect Camel routes.
 */

    }
}
