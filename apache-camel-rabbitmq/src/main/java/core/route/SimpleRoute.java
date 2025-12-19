package core.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("timer:sendToRabbit?repeatCount=1")
                .setBody(constant("Hello from Spring Boot & Camel"))
                .log("Sending message to RabbitMQ: ${body}")
                .to("spring-rabbitmq:demo.exchange?routingKey=demo.key");



        // consumer
        from("spring-rabbitmq:demo.exchange?queues=demo.queue&routingKey=demo.key")
                .log("Consumed from RabbitMQ: ${body}");

    }
}
