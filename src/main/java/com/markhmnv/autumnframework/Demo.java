package com.markhmnv.autumnframework;

import com.markhmnv.autumnframework.demo.DemoController;
import com.markhmnv.autumnframework.core.service.impl.ApplicationContext;
import com.markhmnv.autumnframework.core.service.impl.AutumnApplication;

public class Demo {
    public static void main(String[] args) {
        ApplicationContext context = AutumnApplication.run(Demo.class);
        DemoController demoController = context.getObject(DemoController.class);
        System.out.println(demoController.getDemoUsers());
        System.out.println(demoController.getSecret());
        demoController.doSomething();
    }
}
