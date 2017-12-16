package com.ztev.nbi.config;
import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMQConfig {
    @Autowired
    Environment env;

    @Bean
    @Primary
    public ConnectionFactory jmsNativeConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(env.getProperty("local.activemq.broker_url"));
        connectionFactory.setUserName(env.getProperty("local.activemq.user"));
        connectionFactory.setPassword(env.getProperty("local.activemq.password"));
        return connectionFactory;
    }

    @Bean
    public QueueConnectionFactory jmsNMSConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(env.getProperty("nms.activemq.broker_url"));
        connectionFactory.setUserName(env.getProperty("nms.activemq.user"));
        connectionFactory.setPassword(env.getProperty("nms.activemq.password"));
        return connectionFactory;
    }

    @Bean
    @Primary
    public JmsTemplate jmsNativeTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(jmsNativeConnectionFactory());
        jmsTemplate.setDefaultDestinationName("ztev.parking.topic");
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }



    @Bean
    public JmsListenerContainerFactory<?> jmsListenerTopicContainerFactory(
            @Qualifier("jmsNMSConnectionFactory") ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }


    @Bean
    public JmsListenerContainerFactory<?> jmsQueueListenerContainerFactory(
            @Qualifier("jmsNMSConnectionFactory") ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }



}