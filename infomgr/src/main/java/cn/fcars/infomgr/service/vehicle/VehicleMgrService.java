package cn.fcars.infomgr.service.vehicle;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.vehicle.BaseVehicle;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.service.BaseService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface VehicleMgrService extends BaseService<VehicleInfo, BaseQuery> {
    public List<VehicleInfo> findByParentStringID(String id);
    boolean checkExist(String id,  String pinPai,  String cheXing,String cheKuan,String colorCode);
    void deleteByStringID(String id) throws Exception;
    public List<VehicleInfo> getVehicleInfo(String pinPaiID,String cheXingID,String cheKuanID);
    public VehicleInfo getRealVehicleInfo(String pinPaiID,String cheXingID,String cheKuanID,String colorCode);
    public List<VehicleInfo> getPinPai();
    public List<VehicleInfo> getCheXing(String pinPai);
    public List<VehicleInfo> getCheKuan(String pinPai,String cheXing);
    PageInfo<VehicleInfo> findByPageBase(BaseQuery data);
}
