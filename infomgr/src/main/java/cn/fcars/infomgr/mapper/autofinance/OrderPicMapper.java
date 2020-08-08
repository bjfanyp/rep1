package cn.fcars.infomgr.mapper.autofinance;

import cn.fcars.infomgr.common.AutoFinanceQuery;
import cn.fcars.infomgr.entity.autofinance.OrderPic;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
/**
 * Created by fanyp on 2018/11/25.
 */
public interface OrderPicMapper extends BaseMapper<OrderPic, AutoFinanceQuery> {
    int deleteByOrderID(@Param("orderID") String orderID);
}
