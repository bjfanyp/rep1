package cn.fcars.infomgr.mapper.autofinance;

import cn.fcars.infomgr.common.AutoFinanceQuery;
import cn.fcars.infomgr.entity.autofinance.OrderOperate;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fanyp on 2018/11/25.
 */
public interface OrderOperateMapper extends BaseMapper<OrderOperate, AutoFinanceQuery> {
    int deleteByOrderID(@Param("orderID") String orderID);
    List<OrderOperate> findByPara(AutoFinanceQuery data);
}
