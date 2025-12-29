package com.route.beancomponent;

import org.apache.camel.Handler;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.apache.camel.LoggingLevel.INFO;
import static org.apache.camel.component.bean.BeanConstants.BEAN_METHOD_NAME;

//@Component
public class CamelBeanTesterRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:myTimer?period=10_0000")
                .setBody(e -> new Date())
                .setHeader("myHeader", () -> "I am new Header")  // Create a Header name and store the value JSS
                .to("bean:com.route.beancomponent.MyBean?method=fromServer(${body}, ${header.myHeader})")
                //com.route.beancomponent :- this is fully qualified name
                .log(LoggingLevel.INFO, "${body}");

        // 1st way bean:com.route.beancomponent.MyBean
        // 2nd way bean:com.route.beancomponent.MyBean?method=fromServer
        // 3rd way If I will use @Handler annotation on bean method then I will invoke this line of code - bean:com.route.beancomponent.MyBean


    }
}

class MyBean {

    public String fromServer(String body, String header) {
        return "From Server:" + body + "; Header: " + header;
    }


    public String fromClient(String msg) {
        return "From Client:" + msg;
    }

    public String fromServer(String msg) {
        return "From Server:" + msg;
    }

    // @Handler // this is a another way How to invoke specific bean method
    public String fromDefault(String msg) {
        return "From Default:" + msg;
    }
}
