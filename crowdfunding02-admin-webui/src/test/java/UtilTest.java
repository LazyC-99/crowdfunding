import com.lzc.crowd.util.CrowdUtil;
import org.junit.Test;

public class UtilTest {
    @Test
    public void testMd5 () {
        String source = "123123";
        String encoded = CrowdUtil.md5(source);
        System.out.println(encoded);
    }
}
