package com.jiuzhang.seckill;

import com.jiuzhang.seckill.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedisDemoTest {

    @Resource
    private RedisService redisService;

    @Test
    public void stockTest(){
        String value = redisService.setValue("stock:12",10L).getValue("stock:12");
        System.out.println(value);

    }


}
