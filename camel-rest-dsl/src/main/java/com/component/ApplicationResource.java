package com.component;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


@Component
public class ApplicationResource extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet");
//                .bindingMode(RestBindingMode.json); //
//                .inlineRoutes(true);

        rest("my")
                .get("/hello-world")
                .produces(MediaType.APPLICATION_JSON_VALUE)  // produces - This API sends data in this format
                .to("direct:process");


        from("direct:process")
            .setBody(constant("{\"message\":\"Welcome to Java Techie\"}"));





    }
}
