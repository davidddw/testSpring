package com.yunshan.rabbitmq.demo;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public class Send5 {
    private static final String EXCHANGE_NAME = "topic_logs";
    
    public static void main(String[] argv) throws Exception {  
  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("10.33.39.16");
        factory.setPort(20001);
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
  
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");//声明topic类型的Exchange  
        
        String routingKeyOne = "logs.error.one";// 定义一个路由名为“error”  
        for (int i = 0; i <= 1; i++) {  
            String messageOne = "this is one error logs:" + i;  
            channel.basicPublish(EXCHANGE_NAME, routingKeyOne, null, messageOne  
                    .getBytes());  
            System.out.println(" [x] Sent '" + routingKeyOne + "':'"  
                    + messageOne + "'");  
        }  
  
        System.out.println("################################");  
        String routingKeyTwo = "logs.error.two";  
        for (int i = 0; i <= 2; i++) {  
            String messageTwo = "this is two error logs:" + i;  
            channel.basicPublish(EXCHANGE_NAME, routingKeyTwo, null, messageTwo  
                    .getBytes());  
            System.out.println(" [x] Sent '" + routingKeyTwo + "':'"  
                    + messageTwo + "'");  
        }  
  
        System.out.println("################################");  
        String routingKeyThree = "logs.info.one";  
        for (int i = 0; i <= 3; i++) {  
            String messageThree = "this is one info logs:" + i;  
            channel.basicPublish(EXCHANGE_NAME, routingKeyThree, null,  
                    messageThree.getBytes());  
            System.out.println(" [x] Sent '" + routingKeyThree + "':'"  
                    + messageThree + "'");  
        }  
  
        channel.close();  
        connection.close();  
    }  
}
