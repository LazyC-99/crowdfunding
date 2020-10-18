import com.lzc.crowd.entity.Admin;
import com.lzc.crowd.mapper.AdminMapper;
import com.lzc.crowd.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class test {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService adminService;
    @Test
    public void testSaveAdmin() throws SQLException {
        Admin admin = new Admin(null,"zhangsan","123123","张三","zs@qq.com",null);
        adminService.saveAdmin(admin);
    }
    @Test
    public void testInsertAdmin() throws SQLException {
        Admin admin = new Admin(null,"tom","123123","汤姆","tom@qq.com",null);
        adminMapper.insert(admin);
        System.out.println(adminMapper.insert(admin));
    }
    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
