package com.route;

import com.dto.WeatherDto;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.apache.camel.LoggingLevel.ERROR;

//@Component
public class WeatherRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("activemq:queue:hello")
                .routeId("weather-producer-route")
                .log(ERROR, "Before Enrichment Body : ${body}")
                .unmarshal().json(JsonLibrary.Jackson, WeatherDto.class) // convert json into java object and Java class tells Jackson how to build the object
                .process(this::enrichWeatherDto)
                .log(ERROR, "After Enrichment Body : ${body}")
                .marshal().json(JsonLibrary.Jackson, WeatherDto.class) // Jackson reads the Java object fields and serializes them into JSON
                .to("activemq:queue:another.queue")
                .to("file:///Users/vansh/Downloads/f?fileName=i-create-file-through-apache-camel-activemq.txt&fileExist=Append");

        //fileExist=Append -> Each new message is added to the end of the same file
    }

    private void enrichWeatherDto(Exchange exchange) {
        WeatherDto dto = exchange.getMessage().getBody(WeatherDto.class);
        dto.setReceivedTime(new Date().toString());


        Message message = new DefaultMessage(exchange);
        message.setBody(dto);
        exchange.setMessage(message);

        // Message = content
        // new DefaultMessage(exchange) - Create a NEW Camel message that belongs to THIS exchange.
    }
}
