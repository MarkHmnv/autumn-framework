package com.markhmnv.autumnframework.demo;

import com.markhmnv.autumnframework.annotation.InjectProperty;
import com.markhmnv.autumnframework.annotation.Injectable;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Injectable
@Deprecated
@RequiredArgsConstructor
public class DemoService {
    @InjectProperty
    private String secret;
    private final DemoRepository demoRepository;

    public List<String> findAll() {
        return demoRepository.findAll();
    }

    public String getSecret() {
        return "Very secret string: " + secret;
    }
}
