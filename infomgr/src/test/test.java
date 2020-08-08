import com.github.pagehelper.StringUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class test {

    @Test
    public void testMethod()
    {
//        String s1=null;
//        String s2="";
//        Boolean b1 =StringUtil.isEmpty(s1);
//        Boolean b2 =StringUtil.isEmpty(s2);
//        System.out.println("b1:"+b1+"/r/n"+"b2:"+b2);
//        String pwd=new SimpleHash("MD5","123456", ByteSource.Util.bytes("admin"),3).toString();
//        System.out.println(pwd);
        String a1="s0lution";
        String pattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$";

        System.out.println(Pattern.matches(pattern,a1));
    }
}
