package cn.fcars.infomgr.service.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.Role;
import cn.fcars.infomgr.service.BaseService;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface RoleService extends BaseService<Role, BaseQuery> {
    void add(Role role,String permChoice) throws Exception;
    void update(Role role, String permChoice) throws Exception;
}
