package com.multicastroute;

import static org.apache.camel.LoggingLevel.ERROR;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

s@Component
public class MulticastRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        AtomicInteger orderID = new AtomicInteger(100);

        from("timer:orders?period=2000")
                .process(exchange ->
                        exchange.getIn().setBody("{order-id: '" + (orderID.getAndIncrement()) + " ', " +
                                "price : '$20.00'}"))
                .multicast().parallelProcessing()
                // parallelProcessing -> rum multiple message at the same time
                .to("direct:payment", "direct:stock-allocation");

        from("direct:payment")
                .process(exchange -> enrich(exchange, "Paid"))
                .log(ERROR, "${body}");

        from("direct:stock-allocation")
                .process(exchange -> enrich(exchange, "Allocated"))
                .log(ERROR, "${body}");


    }

    /*
    Reads the message body from Camel Exchange
    Treats JSON as a plain String
    Manually appends a status field
    Puts the modified String back into the message
     */
    private void enrich(Exchange exchange, String statusValue) {
        Message in = exchange.getIn();
        String order = in.getBody(String.class);
        String status = "'status': '" + statusValue + "' ";
        String body = order.replace("}", ", " + status + "}");
        in.setBody(body);
    }

    /*
    exchange - entire message lifecycle
    getIn() - incoming message (headers + body)
     */
}
