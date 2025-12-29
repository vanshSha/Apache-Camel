package com.route.contentbasedroute;

import static org.apache.camel.LoggingLevel.ERROR;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ChoiceRoute extends RouteBuilder {

    public static final String WIDGET = "widget";
    public static final String GADGET = "gadget";
    public static final String GENERAL = "general";
    public static final String INVENTORY = "inventory";

    @Override
    public void configure() throws Exception {


//        from("timer:producer?repeatCount=1")
//                .setBody(constant("Order placed"))
//                .setHeader("inventory", constant("widget"))
//                .to("direct:orders");
        // I will check logic through test



        // this is only consumer part .
        from("direct:orders")
                .choice()
                .when(simple("${header.inventory} == 'widget'"))
                .to("direct:widget")
                .when(simple("${header.inventory} == 'gadget'"))
                .to("direct:gadget")
                .otherwise()
                .to("direct:general");

        from("direct:widget").routeId("widget")
                .log(ERROR, "Got a widget order for ${body}");

        from("direct:gadget").routeId("gadget")
                .log(ERROR, "Got a gadget order for ${body}");

        from("direct:general").routeId("general")
                .log(ERROR, "Got a general order for ${body}");

    }
}
