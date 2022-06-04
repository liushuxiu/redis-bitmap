package com.uestc.redissortintegral.controller;

import com.uestc.redissortintegral.service.ActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;
import javax.annotation.Resource;

@RestController
public class PaihangbangController {
    @Autowired
    private JedisCluster jedisCluster;

    @GetMapping("hello")
    public String hello() {
        String set = jedisCluster.set("ly", "six");
        System.out.println(jedisCluster.get("ly"));
        return set;
    }

    @Resource
    private ActiveService activeService;

    @GetMapping("/active")
    public String active() {
        activeService.test();
        return null;
    }
}
