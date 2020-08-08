package cn.fcars.infomgr.service.cart.impl;
import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.cart.VehicleOrder;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.mapper.cart.VehicleOrderMapper;
import cn.fcars.infomgr.mapper.tuangou.VehicleTuanGouMapper;
import cn.fcars.infomgr.mapper.vehicle.VehicleProductMapper;
import cn.fcars.infomgr.service.cart.VehicleOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleOrderServiceImpl implements VehicleOrderService {

    @Autowired
    Static aStatic;

    @Autowired
    VehicleTuanGouMapper vehicleTuanGouMapper;
    @Autowired
    VehicleProductMapper productMapper;
    @Autowired
    VehicleOrderMapper vehicleOrderMapper;
    @Override
    public List<VehicleOrder> findByParentStringID(String id) {
        return null;
    }
    @Override
    public List<VehicleOrder> findByTuanGouID(String tgID) {
        return vehicleOrderMapper.findByTuanGouID(tgID);
    }


    @Override
    public boolean checkExist(String id, String pinPai, String cheXing, String cheKuan, String colorCode) {
        return false;
    }

    @Override
    public boolean checkUserIDExist(String id, int userID) {
        return vehicleTuanGouMapper.checkUserIDExist(id,userID)>0?false:true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByStringID(String id) throws Exception {
        vehicleOrderMapper.deleteByStringID(id);
    }

    @Override
    public void gqByStringID(String id) throws Exception {

    }
    @Override
    public VehicleOrder findByID(Integer id) {
        return null;
    }

    @Override
    public VehicleOrder findByStringID(String id) {
        return vehicleOrderMapper.findByStringID(id);
    }

    @Override
    public List<VehicleOrder> findByProductID(String id) {
        return vehicleOrderMapper.findByProductID(id);
    }

    @Override
    public VehicleOrder findByName(String name) {
        return null;
    }

    @Override
    public List<VehicleOrder> findAll() {
        return vehicleOrderMapper.findAll();
    }

    @Override
    public List<VehicleOrder> findByQuery(BaseQuery data) {
        return vehicleOrderMapper.findByQuery(data);
    }

    @Override
    public PageInfo<VehicleOrder> findByPage(BaseQuery data) {
        Integer pageSize =  aStatic.getPageSize();
        Integer pageNum=1;
        if (data.getPageNum()!=null) {
            pageNum=data.getPageNum();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<VehicleOrder> tuanGouList = vehicleOrderMapper.findByQuery(data);
        PageInfo<VehicleOrder> pageInfo = new PageInfo(tuanGouList);
        return pageInfo;
    }

    @Override
    public Integer check(String name) {
        return null;
    }

    @Override
    public boolean checkExist(String id, String name) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(VehicleOrder data) throws Exception {
        vehicleOrderMapper.add(data);
//        VehicleTuanGou tuanGou = data.getTuanGou();
//        tuanGou.setTgYtCount(tuanGou.getTgYtCount()+data.getCount());
//        vehicleTuanGouMapper.update(tuanGou);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(VehicleOrder data) throws Exception {
        vehicleOrderMapper.update(data);
    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(VehicleOrder data) throws Exception {
        return 0;
    }
}