package cn.fcars.infomgr.service.basic;

import cn.fcars.infomgr.entity.basic.Enumerator;
import cn.fcars.infomgr.service.BaseService;
import org.apache.ibatis.jdbc.Null;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface EnumeratorService extends BaseService<Enumerator, Null> {
    List<Enumerator> findByPara (String gnlb, String dmlb);
}
