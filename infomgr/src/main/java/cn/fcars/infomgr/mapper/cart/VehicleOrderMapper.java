package cn.fcars.infomgr.mapper.cart;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.cart.VehicleOrder;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VehicleOrderMapper extends BaseMapper<VehicleOrder, BaseQuery> {

    VehicleOrder findByStringID(@Param("id") String id);

    List<VehicleOrder> findByTuanGouID(@Param("id")String id);

    List<VehicleOrder> findByQuery(BaseQuery data);

    int update(VehicleOrder baseVehicle);

    void deleteByStringID(@Param("id") String id);

    int add(VehicleOrder data);

    List<VehicleOrder> findByProductID(@Param("id") String id);

    int checkUserIDExist(@Param("id") String id, @Param("userID") int userID);

}
