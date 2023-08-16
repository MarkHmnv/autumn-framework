package com.markhmnv.autumnframework.config;

import com.markhmnv.autumnframework.context.ApplicationContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.*;
import java.util.Arrays;

@Slf4j
public class DeprecatedHandlerProxyConfiguratorImpl implements ProxyConfigurator {
    @Override
    public Object replaceWithProxy(Object t, Class<?> implClass, ApplicationContext context, Constructor<?> constructorToUse) {
        //TODO: make support for @Deprecate above methods too
        if (implClass.isAnnotationPresent(Deprecated.class)) {
            return createProxyInstance(t, implClass, context, constructorToUse);
        }
        return t;
    }

    /**
     * Creates a proxy instance for a given object using the specified implementation class, application context, and constructor.
     * If the implementation class does not have any non-inherited methods, a dynamic proxy class will be created using ByteBuddy.
     * Otherwise, a standard JDK dynamic proxy will be created.
     *
     * @param t The object to create a proxy for
     * @param implClass The implementation class to use for creating the proxy
     * @param context The application context used for resolving constructor arguments
     * @param constructorToUse The constructor to use for creating the proxy instance
     * @return The created proxy instance
     */
    @SneakyThrows
    private Object createProxyInstance(Object t, Class<?> implClass, ApplicationContext context, Constructor<?> constructorToUse) {
        InvocationHandler handler = new ProxyInvocationHandler(t, implClass.getName());
        Class<?>[] interfaces = implClass.getInterfaces();
        if (interfaces.length == 0 || Arrays.stream(interfaces).allMatch(ifc -> ifc.getDeclaredMethods().length == 0)) {
            Class<?> proxyClass = new ByteBuddy()
                    .subclass(implClass)
                    .method(ElementMatchers.any())
                    .intercept(InvocationHandlerAdapter.of(handler))
                    .make()
                    .load(ClassLoader.getSystemClassLoader())
                    .getLoaded();
            Object[] constructorArgs = Arrays.stream(constructorToUse.getParameters())
                    .map(Parameter::getType)
                    .map(context::getObject).toArray();
            return proxyClass.getConstructor(constructorToUse.getParameterTypes()).newInstance(constructorArgs);
        }
        return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), handler);
    }

    private record ProxyInvocationHandler(Object originalInstance, String className) implements InvocationHandler {
        @Override
        @SneakyThrows
        public Object invoke(Object proxy, Method method, Object[] args) {
            log.warn("Deprecated class: {} was called", className);
            return method.invoke(originalInstance, args);
        }
    }
}