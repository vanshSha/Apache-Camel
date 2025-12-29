package com.route.eip.claimcheckEIP;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ClaimCheckOperation;

import static org.apache.camel.model.ClaimCheckOperation.GetAndRemove;
import static org.apache.camel.model.ClaimCheckOperation.Set;

//@Component
public class ClaimCheckEIP extends RouteBuilder {
    // PlayRouteTest

    // First I invoke my class without .claimCheck(), .setBody(jsonpath("$.name"))
    @Override
    public void configure() throws Exception {
        from("direct:start")
                .to("mock:step-1")
                .claimCheck(Set, "claim-tag-original") // Here I set key
                .setBody(jsonpath("$.name"))
                // jsonpath("$.name") -> this means extract the value from JSON format
                // setBody(jsonpath("$.name")) extracts name from JSON and replaces the entire message body with that value.
                .to("mock:step-2")
                .claimCheck(Set, "claim-tag-step-2") // Here I set key
                .to("mock:step-3")
                .claimCheck(GetAndRemove, "claim-tag-step-2")
                .to("mock:step-4")
                .transform().constant("This message should be there in body even after claim 2.")
                .claimCheck(ClaimCheckOperation.Get, "claim-tag-step-2")
                .to("mock:step-5")
                .claimCheck(ClaimCheckOperation.Get, "claim-tag-original")
                .to("mock:step-6");
    }
}
