package com.markhmnv.autumnframework.config;

import com.markhmnv.autumnframework.annotation.InjectProperty;
import com.markhmnv.autumnframework.context.ApplicationContext;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
    private Map<String, String> propertiesMap = null;

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator() {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("application.properties");
        if(inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                this.propertiesMap = reader.lines()
                        .map(line -> line.split("="))
                        .collect(toMap(arr -> arr[0], arr -> arr[1]));
            }
        }
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
        if (propertiesMap == null)
            return;

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
