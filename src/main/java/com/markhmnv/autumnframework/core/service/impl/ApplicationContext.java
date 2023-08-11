package com.markhmnv.autumnframework.core.service.impl;

import com.markhmnv.autumnframework.core.annotation.Injectable;
import com.markhmnv.autumnframework.core.enums.Scope;
import com.markhmnv.autumnframework.core.factory.ObjectFactory;
import com.markhmnv.autumnframework.core.service.Config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@RequiredArgsConstructor
public class ApplicationContext {
    private final Map<Class<?>, Object> cache = new ConcurrentHashMap<>();
    private final Config config;
    private ObjectFactory factory;

    /**
     * Returns an instance of the specified class type.
     *
     * @param <T>  the type parameter of the object
     * @param type the class type of the object
     * @return an instance of the specified class type
     */
    public <T> T getObject(Class<T> type) {
        Class<? extends T> implClass = type;
        if(cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        if(type.isInterface()) {
            implClass = config.getImplClass(type);
        }

        T t = factory.createObject(implClass);
        if(getScope(implClass) == Scope.SINGLETON) {
            cache.put(type, t);
        }

        return t;
    }

    private Scope getScope(Class<?> clazz) {
        if(clazz.isAnnotationPresent(Injectable.class)) {
            return clazz.getAnnotation(Injectable.class).scope();
        }
        return Scope.SINGLETON;
    }
}
