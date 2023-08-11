package com.markhmnv.autumnframework.core.service.impl;

import com.markhmnv.autumnframework.core.annotation.InjectProperty;
import com.markhmnv.autumnframework.core.service.ObjectConfigurator;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
    private final Map<String, String> propertiesMap;

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator() {
        String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
        Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
        this.propertiesMap = lines
                .map(line -> line.split("="))
                .collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    /**
     * Configures the object with properties from the properties map based on the InjectProperty annotation.
     *
     * @param t        The object to be configured.
     * @param context  The application context.
     */
    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        Class<?> implClass = t.getClass();
        for (Field field : implClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if(annotation != null) {
                String value = annotation.value().isEmpty()
                        ? propertiesMap.get(field.getName())
                        : propertiesMap.get(annotation.value());
                field.setAccessible(true);
                field.set(t, value);
            }
        }
    }
}
