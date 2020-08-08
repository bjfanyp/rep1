package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.entity.basic.MaxNo;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by fanyp on 2018/11/27.
 */
public interface MaxNoMapper extends BaseMapper<MaxNo,String> {

    MaxNo findByDepID(@Param("id") Integer id);
}
