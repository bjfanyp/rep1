package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User, BaseQuery> {

    User findByID( @Param("id")Integer id);

    User findByName( @Param("username")String username);

    int check(@Param("username")String username);

    List<User> findByQuery(BaseQuery userQuery);

    PageInfo<User> findByPage(BaseQuery userQuery);

    int delete(User user);

    int update(User user);

    List<User> findByAttr(@Param("strWhere") String strWhere, @Param("strOrder") String strOrder);
}
