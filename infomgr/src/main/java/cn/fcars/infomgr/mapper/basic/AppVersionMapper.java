package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.AppVersion;
import cn.fcars.infomgr.entity.basic.Dep;
import cn.fcars.infomgr.mapper.BaseMapper;

import java.util.List;

public interface AppVersionMapper extends BaseMapper<AppVersion, BaseQuery> {

    List<AppVersion> findByQuery(BaseQuery data);

}
