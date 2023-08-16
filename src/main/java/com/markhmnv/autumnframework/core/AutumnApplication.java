package com.markhmnv.autumnframework.core;

import com.markhmnv.autumnframework.context.ApplicationContext;
import com.markhmnv.autumnframework.factory.ObjectFactory;
import com.markhmnv.autumnframework.config.Config;
import com.markhmnv.autumnframework.config.JavaConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AutumnApplication {
    /**
     * Runs the application by creating the application context using the provided package name to scan
     * and mapping interface to implementation classes. Returns the created application context.
     *
     * @param packageToScan the package name to scan for components
     * @param ifc2ImplClass a map of interface to implementation classes
     * @return the created application context
     */
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        long startTime = System.currentTimeMillis();
        Config config = new JavaConfig(packageToScan, ifc2ImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        log.info("Application context was created in " + (System.currentTimeMillis() - startTime) + " ms");
        return context;
    }

    public static ApplicationContext run(String packageToScan) {
        return run(packageToScan, new HashMap<>());
    }

    public static ApplicationContext run(Class<?> mainClass) {
        return run(mainClass.getPackageName());
    }
}
