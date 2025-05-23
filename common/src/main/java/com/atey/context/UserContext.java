package com.atey.context;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserContext {

    ThreadLocal<Map<Long,String>> tl = new ThreadLocal<>();

    //设置id
    public void setUser(Map<Long,String> map) {
        tl.set(map);
    }

    //获取id
    public Map<Long,String> getUser(){
        return tl.get();
    }

    //清除id
    public void clearUser(){
        tl.remove();
    }
}
