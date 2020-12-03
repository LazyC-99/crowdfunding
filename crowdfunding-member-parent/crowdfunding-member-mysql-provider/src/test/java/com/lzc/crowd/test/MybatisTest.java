package com.lzc.crowd.test;

import com.lzc.crowd.mapper.MemberMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.lzc.crowd.mapper")
public class MybatisTest {
    @Autowired
    DataSource dataSource;
    @Autowired
    MemberMapper memberMapper;

    private Logger logger = LoggerFactory.getLogger(MybatisTest.class);

    @Test
    public void test() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.info(connection.toString());

    }
}
