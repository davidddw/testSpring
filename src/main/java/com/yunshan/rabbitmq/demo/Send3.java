package com.yunshan.rabbitmq.demo;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public class Send3 {
    private static final String EXCHANGE_NAME = "logs";  
    
    public static void main(String[] argv) throws Exception {  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("10.33.39.16");
        factory.setPort(20001);
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//声明Exchange  
        for (int i = 0; i <= 2; i++) {  
            String message = "hello word!" + i;  
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());  
            System.out.println(" [x] Sent '" + message + "'");  
        }  
        channel.close();  
        connection.close();  
    }
}
