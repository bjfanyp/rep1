package cn.fcars.infomgr.service.vehicle;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.Dep;
import cn.fcars.infomgr.entity.vehicle.BaseVehicle;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.service.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface VehicleService extends BaseService<BaseVehicle, BaseQuery> {
    public List<BaseVehicle> findByParentStringID(String id);
    void deleteByStringID(String id) throws Exception;
    List<BaseVehicle> getPinPai();
    List<BaseVehicle> getCheXing(String id);
    List<BaseVehicle> getCheKuan(String id);
    List<BaseVehicle> getProductPinPai();
    List<BaseVehicle> getProductCheXing(String id);
    List<BaseVehicle> getProductCheKuan(String id);
}
