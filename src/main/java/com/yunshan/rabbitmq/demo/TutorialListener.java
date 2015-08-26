package com.yunshan.rabbitmq.demo;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TutorialListener implements MessageListener {
    public void onMessage(Message message) {
        String messageBody = new String(message.getBody());
        System.out.println("Listener received message----->" + messageBody);
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:rabbitConfiguration.xml");// loading beans
        AmqpTemplate aTemplate = (AmqpTemplate) context.getBean("tutorialTemplate");

        /*for (int i = 0; i < 10; i++) {
            
            aTemplate.convertAndSend("my.routingkey.1", "Message # " + i + " on " + new Date());// send
            Thread.sleep(1000);
        }*/
    }
}
