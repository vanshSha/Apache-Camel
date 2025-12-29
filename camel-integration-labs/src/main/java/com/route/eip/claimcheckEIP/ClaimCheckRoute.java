package com.route.eip.claimcheckEIP;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ClaimCheckOperation;

import java.util.Date;

//@Component
public class ClaimCheckRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:time?period=1000")
                .setBody(constant(new Date()))
                .to("direct:start12");

        from("direct:start12")

                //1 SET – store current message, replace body
                .log("Before SET -> ${body}")
                .claimCheck(ClaimCheckOperation.Set)   // set key
                .log("After SET (body replaced) -> ${body}")

                // do some lightweight processing
                .setBody(simple("Lightweight body"))

                //2 GET – retrieve stored message (without removing)
                .claimCheck(ClaimCheckOperation.Get)    // get key
                .log("After GET -> ${body}")

                //3 PUSH – push another message on claim stack
                .setBody(simple("Another heavy message"))
                .claimCheck(ClaimCheckOperation.Push)   // saves the current message on a STACK, you are pushing the current Exchange message
                .log("After PUSH -> ${body}")

                // do something else
                .setBody(simple("Temporary body"))
                .claimCheck(ClaimCheckOperation.Push)
                .log("After PUSH -> ${body}")

                //4 POP – restore last pushed message
                .claimCheck(ClaimCheckOperation.Pop) // here I will get latest message you can say top one ,  Stack behavior (LIFO)
                .log("After POP -> ${body}")

                //5 GET_AND_REMOVE – restore and delete stored data
                .claimCheck(ClaimCheckOperation.GetAndRemove)
                .log("After GET_AND_REMOVE -> ${body}");
    }
}
