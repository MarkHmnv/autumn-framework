package com.markhmnv.autumnframework.demo;

import com.markhmnv.autumnframework.core.annotation.Injectable;

import java.io.Serializable;

import static com.markhmnv.autumnframework.core.enums.Scope.PROTOTYPE;

@Injectable(scope = PROTOTYPE)
public class DemoInterfaceImpl implements DemoInterface, Serializable {
    @Override
    public void doSomething() {
        System.out.println("Do something");
    }
}
