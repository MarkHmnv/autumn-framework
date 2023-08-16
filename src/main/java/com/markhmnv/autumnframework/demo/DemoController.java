package com.markhmnv.autumnframework.demo;

import com.markhmnv.autumnframework.annotation.Injectable;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Injectable
@RequiredArgsConstructor
public class DemoController {
    private final DemoService demoService;
    private final DemoInterface demoInterface;

    public List<String> getDemoUsers() {
        return demoService.findAll();
    }

    public String getSecret() {
        return demoService.getSecret();
    }

    public void doSomething() {
        demoInterface.doSomething();
    }
}
