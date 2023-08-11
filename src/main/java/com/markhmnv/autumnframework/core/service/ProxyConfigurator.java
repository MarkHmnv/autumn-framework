package com.markhmnv.autumnframework.core.service;

import com.markhmnv.autumnframework.core.service.impl.ApplicationContext;

import java.lang.reflect.Constructor;

public interface ProxyConfigurator {
    Object replaceWithProxy(Object t, Class<?> implClass, ApplicationContext context, Constructor<?> constructorToUse);
}
