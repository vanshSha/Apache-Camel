package com.route.eip.interceptEIP;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ClaimCheckOperation;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.ClaimCheckOperation.GetAndRemove;
import static org.apache.camel.model.ClaimCheckOperation.Set;

//@Component
public class BasicInterceptEIP extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // THis is basic use case
       //  intercept().log("log:msg");

        // Advance use case
        intercept()
                .when(body().contains("secret")).stop(); // this is 2nd endPoint

        from("direct:start")
                .to("mock:step-1")
                .claimCheck(Set, "claim-tag-original")
                .setBody(jsonpath("$.name"))
                .to("mock:step-2")
                .claimCheck(Set, "claim-tag-step-2")
                .to("mock:step-3")
                .claimCheck(GetAndRemove, "claim-tag-step-2")
                .to("mock:step-4")
                .transform()
                .constant("This message should be there in body even after claim 2.")
                .claimCheck(ClaimCheckOperation.Get, "claim-tag-step-2")
                .to("mock:step-5")
                .claimCheck(ClaimCheckOperation.Get, "claim-tag-original")
                .to("mock:step-6");
    }
}
