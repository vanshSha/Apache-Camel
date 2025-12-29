package com.route.eip.circuitbreakerEIP;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class CircuitBreakerEIP extends RouteBuilder {


    @Override
    public void configure() throws Exception {
            from("timer:time?period=1000")
                    .circuitBreaker()
                    .resilience4jConfiguration() // tells Camel WHEN TO STOP calling a bad service and WHEN TO TRY AGAIN.

                    .timeoutEnabled(true)
                    .timeoutDuration(500)  // If the call takes more than 500 milliseconds, FAIL it.

                    // This is open state
                    .failureRateThreshold(99) // When failure limit reach 99% or more , the circuit open

                    // HALF-OPEN state
                    .waitDurationInOpenState(5) // After opening, wait for 5 seconds before trying again
                    .automaticTransitionFromOpenToHalfOpenEnabled(true) // this method means after wait time it will move on
                    // to ( OPEN --> HALF-OPEN) without doing anything
                    .end()
                    .log(LoggingLevel.INFO, "---------------------START--------------------")
                    .to("http://localhost:8080/hello?sleepTimeMills=1000")
                    .onFallback().transform().constant("Static Fallback Message ")
                    // onFallback -> this method means what should happen when the circuit breaker fails
                    // transform -> response shaping, defines what response to send when failure happens.
                    .end()
                    .log(LoggingLevel.INFO, "Body - ${body}")
                    .log(LoggingLevel.INFO, "----------------------END---------------------")
                    .end();

            // Without .end() Camel won’t know where config stops
    }
}
