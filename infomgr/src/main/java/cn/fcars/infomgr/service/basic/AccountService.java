package cn.fcars.infomgr.service.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.Account;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.service.BaseService;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface AccountService extends BaseService<Account, BaseQuery> {
    Account findByPara(String name,String type);
    Account findAccount(String name,String bank,String number, String type);
    void operaAccountAndUserAccount(String id,Account account, User user);
}
