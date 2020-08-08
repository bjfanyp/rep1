package cn.fcars.infomgr.service.basic;

import cn.fcars.infomgr.entity.basic.UserRole;
import cn.fcars.infomgr.service.BaseService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface UserRoleService extends BaseService<UserRole, Null> {
    List<UserRole> findByPara(@Param("userID") Integer userID, @Param("roleID") Integer roleID);
}
