package com.routes.contentBasedRoute;

import com.route.contentbasedroute.ChoiceRoute;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.route.contentbasedroute.ChoiceRoute.*;
import static org.apache.camel.builder.AdviceWith.adviceWith;
import static org.apache.camel.test.junit5.TestSupport.getMockEndpoint;
import static org.assertj.core.util.Maps.newHashMap;

@MockEndpoints
public class ChoiceRouteTest extends CamelTestSupport {

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    public RouteBuilder createRouteBuilder() {
        return new ChoiceRoute();
    }

    @Test
    void givenGadgetOrderRequest_route_WillProcessGadgetOrders() throws Exception {

         //  commented basic part    -
//        String body = "Airpods";
//        Map<String, Object> headers = Maps.newHashMap("inventory", "gadget");
        /* Here I am using object because
        Headers can hold any type
        Using Object means a header can be: Number (3) , Boolean (true) , Date , String ("gadget")
        - With Objects Numbers are compared as number, Booleans work as booleans
        - With Strings Everything is text, Comparisons can give wrong result. */
       // template.sendBodyAndHeaders("direct:orders", body, headers);


        // This is advance testing
        MockEndpoint mockGadget = mockEndpoint(GADGET, 1);
        MockEndpoint mockWidget = mockEndpoint(WIDGET, 0);
        MockEndpoint mockGeneral = mockEndpoint(GENERAL, 0);

        context.start();

        String body = "Airpods";
        Map<String, Object> headers = newHashMap(INVENTORY, GADGET);
        template.sendBodyAndHeaders("direct:orders", body, headers);

        assertAllSatisfied(mockGadget, mockWidget, mockGeneral);
    }

    @Test
    void givenWidgetOrderRequest_route_WillProcessWidgetOrders() throws Exception {
//        String body = "Amazon";
//        Map<String, Object> headers = newHashMap("inventory", "widget");
//        template.sendBodyAndHeaders("direct:orders", body, headers);
        MockEndpoint mockGadget = mockEndpoint(GADGET, 0);
        MockEndpoint mockWidget = mockEndpoint(WIDGET, 1);
        MockEndpoint mockGeneral = mockEndpoint(GENERAL, 0);

        context.start();

        String body = "Vansh";
        Map<String, Object> headers = newHashMap(INVENTORY, WIDGET);
        template.sendBodyAndHeaders("direct:orders", body, headers);

        assertAllSatisfied(mockGadget, mockWidget, mockGeneral);
    }

    @Test
    void givenGeneralOrderRequest_route_WillProcessGeneralOrder() throws Exception {
        MockEndpoint mockGadget = mockEndpoint(GADGET, 0);
        MockEndpoint mockWidget = mockEndpoint(WIDGET, 0);
        MockEndpoint mockGeneral = mockEndpoint(GENERAL, 1);

        context.start();

        String body = "T-Shirt";
        Map<String, Object> headers = newHashMap(INVENTORY, GENERAL);
        template.sendBodyAndHeaders("direct:orders", body, headers);

        assertAllSatisfied(mockGadget, mockWidget, mockGeneral);
    }

    private MockEndpoint mockEndpoint(String orderType, int expectedCount) throws Exception {
        RouteDefinition route = context.getRouteDefinition(orderType);
        adviceWith(
                route, context, new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        weaveAddLast().to("mock:" + orderType);
                    }});
        MockEndpoint mockEndpoint = getMockEndpoint("mock:" + orderType);
        mockEndpoint.expectedMessageCount(expectedCount);
        return mockEndpoint;
    }

    private void assertAllSatisfied(MockEndpoint... mockEndpoint) throws InterruptedException {
        for (MockEndpoint endpoint : mockEndpoint) {
            endpoint.assertIsSatisfied();
        }
    }

}




