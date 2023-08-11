package com.markhmnv.autumnframework.core.service;

import com.markhmnv.autumnframework.core.service.impl.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object t, ApplicationContext context);
}
