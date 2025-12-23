package com.kafka;

import static org.apache.camel.LoggingLevel.ERROR;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StockKafkaRoute extends RouteBuilder {

    final String KAFKA_ENDPOINT = "kafka:%s?brokers=localhost:9092";

    /*
    kafka: -> Camel Kafka component
	%s. -> placeholder for the topic name
	brokers=localhost:9092 -> Kafka broker address

	${header.kafka.OFFSET}. —> Tells you which message position this is in the topic
	like this [0] [first message]
                    [1] [second message]
     */
    @Override
    public void configure() throws Exception {

        fromF(KAFKA_ENDPOINT, "stock-live")
                .log(ERROR, "[${header.kafka.OFFSET}] [${body}]")
                .bean(StockPriceEnricher.class)
                .toF(KAFKA_ENDPOINT, "stock-audit");
    }
}


class StockPriceEnricher {
    public String enrichStockPrices(String stockPrice) {
        return stockPrice + " , " + new Date();
    }
}
