package cn.fcars.infomgr.service.vehicle;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.service.BaseService;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface VehicleProductService extends BaseService<VehicleProduct, BaseQuery> {
    public List<VehicleProduct> findByParentStringID(String id);
    boolean checkExist(String id, String pinPai, String cheXing, String cheKuan, String colorCode);
    void deleteByStringID(String id) throws Exception;
    List<VehicleProduct> getPinPai();
    List<VehicleProduct> getCheXing(String pinPai);
}
