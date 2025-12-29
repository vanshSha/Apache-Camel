package com.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;

@MockEndpoints
public class HelloRouteJUnitMockTest extends CamelTestSupport {

    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                 from("direct:greeting")
                         .to("mock:greetingResult");
            }
        };
    }

    @Test
    public void testMocksAreValid() throws InterruptedException {
      MockEndpoint mock = getMockEndpoint("mock:greetingResult");
      mock.expectedMessageCount(2); // this line means how many message I will receive
      // 23 If do you want seed failed system then. I will use 23

        System.out.println("Sending 1");
        template.sendBody("direct:greeting", "Team");

        System.out.println("Sending 2");
        template.sendBody("direct:greeting", "Me");

        mock.assertIsSatisfied();  // this line means whatever expectations I set . It is satisfied or not .
    }

}

