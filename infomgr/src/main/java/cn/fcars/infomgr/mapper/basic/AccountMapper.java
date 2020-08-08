package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.Account;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by fanyp on 2018/11/25.
 */
public interface AccountMapper extends BaseMapper<Account, BaseQuery> {
    Account findByPara(@Param("name")String name,@Param("type")String type);
    Account findAccount(@Param("name")String name, @Param("bank")String bank, @Param("number")String number, @Param("type")String type);
}
