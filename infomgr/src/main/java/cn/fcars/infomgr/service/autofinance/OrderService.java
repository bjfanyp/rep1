package cn.fcars.infomgr.service.autofinance;

import cn.fcars.infomgr.common.AutoFinanceQuery;
import cn.fcars.infomgr.common.TimeProcess;
import cn.fcars.infomgr.entity.autofinance.Order;
import cn.fcars.infomgr.service.BaseService;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface OrderService extends BaseService<Order, AutoFinanceQuery> {
   Order findByStringID(String id);
   void deleteByStringID(String id) throws Exception ;
   void updateData(Order data);
   List<Order> findByPara(String rbID);
   TimeProcess findProcess(String id);
}
