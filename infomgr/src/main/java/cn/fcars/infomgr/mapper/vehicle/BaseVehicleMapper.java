package cn.fcars.infomgr.mapper.vehicle;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.Dep;
import cn.fcars.infomgr.entity.vehicle.BaseVehicle;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseVehicleMapper extends BaseMapper<BaseVehicle, BaseQuery> {

//    BaseVehicle findByID(@Param("id") Integer id);

    BaseVehicle findByStringID(@Param("id") String id);

    BaseVehicle findByName(@Param("name") String name);

    int check(@Param("name") String name);

    List<BaseVehicle> findByQuery(BaseQuery data);

    List<BaseVehicle> findByParentStringID(@Param("id")String id);

    int update(BaseVehicle baseVehicle);

    int checkExist(@Param("id")String id,@Param("name")String name);

    void deleteByStringID(@Param("id")String id);

    int add(BaseVehicle baseVehicle);

    List<BaseVehicle> getPinPai();
    List<BaseVehicle> getCheXing(String id);
    List<BaseVehicle> getCheKuan(String id);

    List<BaseVehicle> getProductPinPai();
    List<BaseVehicle> getProductCheXing(String id);
    List<BaseVehicle> getProductCheKuan(String id);
}
