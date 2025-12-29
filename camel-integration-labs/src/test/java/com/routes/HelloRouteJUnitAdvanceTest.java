package com.routes;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.apache.camel.builder.AdviceWith.adviceWith;

public class HelloRouteJUnitAdvanceTest extends CamelTestSupport {

    // If this method is true that means Camel, DON’T start the routes yet. I want to change them first
    // If this method is false that means Camel, Camel starts routes immediately
    public boolean isUseAdviceWith() {
        return true;
    }

    protected RouteBuilder createRouteBuilder() throws Exception {
        return new HelloRoute();
    }


    @Test
    public void testMockEndPoints() throws Exception {
        RouteDefinition route = context.getRouteDefinition("greeting");
        // RouteDefinition this is the (blueprint) use for design of a Camel route, not the running route itself.
        // context: CamelContext is the runtime container that creates, manages, and runs all Camel routes.
        // getRouteDefinition() this is the method of context. this method access blueprint

        adviceWith(route, context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveAddLast().to("mock:finishGreeting");
                // weaveAddLast: add something in the last of the end .
            }
            // new AdviceWithRouteBuilder() A special builder used ONLY to modify an existing route definition.
        });
        context().start();
        // context.start() - Camel, now start the context and run the routes.

        MockEndpoint mock = getMockEndpoint("mock:finishGreeting");
        mock.expectedMessageCount(1);

        template.sendBody("direct:greeting", "team");
        mock.assertIsSatisfied();
    }

    // Conclusion of this code It proves that when a message is sent to direct:greeting,
    //  the route runs fully and reaches its end.

}
