package com.markhmnv.autumnframework.config;

import com.markhmnv.autumnframework.context.ApplicationContext;

import java.lang.reflect.Constructor;

public interface ProxyConfigurator {
    Object replaceWithProxy(Object t, Class<?> implClass, ApplicationContext context, Constructor<?> constructorToUse);
}
