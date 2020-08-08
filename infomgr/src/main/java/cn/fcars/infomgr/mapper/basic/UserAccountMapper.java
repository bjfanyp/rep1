package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.entity.basic.UserAccount;
import cn.fcars.infomgr.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAccountMapper extends BaseMapper<UserAccount, BaseQuery> {

    List<UserAccount> findByQuery(BaseQuery data);

    UserAccount findByStringID(@Param("id")String id);

    int add(UserAccount data);

    int update(UserAccount data);

    int delete(String id);

}
