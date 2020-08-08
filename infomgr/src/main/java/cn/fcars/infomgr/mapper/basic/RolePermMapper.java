package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.entity.basic.Role;
import cn.fcars.infomgr.entity.basic.RolePerm;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fanyp on 2018/11/25.
 */
public interface RolePermMapper extends BaseMapper<RolePerm, String> {
    int deleteByPermID(@Param("id") Integer id);
    int deleteByRoleID(@Param("id") Integer id);
    List<RolePerm> findByParam(@Param("roleID") Integer roleID,@Param("permID") Integer permID);
}
