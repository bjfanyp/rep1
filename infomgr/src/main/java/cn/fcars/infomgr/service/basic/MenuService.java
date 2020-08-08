package cn.fcars.infomgr.service.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.Menu;
import cn.fcars.infomgr.service.BaseService;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface MenuService extends BaseService<Menu, BaseQuery> {
    List<Menu> findParentMenu();
    List<Menu> findByParentID(Integer id);
    String findMenuStr(Integer id) throws Exception;
}
