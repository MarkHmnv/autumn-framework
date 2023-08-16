package com.markhmnv.autumnframework.demo;

import com.markhmnv.autumnframework.annotation.Injectable;

import java.util.List;

@Injectable
public class DemoRepository {
    private final List<String> users;

    public DemoRepository() {
        this.users = List.of("User1", "User2", "User3");
    }

    public List<String> findAll() {
        return users;
    }
}
