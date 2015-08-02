package com.yunshan.cloudstack.utils;

public interface ActionDelegate<T> {
    void action(T param);
}