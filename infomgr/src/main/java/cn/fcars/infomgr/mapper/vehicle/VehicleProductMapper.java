package cn.fcars.infomgr.mapper.vehicle;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VehicleProductMapper extends BaseMapper<VehicleProduct, BaseQuery> {

    VehicleProduct findByStringID(@Param("id") String id);

    List<VehicleProduct> findByQuery(BaseQuery data);

    int update(VehicleProduct baseVehicle);

    int checkExist(@Param("id") String id, @Param("pinPaiID") String pinPaiID, @Param("cheXingID") String cheXingID, @Param("cheKuanID") String cheKuanID, @Param("colorCode") String colorCode);

    void deleteByStringID(@Param("id") String id);

    int add(VehicleProduct data);

    List<VehicleProduct> getPinPai();

    List<VehicleProduct> getCheXing(@Param("pinPaiID")String pinPaiID);

}
