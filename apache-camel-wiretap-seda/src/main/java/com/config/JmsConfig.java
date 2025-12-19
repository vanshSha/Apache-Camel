package com.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;

@Configuration
public class JmsConfig {
    @Bean
    public ConnectionFactory jmsConnectionFactory() {
        ActiveMQConnectionFactory activeMQCF =
                new ActiveMQConnectionFactory("tcp://localhost:61616");

        //  This is NOT for queue creation
        // This is for allowing object messages (serialization)
        activeMQCF.setTrustAllPackages(true);

        // Spring wrapper (recommended)
        CachingConnectionFactory cachingCF =
                new CachingConnectionFactory(activeMQCF);
        cachingCF.setSessionCacheSize(10);

        return cachingCF;
    }
}
