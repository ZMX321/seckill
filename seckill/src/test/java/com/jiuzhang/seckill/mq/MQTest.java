package com.jiuzhang.seckill.mq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class MQTest {


    @Autowired
    private RocketMQService service;

    @Test
    public void sendMQTest() throws Exception{
        service.sendMessage("test-jiuzhang","Hello World" + new Date().toString());
    }
}
