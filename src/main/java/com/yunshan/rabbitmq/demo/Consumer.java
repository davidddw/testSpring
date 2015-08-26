package com.yunshan.rabbitmq.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.amqp.core.AmqpTemplate;

@Configuration
public class Consumer {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RabbitmqConfiguration.class);
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
        System.out.println("Received: " + amqpTemplate.receiveAndConvert());
        ((AbstractApplicationContext) context).close();
    }
}
