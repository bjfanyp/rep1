package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.entity.basic.UserRole;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;

import java.util.List;

/**
 * Created by fanyp on 2018/11/25.
 */
public interface UserRoleMapper extends BaseMapper<UserRole, Null> {

    List<UserRole> findByPara(@Param("userID") Integer userID, @Param("roleID") Integer roleID);
    int deleteByUserID(@Param("userID") Integer userID);
}
