package com.aggregation;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import static org.apache.camel.LoggingLevel.ERROR;

@Component
public class AggregationRoute extends RouteBuilder {

    final String CORRELATION_ID = "correlationId";   // Correlation Identifier 1
    // Correlation is the mechanism used to identify and group related messages using a shared identifier.

    @Override
    public void configure() throws Exception {
        // after 1 time running I am adding this stuff
        Random random = new Random();

        from("timer:insurance?period=200")
                .process(exchange -> {

                    Message message = exchange.getMessage();
                    //message.setHeader(CORRELATION_ID, 1);
                    message.setHeader(CORRELATION_ID, random.nextInt(4)); // setHeader : add update metadata (a header) on the message
                    message.setBody(new Date()); // erases the old content and writes new content into the SAME message.
                })
//                .log(ERROR, "${header."+CORRELATION_ID+"} ${body}")
                 // Completion Condition
                // .aggregate() this method allows message aggregation
                .aggregate(header(CORRELATION_ID), new MyAggregationStrategy())
                .completionSize(2) // completes an aggregation once n messages with the same correlation key are received.
                .log(ERROR, "${header." + CORRELATION_ID + "} ${body}");
    }
}
