package com.markhmnv.autumnframework.core.service;

import org.reflections.Reflections;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> ifc);
    Reflections getScanner();
}
