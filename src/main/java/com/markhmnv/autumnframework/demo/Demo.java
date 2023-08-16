package com.markhmnv.autumnframework.demo;

import com.markhmnv.autumnframework.context.ApplicationContext;
import com.markhmnv.autumnframework.core.AutumnApplication;

public class Demo {
    public static void main(String[] args) {
        ApplicationContext context = AutumnApplication.run(Demo.class);
        DemoController demoController = context.getObject(DemoController.class);
        System.out.println(demoController.getDemoUsers());
        System.out.println(demoController.getSecret());
        demoController.doSomething();
    }
}
