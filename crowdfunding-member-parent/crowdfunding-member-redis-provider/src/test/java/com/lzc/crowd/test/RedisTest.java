package com.lzc.crowd.test;

import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    private Logger logger = LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() throws SQLException {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("key","value",3, TimeUnit.MINUTES);
        logger.info("1231231");
    }
}
