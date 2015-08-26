package com.yunshan.rabbitmq.demo;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitmqConfiguration {

    protected final String helloWorldQueueName = "hello.world.queue";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("10.33.39.16");
        connectionFactory.setPort(20001);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        //The routing key is set to the name of the queue by the broker for the default exchange.
        template.setRoutingKey(this.helloWorldQueueName);
        //Where we will synchronously receive messages from
        template.setQueue(this.helloWorldQueueName);
        return template;
    }
    
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public Queue helloWorldQueue() {
        return new Queue(this.helloWorldQueueName);
    }
}
