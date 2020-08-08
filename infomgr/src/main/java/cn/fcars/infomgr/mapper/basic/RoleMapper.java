package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.Role;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fanyp on 2018/11/25.
 */
public interface RoleMapper extends BaseMapper<Role, String> {

    Integer check(@Param("id") Integer id, @Param("name") String name);
    List<Role> findByQuery(BaseQuery roleQuery);
}
