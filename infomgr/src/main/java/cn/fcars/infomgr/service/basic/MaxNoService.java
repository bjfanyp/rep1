package cn.fcars.infomgr.service.basic;

import cn.fcars.infomgr.entity.basic.MaxNo;
import cn.fcars.infomgr.entity.basic.Role;
import cn.fcars.infomgr.service.BaseService;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface MaxNoService extends BaseService<MaxNo, String> {
    MaxNo findByDepID(Integer id);
}
