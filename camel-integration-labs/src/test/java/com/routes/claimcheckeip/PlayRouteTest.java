package com.routes.claimcheckeip;

import com.route.eip.claimcheckEIP.ClaimCheckEIP;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class PlayRouteTest extends CamelTestSupport {

    protected RouteBuilder createRouteBuilder() throws Exception {
        return new ClaimCheckEIP();

                //new ClaimCheckRoute();
    }


    @Test
    void callDirect() {

        template.sendBody("direct:start", loadComplexMessage());
        printMock("mock:step-1");
        printMock("mock:step-2");
        printMock("mock:step-3");
        printMock("mock:step-4");
        printMock("mock:step-5");
        printMock("mock:step-6");

    }

    private String loadComplexMessage() {
        String str =
                """
                        {
                            "name": "Vansh",
                            "city": "Delhi",
                            "attachment": "binary-large-data-xxxxxdddddd1213 secret"
                        }
                        """;
        return str;
    }

    void printMock(String route) {
        MockEndpoint mock = getMockEndpoint(route);
        System.out.println(
                "Mock ["
                        + route
                        + " ]"
                        + mock.getReceivedExchanges().get(0).getMessage().getBody());
    }

}
