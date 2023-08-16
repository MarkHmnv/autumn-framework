package com.markhmnv.autumnframework.config;

import com.markhmnv.autumnframework.context.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object t, ApplicationContext context);
}
