package com.lzc.crowd.controller;

import com.lzc.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    private Logger logger = LoggerFactory.getLogger(RedisController.class);

    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key")String key, @RequestParam("value")String value){
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key,value);
            return ResultEntity.successWithoutData();
        }catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }
    }

    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeout(
            @RequestParam("key")String key,
            @RequestParam("value")String value,
            @RequestParam("time")long time,
            @RequestParam("timeUnit") TimeUnit timeUnit
    ){
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key,value,time,timeUnit);
            return ResultEntity.successWithoutData();
        }catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }
    }
    @RequestMapping("get/redis/value/by/key/remote")
    ResultEntity<String> getRedisValueByKeyRemote(@RequestParam("key")String key){
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String value = operations.get(key);
            return ResultEntity.successWithData(value);
        }catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }
    }

    @RequestMapping("remove/redis/key/remote")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key")String key){
        try {
            stringRedisTemplate.delete(key);
            return ResultEntity.successWithoutData();
        }catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }
    }
}
