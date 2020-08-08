package cn.fcars.infomgr.service.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.service.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface UserService extends BaseService<User, BaseQuery> {
    void add(User user, Integer roleID);
    void update(User user, Integer roleID) ;
    void reset(Integer id) throws Exception;
    Map<String,Object> login(String username, String password) throws Exception;
    Map<String,Object>checkUsernameExist(String username);
    Map<String, Object> register(String username, String password, String code) throws Exception;
    Map<String,Object>fotgotPassword(String username,String password);
}
