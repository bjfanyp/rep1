package cn.fcars.infomgr.common;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {
    public LinkedHashMap<String, String> builder()
    {
        LinkedHashMap<String, String> map=new LinkedHashMap<>();
        map.put("/static/**","anon");
        map.put("/agent/**","anon");
        map.put("/aliPay/**","anon");
        map.put("/webapi/**","anon");
        map.put("/wechat_article/**","anon");
        map.put("/sms/**","anon");
        map.put("/login.jsp","anon");
        map.put("/login","anon");
        map.put("/generate","anon");
        map.put("/user1","anon");
        map.put("/logout","logout");
        map.put("/main","user");
        map.put("/**","authc,perms");
        return map;
    }
}
