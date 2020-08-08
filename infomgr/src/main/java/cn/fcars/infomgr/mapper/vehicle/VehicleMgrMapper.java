package cn.fcars.infomgr.mapper.vehicle;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.vehicle.BaseVehicle;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VehicleMgrMapper extends BaseMapper<VehicleInfo, BaseQuery> {


    VehicleInfo findByStringID(@Param("id") String id);

    List<VehicleInfo> findByQuery(BaseQuery data);

    int update(VehicleInfo baseVehicle);

    int checkExist(@Param("id") String id, @Param("pinPaiID")String pinPaiID, @Param("cheXingID")String cheXingID, @Param("cheKuanID")String cheKuanID,@Param("colorCode")String colorCode);

    void deleteByStringID(@Param("id") String id);

    int add(VehicleInfo data);

    List<VehicleInfo> getPinPai();

    List<VehicleInfo> getCheXing(@Param("pinPaiID")String pinPaiID);

    List<VehicleInfo> getCheKuan(@Param("pinPaiID")String pinPaiID,@Param("cheXingID")String cheXingID);

    List<VehicleInfo> getVehicleInfo(@Param("pinPaiID")String pinPaiID, @Param("cheXingID")String cheXingID, @Param("cheKuanID")String cheKuanID);

    VehicleInfo getRealVehicleInfo(@Param("pinPaiID")String pinPaiID, @Param("cheXingID")String cheXingID, @Param("cheKuanID")String cheKuanID, @Param("colorCode")String colorCode);

}
