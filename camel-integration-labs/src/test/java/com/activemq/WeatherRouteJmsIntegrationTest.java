package com.activemq;

import com.DemoApplication;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(
        classes = DemoApplication.class,
        properties = {"jms.camel.activemq.enable=true"}
)
@CamelSpringBootTest
public class WeatherRouteJmsIntegrationTest {

    @Autowired
    private JmsTemplate activeTemplate;
}
