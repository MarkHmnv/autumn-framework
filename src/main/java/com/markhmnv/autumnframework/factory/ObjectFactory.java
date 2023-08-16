package com.markhmnv.autumnframework.factory;

import com.markhmnv.autumnframework.annotation.Inject;
import com.markhmnv.autumnframework.context.ApplicationContext;
import com.markhmnv.autumnframework.config.ObjectConfigurator;
import com.markhmnv.autumnframework.config.ProxyConfigurator;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectFactory {
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();
    private final ApplicationContext context;

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        Reflections scanner = context.getConfig().getScanner();
        
        for(var aClass: scanner.getSubTypesOf(ObjectConfigurator.class)) {
            objectConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        for(var aClass: scanner.getSubTypesOf(ProxyConfigurator.class)) {
            proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    public <T> T createObject(Class<T> implClass) {
        Constructor<T> constructorToUse = selectConstructor(implClass);

        T t = createInstance(constructorToUse);
        configure(t);
        invokeInit(implClass, t);
        t = wrapWithProxyIfNeeded(implClass, t, constructorToUse);
        return t;
    }

    @SneakyThrows
    private <T> Constructor<T> selectConstructor(Class<T> implClass) {
        Constructor<?>[] constructors = implClass.getConstructors();
        Constructor<T> constructorToUse = (Constructor<T>) constructors[0];

        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                constructorToUse = (Constructor<T>) constructor;
                break;
            }
        }
        return constructorToUse != null ? constructorToUse : implClass.getDeclaredConstructor();
    }

    private <T> T wrapWithProxyIfNeeded(Class<T> implClass, T t, Constructor<T> constructorToUse) {
        for(var proxyConfigurator: proxyConfigurators) {
            t = (T) proxyConfigurator.replaceWithProxy(t, implClass, context, constructorToUse);
        }
        return t;
    }

    private <T> void configure(T t) {
        objectConfigurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));
    }

    @SneakyThrows
    private <T> T createInstance(Constructor<T> constructorToUse) {
        return constructorToUse.newInstance(
                Arrays.stream(constructorToUse.getParameters())
                        .map(Parameter::getType)
                        .map(context::getObject).toArray()
        );
    }

    @SneakyThrows
    private <T> void invokeInit(Class<T> implClass, T t) {
        for(Method method: implClass.getMethods()) {
            if(method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }
}
