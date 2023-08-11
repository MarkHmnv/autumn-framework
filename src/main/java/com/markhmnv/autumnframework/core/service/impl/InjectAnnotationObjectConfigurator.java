package com.markhmnv.autumnframework.core.service.impl;

import com.markhmnv.autumnframework.core.annotation.Inject;
import com.markhmnv.autumnframework.core.service.ObjectConfigurator;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectAnnotationObjectConfigurator implements ObjectConfigurator {
    /**
     * Configures an object by injecting dependencies from the ApplicationContext
     *
     * @param t The object to be configured
     * @param context The ApplicationContext containing the dependencies
     */
    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        for(Field field: t.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                field.set(t, object);
            }
        }
    }
}
