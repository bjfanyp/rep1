package cn.fcars.infomgr.mapper.tuangou;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VehicleTuanGouMapper extends BaseMapper<VehicleTuanGou, BaseQuery> {

    VehicleTuanGou findByStringID(@Param("id") String id);

    List<VehicleTuanGou> findByQuery(BaseQuery data);

    int update(VehicleTuanGou baseVehicle);

    void deleteByStringID(@Param("id") String id);

    void gqByStringID(@Param("id") String id);

    int add(VehicleTuanGou data);

    List<VehicleTuanGou> findByProductID(@Param("id") String id);

    int checkUserIDExist(@Param("id")String id,@Param("userID")int userID);

}
