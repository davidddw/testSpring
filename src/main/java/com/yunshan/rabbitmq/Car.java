package com.yunshan.rabbitmq;


import org.springframework.stereotype.Component;

@Component("car")
public class Car {
    //@Resource
    private String brand;
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //@Resource
    private double price;

    // 省略 get/setter

    @Override
    public String toString() {
        return "brand:" + brand + "," + "price:" + price;
    }
}
