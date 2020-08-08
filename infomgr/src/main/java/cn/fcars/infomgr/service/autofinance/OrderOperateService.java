package cn.fcars.infomgr.service.autofinance;


import cn.fcars.infomgr.common.AutoFinanceQuery;
import cn.fcars.infomgr.entity.autofinance.OrderOperate;
import cn.fcars.infomgr.service.BaseService;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface OrderOperateService extends BaseService<OrderOperate, AutoFinanceQuery> {
     List<OrderOperate> findByPara(AutoFinanceQuery data);
     void saveOrUpdate(OrderOperate data) throws Exception;
}
