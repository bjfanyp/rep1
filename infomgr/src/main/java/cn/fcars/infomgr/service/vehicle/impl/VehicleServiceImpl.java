package cn.fcars.infomgr.service.vehicle.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.Dep;
import cn.fcars.infomgr.entity.basic.MaxNo;
import cn.fcars.infomgr.entity.vehicle.BaseVehicle;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.mapper.basic.DepMapper;
import cn.fcars.infomgr.mapper.basic.MaxNoMapper;
import cn.fcars.infomgr.mapper.vehicle.BaseVehicleMapper;
import cn.fcars.infomgr.service.basic.DepService;
import cn.fcars.infomgr.service.vehicle.VehicleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    Static aStatic;
    @Autowired
    BaseVehicleMapper baseVehicleMapper;

    @Override
    public BaseVehicle findByID(Integer id) {
        return null;
    }

    @Override
    public BaseVehicle findByStringID(String id) {
        return baseVehicleMapper.findByStringID(id);
    }

    @Override
    public BaseVehicle findByName(String name) {
        return baseVehicleMapper.findByName(name);
    }
    @Override
    public List<BaseVehicle> findAll() {
        return baseVehicleMapper.findAll();
    }

    @Override
    public List<BaseVehicle> findByQuery(BaseQuery data) {
        return null;
    }

    @Override
    public PageInfo<BaseVehicle> findByPage(BaseQuery data) {
       return  null;
    }

    @Override
    public Integer check(String name) {
        return null;
    }


    @Override
    public boolean checkExist(String id, String name) {
        int count = baseVehicleMapper.checkExist(id,name);
        if(count>0){
            return false;
        }
        else {
            return true;
        }
    }
    @Override
    public void add(BaseVehicle data) throws Exception {
        baseVehicleMapper.add(data);
    }

    @Override
    public void update(BaseVehicle data) throws Exception {
        baseVehicleMapper.update(data);
    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(BaseVehicle data) throws Exception {
        return 0;
    }

    @Override
    public List<BaseVehicle> findByParentStringID(String id) {
        return baseVehicleMapper.findByParentStringID(id);
    }

    @Override
    public void deleteByStringID(String id) throws Exception {
        baseVehicleMapper.deleteByStringID(id);
    }

    @Override
    public List<BaseVehicle> getPinPai() {
        return baseVehicleMapper.getPinPai();
    }
    @Override
    public List<BaseVehicle> getProductPinPai() {
        return baseVehicleMapper.getProductPinPai();
    }

    @Override
    public List<BaseVehicle> getProductCheXing(String id) {
        return baseVehicleMapper.getProductCheXing(id);
    }

    @Override
    public List<BaseVehicle> getProductCheKuan(String id) {
        return baseVehicleMapper.getProductCheKuan(id);
    }

    @Override
    public List<BaseVehicle> getCheXing(String id) {
        return baseVehicleMapper.getCheXing(id);
    }

    @Override
    public List<BaseVehicle> getCheKuan(String id) {
        return baseVehicleMapper.getCheKuan(id);
    }
}