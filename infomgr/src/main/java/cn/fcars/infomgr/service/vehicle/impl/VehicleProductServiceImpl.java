package cn.fcars.infomgr.service.vehicle.impl;
import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.mapper.vehicle.VehicleMgrMapper;
import cn.fcars.infomgr.mapper.vehicle.VehicleProductMapper;
import cn.fcars.infomgr.service.tuangou.VehicleTuanGouService;
import cn.fcars.infomgr.service.vehicle.VehicleMgrService;
import cn.fcars.infomgr.service.vehicle.VehicleProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.aspectj.bridge.Version.getTime;

@Service
public class VehicleProductServiceImpl implements VehicleProductService {

    @Autowired
    Static aStatic;

    @Autowired
    VehicleProductMapper vehicleProductMapper;
    @Autowired
    VehicleTuanGouService vehicleTuanGouService;

    @Override
    public VehicleProduct findByID(Integer id) {
        return null;
    }

    @Override
    public VehicleProduct findByStringID(String id) throws Exception {
        VehicleProduct product=new VehicleProduct();
        List<VehicleTuanGou> tuanGouList = vehicleTuanGouService.findByProductID(id);
        Calendar calendar = Calendar.getInstance();
        Date date =new Date();
        int cnt=0;

        if(tuanGouList.size()>0){
            for(int i=0;i<tuanGouList.size();i++){
                calendar.setTime(tuanGouList.get(i).getTgJZDate());
                calendar.add(Calendar.DATE, 1);
                if(calendar.getTime().getTime()<date.getTime()){
                    vehicleTuanGouService.gqByStringID(tuanGouList.get(i).getTgID());
                    if(cnt==0){
                        cnt=tuanGouList.get(i).getTgCount();
                    }
                    else{
                        cnt=cnt+tuanGouList.get(i).getTgCount();
                    }
                }
            }
        }
        product=vehicleProductMapper.findByStringID(id);
        if(cnt!=0){
            product.setpKTCount(product.getpKTCount()+cnt);
            vehicleProductMapper.update(product);
        }
        return product;
    }

    @Override
    public VehicleProduct findByName(String name) {
        return vehicleProductMapper.findByName(name);
    }

    @Override
    public List<VehicleProduct> findAll() {
        return vehicleProductMapper.findAll();
    }

    @Override
    public List<VehicleProduct> findByQuery(BaseQuery data) {
        return null;
    }

    @Override
    public PageInfo<VehicleProduct> findByPage(BaseQuery data) {
//        Integer pageSize =  aStatic.getPageSize();
        Integer pageSize =  5;
        Integer pageNum=1;
        if (data.getPageNum()!=null) {
            pageNum=data.getPageNum();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<VehicleProduct> vehicleList = vehicleProductMapper.findByQuery(data);
        PageInfo<VehicleProduct> pageInfo = new PageInfo(vehicleList);
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
    public void add(VehicleProduct data) throws Exception {
        vehicleProductMapper.add(data);
    }

    @Override
    public void update(VehicleProduct data) throws Exception {
        vehicleProductMapper.update(data);
    }

    @Override
    public void deleteByID(Integer id) throws Exception {
    }

    @Override
    public int delete(VehicleProduct data) throws Exception {
        return 0;
    }

    @Override
    public List<VehicleProduct> findByParentStringID(String id) {
        return null;
    }

    @Override
    public boolean checkExist(String id, String pinPaiID, String cheXingID, String cheKuanID, String colorCode) {
        int count = vehicleProductMapper.checkExist(id,pinPaiID,cheXingID,cheKuanID,colorCode);
        if(count>0){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void deleteByStringID(String id) throws Exception {
        vehicleProductMapper.deleteByStringID(id);
    }
    @Override
    public List<VehicleProduct> getPinPai() {
        return vehicleProductMapper.getPinPai();
    }

    @Override
    public List<VehicleProduct> getCheXing(String pinPai) {
        return vehicleProductMapper.getCheXing(pinPai);
    }


}