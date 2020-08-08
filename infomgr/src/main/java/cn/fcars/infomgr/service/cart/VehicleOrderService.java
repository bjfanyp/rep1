package cn.fcars.infomgr.service.cart;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.cart.VehicleOrder;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.service.BaseService;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface VehicleOrderService extends BaseService<VehicleOrder, BaseQuery> {
    List<VehicleOrder> findByTuanGouID(String tgID);
    List<VehicleOrder> findByParentStringID(String id);
    boolean checkExist(String id, String pinPai, String cheXing, String cheKuan, String colorCode);
    boolean checkUserIDExist(String id, int userID);
    void deleteByStringID(String id) throws Exception;
    void gqByStringID(String id) throws Exception;
    List<VehicleOrder> findByProductID(String id);
}
