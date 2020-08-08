package cn.fcars.infomgr.service.vehicle.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.vehicle.BaseVehicle;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.mapper.vehicle.BaseVehicleMapper;
import cn.fcars.infomgr.mapper.vehicle.VehicleMgrMapper;
import cn.fcars.infomgr.service.vehicle.VehicleMgrService;
import cn.fcars.infomgr.service.vehicle.VehicleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleMgrServiceImpl implements VehicleMgrService {

    @Autowired
    Static aStatic;
    @Autowired
    VehicleMgrMapper vehicleMgrMapper;
    @Autowired
    BaseVehicleMapper baseVehicleMapper;

    @Override
    public VehicleInfo findByID(Integer id) {
        return null;
    }

    @Override
    public VehicleInfo findByStringID(String id) {
        return vehicleMgrMapper.findByStringID(id);
    }

    @Override
    public VehicleInfo findByName(String name) {
        return vehicleMgrMapper.findByName(name);
    }
    @Override
    public List<VehicleInfo> findAll() {
        return vehicleMgrMapper.findAll();
    }

    @Override
    public List<VehicleInfo> findByQuery(BaseQuery data) {
        return null;
    }

    @Override
    public PageInfo<VehicleInfo> findByPage(BaseQuery data) {
        Integer pageSize =  aStatic.getPageSize();
        Integer pageNum=1;
        if (data.getPageNum()!=null) {
            pageNum=data.getPageNum();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<VehicleInfo> vehicleList = vehicleMgrMapper.findByQuery(data);
        PageInfo<VehicleInfo> pageInfo = new PageInfo(vehicleList);
        return pageInfo;
    }

    @Override
    public PageInfo<VehicleInfo> findByPageBase(BaseQuery data) {
        if (data.getPinPai()!=null&&data.getPinPai().equals("0")) {
            data.setPinPai(null);
        }
        if (data.getCheXing()!=null&&data.getCheXing().equals("0")) {
            data.setCheXing(null);
        }
        List<BaseVehicle> baseVehicleList = baseVehicleMapper.findByQuery(data);
        List<VehicleInfo> list = new ArrayList<>();
        for(int i=0;i<baseVehicleList.size();i++){
            BaseVehicle baseVehicle = baseVehicleList.get(i);
            if(baseVehicle.getParentBaseID() == null||baseVehicle.getParentBaseID().equals("")) {
                VehicleInfo info = new VehicleInfo();
                info.setVehicleID(baseVehicle.getBaseID());
                info.setPinPai(baseVehicle.getBaseContent());
                list.add(info);
                for (int j = 0; j < baseVehicleList.size(); j++) {
                    if(baseVehicleList.get(j).getParentBaseID()!=null && baseVehicleList.get(j).getParentBaseID().equals(info.getVehicleID())){
                        VehicleInfo info1 = new VehicleInfo();
                        info1.setVehicleID(baseVehicleList.get(j).getBaseID());
                        info1.setPinPai(info.getPinPai());
                        info1.setCheXing(baseVehicleList.get(j).getBaseContent());
                        list.add(info1);
                        for (int m = 0; m < baseVehicleList.size(); m++) {
                            if(baseVehicleList.get(m).getParentBaseID()!=null && baseVehicleList.get(m).getParentBaseID().equals(info1.getVehicleID())) {
                                VehicleInfo info2 = new VehicleInfo();
                                info2.setVehicleID(baseVehicleList.get(m).getBaseID());
                                info2.setPinPai(info1.getPinPai());
                                info2.setCheXing(info1.getCheXing());
                                info2.setCheKuan(baseVehicleList.get(m).getBaseContent());
                                list.add(info2);
                            }
                        }
                    }
                }
            }
        }
        Integer pageSize =  aStatic.getPageSize();
        Integer pageNum=1;
        if (data.getPageNum()!=null) {
            pageNum=data.getPageNum();
        }
        int fromIndex = 0;
        int toIndex = 0;
        int total = list.size();
        if (total / pageSize == 0 && total % pageSize > 0) {
            fromIndex = 0;
            toIndex = total;
        } else {
            if (total / pageSize >= 1 && total % pageSize >= 0) {
                fromIndex = pageSize * (pageNum - 1);
                if (pageSize * pageNum >= total) {
                    toIndex = total;
                } else {
                    toIndex = pageSize * pageNum;
                }
            }
        }
        list = list.subList(fromIndex, toIndex);
        PageHelper.startPage(pageNum, pageSize);

        PageInfo<VehicleInfo> pageInfo = new PageInfo(list);
        if(pageNum==1){
            pageInfo.setHasPreviousPage(false);
            pageInfo.setHasNextPage(true);
        }
        else if(pageNum==total/pageSize+1){
            pageInfo.setHasPreviousPage(true);
            pageInfo.setHasNextPage(false);
        }
        else {
            pageInfo.setHasPreviousPage(true);
            pageInfo.setHasNextPage(true);
        }
        pageInfo.setTotal(total);
        pageInfo.setPages(total/pageSize+1);
        pageInfo.setPageNum(pageNum);
        return pageInfo;
    }

    @Override
    public Integer check(String userID) {
        return vehicleMgrMapper.check(userID);
    }

    @Override
    public boolean checkExist(String id, String name) {
        return false;
    }

    @Override
    public void add(VehicleInfo data) throws Exception {
        vehicleMgrMapper.add(data);
    }

    @Override
    public void update(VehicleInfo data) throws Exception {
        vehicleMgrMapper.update(data);
    }


    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(VehicleInfo data) throws Exception {
        return 0;
    }


    @Override
    public List<VehicleInfo> findByParentStringID(String id) {
        return null;
    }

    @Override
    public boolean checkExist(String id, String pinPaiID, String cheXingID, String cheKuanID, String colorCode) {
        int count = vehicleMgrMapper.checkExist(id,pinPaiID,cheXingID,cheKuanID,colorCode);
        if(count>0){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void deleteByStringID(String id) throws Exception {
        vehicleMgrMapper.deleteByStringID(id);
    }

    @Override
    public List<VehicleInfo> getVehicleInfo(String pinPaiID, String cheXingID, String cheKuanID) {
        return vehicleMgrMapper.getVehicleInfo(pinPaiID,cheXingID,cheKuanID);
    }

    @Override
    public VehicleInfo getRealVehicleInfo(String pinPaiID, String cheXingID, String cheKuanID,String colorCode) {
        return vehicleMgrMapper.getRealVehicleInfo(pinPaiID,cheXingID,cheKuanID,colorCode);
    }

    @Override
    public List<VehicleInfo> getPinPai() {
        return vehicleMgrMapper.getPinPai();
    }

    @Override
    public List<VehicleInfo> getCheXing(String pinPai) {
        return vehicleMgrMapper.getCheXing(pinPai);
    }

    @Override
    public List<VehicleInfo> getCheKuan(String pinPai, String cheXing) {
        return vehicleMgrMapper.getCheKuan(pinPai,cheXing);
    }
}
