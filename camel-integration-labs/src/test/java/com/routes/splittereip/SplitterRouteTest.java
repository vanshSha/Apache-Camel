package com.routes.splittereip;

import lombok.Builder;
import lombok.Data;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitterRouteTest extends CamelTestSupport {


    @Test
    void splitEip() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:split");
        mock.expectedBodiesReceived("A", "B", "C");
        //  List<String> body = Arrays.asList("A", "B", "C"); now i am not passing list
        template.sendBody("direct:start", "A#B#C");
        mock.assertIsSatisfied();
    }

    // 2nd test case
    @Test
    void complexSplitEip() {
        List<Order> orders = new ArrayList<>();
        orders.add(Order.builder().orderId("0100").itemIds(Arrays.asList("I100", "I101", "I102")).build());
        orders.add(Order.builder().orderId("0100").itemIds(Arrays.asList("I200", "I101", "I202")).build());
        CustomerOrders customerOrders = CustomerOrders.builder().customerId("Vansh").orders(orders).build();
        template.sendBody("direct:customerOrder", customerOrders);
    }

    // 3rd test case split Aggregate EIP
    @Test
    void splitAndAggregateEip() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:aggregatedResult");
        mock.expectedBodiesReceived("A=Apple + B=Bucket + C=Cat");
        template.sendBody("direct:customerOrderAggregate", "A,B,C");
        mock.assertIsSatisfied();
    }

    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .log("Before Split line ${body}")
                        .split(body()).delimiter("#") // here i am using my own delimiter.
                        .log("Split line ${body}")
                        .to("mock:split");


                // complex split
                from("direct:customerOrder")
                        .log("Customer Id: ${body.customerId}")
                        .split(method(OrderService.class))
//                        .split(body())
//                            .split(simple("${body.orders}"))          // split Orders
//                            .split(simple("${body.itemIds}"))          // split itemIds
                        .log("Order: ${body}");


                // splitAndAggregateEip
                from("direct:customerOrderAggregate")
                        .split(body(), new WordAggregationStrategy())
                        .stopOnException()
                        .bean(WordTranslateBean.class).to("mock:split")
                        .end()// here end i am using because I want to get original body
                        .log("Aggregate ${body}")
                        .to("mock:aggregatedResult");


            }
        };
    }
//}


    @Data
    @Builder
    static class CustomerOrders {
        private String customerId;
        private List<Order> orders;
    }

    @Data
    @Builder
    static class Order {
        private String orderId;
        private List<String> itemIds;
    }

    @Data
    @Builder
    static class OrderService {
        public static List<Order> getOrders(CustomerOrders customerOrders) {
            return customerOrders.getOrders();
        }
    }

}
