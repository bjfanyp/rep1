package cn.fcars.infomgr.service.tuangou;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.service.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface VehicleTuanGouService extends BaseService<VehicleTuanGou, BaseQuery> {
    public List<VehicleTuanGou> findByParentStringID(String id);
    boolean checkExist(String id, String pinPai, String cheXing, String cheKuan, String colorCode);
    boolean checkUserIDExist(String id, int userID);
    void deleteByStringID(String id) throws Exception;
    void gqByStringID(String id) throws Exception;
    List<VehicleTuanGou> findByProductID(String id);
}
