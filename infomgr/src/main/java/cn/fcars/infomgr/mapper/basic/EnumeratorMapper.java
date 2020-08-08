package cn.fcars.infomgr.mapper.basic;


import cn.fcars.infomgr.entity.basic.Enumerator;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;

import java.util.List;

public interface EnumeratorMapper extends BaseMapper<Enumerator, Null> {
    List<Enumerator> findByPara(@Param("gnlb") String gnlb,@Param("dmlb")String dmlb);
}
