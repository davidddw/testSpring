package com.yunshan.rabbitmq.demo;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Reqv5_1 {
    private static final String EXCHANGE_NAME = "topic_logs";// 定义Exchange名称 
    
    public static void main(String[] argv) throws Exception {  
  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("10.33.39.16");
        factory.setPort(20001); 
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
  
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");// 声明topic类型的Exchange  
        
        String queueName = "queue_topic_logs1";// 定义队列名为“queue_topic_logs1”的Queue  
        channel.queueDeclare(queueName, false, false, false, null);  
//      String routingKeyOne = "*.error.two";// "error"路由规则  
//      channel.queueBind(queueName, EXCHANGE_NAME, routingKeyOne);// 把Queue、Exchange及路由绑定  
        String routingKeyTwo = "logs.*.one";//通配所有logs下第三词（最后一个）词为one的消息  
        channel.queueBind(queueName, EXCHANGE_NAME, routingKeyTwo);  
  
        System.out.println(" [*] Waiting for messages.");  
  
        QueueingConsumer consumer = new QueueingConsumer(channel);  
        channel.basicConsume(queueName, true, consumer);  
  
        while (true) {  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());  
            String routingKey = delivery.getEnvelope().getRoutingKey();  
  
            System.out.println(" [x] Received '" + routingKey + "':'" + message  
                    + "'");  
        }  
    }  
}
