package com.route.eip.claimcheckEIP;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ClaimCheckOperation;

//@Component
public class MainKeyBasedFullEx extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Store original message with a KEY
        from("timer:order?repeatCount=1")
                // Example incoming message
                .setHeader("orderId", constant("ORD-101"))
                .setBody(constant("{orderId:ORD-101, amount:5000, items:[A,B,C]}"))

                .log("INCOMING ORDER -> ${body}")
                // STORE using KEY
                .claimCheck(ClaimCheckOperation.Set, "orderId")
                // Replace body with lightweight data
                .setBody(simple("Processing order ${header.orderId}"))
                .to("direct:process");


        // Use lightweight data (no heavy payload)
        from("direct:process")
                .log("LIGHT BODY -> ${body}")
                .to("direct:restore");

        // GET original message using the SAME KEY
        from("direct:restore")
                // RESTORE but KEEP stored
                .claimCheck(ClaimCheckOperation.Get, "orderId")
                .log("RESTORED (GET) -> ${body}")
                // still stored, can be used again
                .to("direct:final");


        // Final restore + cleanup
        from("direct:final")
                // RESTORE and DELETE
                .claimCheck(ClaimCheckOperation.GetAndRemove, "orderId")
                .log("FINAL MESSAGE (GET_AND_REMOVE) -> ${body}");

    }
}
