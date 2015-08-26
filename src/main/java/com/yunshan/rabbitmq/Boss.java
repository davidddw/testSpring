package com.yunshan.rabbitmq;

import javax.annotation.Resource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("boss")
public class Boss {
    
    @Resource
    private Car car;
    
    @Resource
    private Office office;

    // 省略 get/setter

    @Override
    public String toString() {
        return "car:" + car + "\n" + "office:" + office;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
    
    public static void main(String[] args) {
        ClassPathXmlApplicationContext cx = new ClassPathXmlApplicationContext(
                "classpath:application.xml");
        Boss boss = cx.getBean("boss", Boss.class);
        System.out.println(boss);
        cx.close();
    }
}
