package com.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.apache.camel.LoggingLevel.ERROR;

public class HelloRouteJUnitTest extends CamelTestSupport {

    protected RouteBuilder createRouteBuilder() throws Exception {
        return new HelloRoute();

       //   this is normal test for testing routing end
//        return new RouteBuilder() {
//            @Override
//            public void configure() throws Exception {
//                from("direct:greeting")
//                        .log(ERROR, "${body}");
//            }
//        };
    }

    @Test  // comes from JUnit This method is a test case. Execute it automatically when tests run
    public void testMocksAreValid() throws InterruptedException {
        System.out.println("Sending 1");
        template.sendBody("direct:greeting", "Team");
        // template — this is Apache Camel’s ProducerTemplate
        System.out.println("Sending 2");
        template.sendBody("direct:greeting", "Me");
        // template.sendBody(String endpointUri, Object body);
        // sendBody() - this simplify method signature


    }
}
