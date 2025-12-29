package com.routes;

import static org.apache.camel.LoggingLevel.ERROR;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HelloRoute  extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:greeting")
                .id("greeting")
                //id() assigns an ID to one specific step in a route. id like A name of anyPerson, Like a label
                .log(ERROR, "Hello ${body}")
                .choice() // this like if else logic for routing message
                .when().simple("${body} contains 'Team'") // this block like if condition is true . Condition is that If contains
                // team then print. Solo Fighter
                .log(ERROR, "Solo Fighter :)")
                .end() // end the choice block
                .to("direct:finishGreeting");

                    from("direct:finishGreeting")
                            .log(ERROR, "Bye ${body}");

    }
}
