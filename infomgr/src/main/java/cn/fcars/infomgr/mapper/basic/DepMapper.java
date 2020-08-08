package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.Dep;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepMapper extends BaseMapper<Dep, BaseQuery> {

    Dep findByID( @Param("id") Integer id);

    Dep findByName(@Param("name") String name);

    int check(@Param("name") String name);

    List<Dep> findByPara(@Param("userType") String userType,@Param("isDelete") String isDelete);

    List<Dep> findByQuery(BaseQuery depQuery);

    int update(Dep dep);
}
