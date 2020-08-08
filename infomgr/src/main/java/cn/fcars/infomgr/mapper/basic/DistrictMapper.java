package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.Dep;
import cn.fcars.infomgr.entity.basic.District;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DistrictMapper extends BaseMapper<District, BaseQuery> {

    District findByStringID(@Param("code") String code);

    List<District> findByParentStringID(@Param("parentCode") String parentCode);

    List<District> findAll();

    List<District> findQuery();
}
