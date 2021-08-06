package com.jiuzhang.seckill.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;

@Service
public class RedisService {

    @Resource
    private JedisPool jedisPool;

    public RedisService setValue(String key,Long value){
        Jedis client = jedisPool.getResource();
        client.set(key,value.toString());
        client.close();
        return this;
    }

    public String getValue(String key){
        Jedis client = jedisPool.getResource();
        String value = client.get(key);
        client.close();
        return value;
    }


    public boolean stockDeductValidation(String key){
        try(Jedis client = jedisPool.getResource()){
            String lua = "if redis.call('exists',KEYS[1]) == 1 then\n" +
                    "        local stock = tonumber(redis.call('get',KEYS[1]))\n" +
                    "        if(stock <= 0) then\n" +
                    "            return -1\n" +
                    "        end;\n" +
                    "        redis.call('decr',KEYS[1]);\n" +
                    "        return stock - 1;\n" +
                    "    end;\n" +
                    "    return -1;";
            Long stock = (Long) client.eval(lua, Collections.singletonList(key),Collections.emptyList());
            if(stock < 0){
                System.out.println("Insuffcient stock");
                return false;
            }
            System.out.println("Seckill succeed");
            return true;
        }catch (Throwable throwable){
            System.out.println("Seckill Failed " + throwable.toString());
            return false;
        }
    }
}
