package cn.fcars.infomgr.service.basic;

import cn.fcars.infomgr.entity.basic.Role;
import cn.fcars.infomgr.entity.basic.RolePerm;
import cn.fcars.infomgr.service.BaseService;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface RolePermService extends BaseService<RolePerm, String> {
    List<RolePerm> findByParam(Integer roleID, Integer permID);
    int deleteByPermID(Integer id);
    int deleteByRoleID(Integer id);
}
