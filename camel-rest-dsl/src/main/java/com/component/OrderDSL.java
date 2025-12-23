package com.component;

import com.dto.Order;
import com.service.OrderService;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class OrderDSL extends RouteBuilder {

    @Autowired
    private OrderService service;

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json);// in - JSON requests to Java objects , out - Java objects to JSON responses


        // get method
        rest()
                .get("getOrders")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:orders");

        from("direct:orders")
                .process(exchange -> {
                    exchange.getMessage().setBody(service.getOrders());
                })
                .log("GET /getOrders response: ${body}");

        // post order - here 1 problem is that Binding issue
            rest()
                .post("postOrder")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(Order.class) // covert JSON into based on passed in this stuff --> .type(Order.class)  converted into Objected
                .outType(Order.class) // defines the response payload type and converts the Java object to JSON
                .to("direct:createOrder");

        from("direct:createOrder")
                .log("Raw body before processing: ${body}")
                .process(exchange -> {

                    Order order = exchange.getIn().getBody(Order.class);

                    Order saved = service.addOrder(order);
//  -                  log("Raw body: ${saved}");
                    exchange.getMessage().setBody(saved);
                    exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, "application/json");
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 201);})
                .log("Response body: ${body}")
                .log("Response headers: ${headers}");

    }
}
