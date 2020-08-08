package cn.fcars.infomgr.mapper.autofinance;

import cn.fcars.infomgr.common.AutoFinanceQuery;
import cn.fcars.infomgr.entity.autofinance.Order;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Or;

import java.util.List;

/**
 * Created by fanyp on 2018/11/25.
 */
public interface OrderMapper extends BaseMapper<Order, AutoFinanceQuery> {
    List<Order> findByPara(@Param("rbID") String rbID);
    Order findByStringID(@Param("id") String id);
    int deleteByStringID(@Param("id") String id);
}
