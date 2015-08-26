package com.yunshan.rabbitmq.demo;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public class Send4 {
    private static final String EXCHANGE_NAME = "direct_logs";  
    
    public static void main(String[] argv) throws Exception {  
  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("10.33.39.16");
        factory.setPort(20001);
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
  
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");//rounting模式  
  
        String routingKeyOne = "error";//定义一个路由名为“error”  
        for (int i = 0; i <= 1; i++) {  
            String messageOne = "this is a error logs:" + i;  
            channel.basicPublish(EXCHANGE_NAME, routingKeyOne, null, messageOne  
                    .getBytes());  
            System.out.println(" [x] Sent '" + routingKeyOne + "':'" + messageOne  
                    + "'");  
        }  
  
        System.out.println("################################");  
        String routingKeyTwo = "info";  
        for (int i = 0; i <= 2; i++) {  
            String messageTwo = "this is a info logs:" + i;  
            channel.basicPublish(EXCHANGE_NAME, routingKeyTwo, null, messageTwo  
                    .getBytes());  
            System.out.println(" [x] Sent '" + routingKeyTwo + "':'" + messageTwo  
                    + "'");  
        }  
  
        System.out.println("################################");  
        String routingKeyThree = "all";  
        for (int i = 0; i <= 3; i++) {  
            String messageThree = "this is a all logs:" + i;  
            channel.basicPublish(EXCHANGE_NAME, routingKeyThree, null,  
                    messageThree.getBytes());  
            System.out.println(" [x] Sent '" + routingKeyThree + "':'"  
                    + messageThree + "'");  
        }  
  
        channel.close();  
        connection.close();  
    }
}
