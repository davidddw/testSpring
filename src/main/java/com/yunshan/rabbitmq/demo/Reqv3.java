package com.yunshan.rabbitmq.demo;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Reqv3 {
    private static final String EXCHANGE_NAME = "logs";  
    
    public static void main(String[] argv) throws Exception {  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("10.33.39.16");
        factory.setPort(20001);
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");  
        String queueName = "log-fb1";  
        channel.queueDeclare(queueName, false, false, false, null);  
        channel.queueBind(queueName, EXCHANGE_NAME, "");//把Queue、Exchange绑定  
        QueueingConsumer consumer = new QueueingConsumer(channel);  
        channel.basicConsume(queueName, true, consumer);  
        while (true) {  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());  
            System.out.println(" [x] Received '" + message + "'");  
        }  
    }  
}
