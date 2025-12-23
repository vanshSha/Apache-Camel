package com.aggregation;

import org.apache.camel.Exchange;
import java.util.Objects;

public class MyAggregationStrategy implements org.apache.camel.AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if(Objects.isNull(oldExchange)){
            return newExchange;
        }
        String oldBody = oldExchange.getIn().getBody(String.class);
        // getIn()  accesses the current message inside the Exchange
        // getBody(String.class) reads the message body and Type-Casts It safely to String

        String newBody = oldExchange.getIn().getBody(String.class );
        String aggBody = oldBody + "->" + newBody;

        oldExchange.getIn().setBody(aggBody);  // setBody()   erases the old content and writes new content into the SAME message.
        return oldExchange;
    }
}
