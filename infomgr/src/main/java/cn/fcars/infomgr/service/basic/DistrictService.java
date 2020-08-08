package cn.fcars.infomgr.service.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.District;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.service.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface DistrictService extends BaseService<District, BaseQuery> {
    District findByStringID(String code);
    List<District> findByParentStringID(String parentCode);
    String getCity() throws Exception;
    String getUpdateCity(String code) throws Exception;
    String getViewCity(String code) throws Exception;

}
