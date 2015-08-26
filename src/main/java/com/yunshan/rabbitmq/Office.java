package com.yunshan.rabbitmq;


import org.springframework.stereotype.Component;

@Component("office")
public class Office {
    //@Resource
    private String officeNo = "001";

    //省略 get/setter

    public String getOfficeNo() {
        return officeNo;
    }

    public void setOfficeNo(String officeNo) {
        this.officeNo = officeNo;
    }

    @Override
    public String toString() {
        return "officeNo:" + officeNo;
    }
}
