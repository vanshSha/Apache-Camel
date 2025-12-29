package com.route.nats;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class OrderManagementSystemRoute extends RouteBuilder {

    private static final String NATS_SERVER = "?servers=localhost:4222";

    @Override
    public void configure() throws Exception {
// This is begging code for only testing purpose
//        from("nats:ORDERS.CHECKOUT?servers=localhost:4222")
//                .log(LoggingLevel.INFO, "CHECKOUT for ${body}");

        // 1️⃣ CHECKOUT → ORDER-CREATED
        from("nats:ORDERS.CHECKOUT?" + NATS_SERVER)
                .routeId("checkout-route")
                .log(LoggingLevel.INFO, "CHECKOUT for ${body}")
                .to("nats:ORDERS.ORDER-CREATED?" + NATS_SERVER);

        // 2️⃣ ORDER-CREATED → PAYMENT-RECEIVED
        from("nats:ORDERS.ORDER-CREATED?" + NATS_SERVER)
                .routeId("order-created-route")
                .log(LoggingLevel.INFO, "ORDER-CREATED for ${body}")
                .to("nats:ORDERS.PAYMENT-RECEIVED?" + NATS_SERVER);

        // 3️⃣ PAYMENT-RECEIVED → DELIVERED
        from("nats:ORDERS.PAYMENT-RECEIVED?" + NATS_SERVER)
                .routeId("payment-received-route")
                .log(LoggingLevel.INFO, "PAYMENT-RECEIVED for ${body}")
                .to("nats:ORDERS.DELIVERED?" + NATS_SERVER);

    }

    // - nats pub -s localhost:4222 ORDERS.CHECKOUT "ORDER_ID: 100" - this is final and import line for consumer and producer and subscriber
}


// nats pub -s localhost:4222 ORDERS.CHECKOUT "ORDER_ID: 0"  -- I Execute this command from Terminal