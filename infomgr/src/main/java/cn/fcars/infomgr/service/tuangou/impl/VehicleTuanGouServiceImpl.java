package cn.fcars.infomgr.service.tuangou.impl;
import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.mapper.tuangou.VehicleTuanGouMapper;
import cn.fcars.infomgr.mapper.vehicle.VehicleProductMapper;
import cn.fcars.infomgr.service.tuangou.VehicleTuanGouService;
import cn.fcars.infomgr.service.vehicle.VehicleProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleTuanGouServiceImpl implements VehicleTuanGouService {

    @Autowired
    Static aStatic;

    @Autowired
    VehicleTuanGouMapper vehicleTuanGouMapper;
    @Autowired
    VehicleProductMapper productMapper;

    @Override
    public List<VehicleTuanGou> findByParentStringID(String id) {
        return null;
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
        VehicleTuanGou tuanGou = vehicleTuanGouMapper.findByStringID(id);
        VehicleProduct product = tuanGou.getProduct();
        product.setpKTCount(product.getpKTCount()+tuanGou.getTgCount());
        productMapper.update(product);
        vehicleTuanGouMapper.deleteByStringID(id);
    }

    @Override
    public void gqByStringID(String id) throws Exception {
        vehicleTuanGouMapper.gqByStringID(id);
    }
    @Override
    public VehicleTuanGou findByID(Integer id) {
        return null;
    }

    @Override
    public VehicleTuanGou findByStringID(String id) {
        return vehicleTuanGouMapper.findByStringID(id);
    }

    @Override
    public List<VehicleTuanGou> findByProductID(String id) {
        return vehicleTuanGouMapper.findByProductID(id);
    }


    @Override
    public VehicleTuanGou findByName(String name) {
        return null;
    }

    @Override
    public List<VehicleTuanGou> findAll() {
        return vehicleTuanGouMapper.findAll();
    }

    @Override
    public List<VehicleTuanGou> findByQuery(BaseQuery data) {
        return vehicleTuanGouMapper.findByQuery(data);
    }

    @Override
    public PageInfo<VehicleTuanGou> findByPage(BaseQuery data) {
        Integer pageSize =  aStatic.getPageSize();
        Integer pageNum=1;
        if (data.getPageNum()!=null) {
            pageNum=data.getPageNum();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<VehicleTuanGou> tuanGouList = vehicleTuanGouMapper.findByQuery(data);
        PageInfo<VehicleTuanGou> pageInfo = new PageInfo(tuanGouList);
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
    public void add(VehicleTuanGou data) throws Exception {
        vehicleTuanGouMapper.add(data);
        int cnt=data.getProduct().getpKTCount()-data.getTgCount();
        VehicleProduct product = data.getProduct();
        product.setpKTCount(cnt);
        productMapper.update(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(VehicleTuanGou data) throws Exception {
        VehicleTuanGou temp = vehicleTuanGouMapper.findByStringID(data.getTgID());
        int c1=temp.getTgCount();
        temp.setTgYtCount(data.getTgYtCount());
        temp.setTgCount(data.getTgCount());
        vehicleTuanGouMapper.update(temp);
        int c2=data.getTgCount();
        VehicleProduct product =temp.getProduct();
        product.setpKTCount(product.getpKTCount()+c1-c2);
        productMapper.update(product);
    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(VehicleTuanGou data) throws Exception {
        return 0;
    }
}