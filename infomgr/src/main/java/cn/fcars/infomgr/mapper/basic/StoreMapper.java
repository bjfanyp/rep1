package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.Account;
import cn.fcars.infomgr.entity.basic.Store;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by fanyp on 2018/11/25.
 */
public interface StoreMapper extends BaseMapper<Store, BaseQuery> {

    Store findByStringID(@Param("id") String id);
}
