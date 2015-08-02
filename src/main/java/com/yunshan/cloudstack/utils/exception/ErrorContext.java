package com.yunshan.cloudstack.utils.exception;

import java.util.List;

import com.yunshan.cloudstack.utils.Pair;

public interface ErrorContext {

    ErrorContext add(Class<?> entity, String uuid);

    List<Pair<Class<?>, String>> getEntitiesInError();
}
