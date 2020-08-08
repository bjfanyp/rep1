package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.*;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.mapper.basic.DepMapper;
import cn.fcars.infomgr.mapper.basic.DistrictMapper;
import cn.fcars.infomgr.mapper.basic.MaxNoMapper;
import cn.fcars.infomgr.service.basic.DepService;
import cn.fcars.infomgr.service.basic.DistrictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    DistrictMapper districtMapper;

    @Override
    public District findByStringID(String code) {
        return districtMapper.findByStringID(code);
    }

    @Override
    public List<District> findByParentStringID(String parentCode) {
        return districtMapper.findByParentStringID(parentCode);
    }
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }
    public String getCity() throws Exception {
        try {
            List<District> provinceList =  districtMapper.findByParentStringID(null);
            List<District> cityList = districtMapper.findAll();
            String resHead = "[{name:'城市列表', open: true,  isParent:true}";
            String resBody = "";
            String resFoot = "]";
            if (cityList.size() > 0) {
                for (District district : provinceList) {
                    String temp = ",{name:'" + district.getDistrictName() + "', open: false, id: " + district.getDistrictCode();
                        if (cityList.size() > 0) {
                            temp = temp + ",children: [";
                            for (int i = 0; i < cityList.size(); i++) {
                                if(cityList.get(i).getParentCode()!=null&&cityList.get(i).getParentCode().equals(district.getDistrictCode())){
                                    temp = temp + "{name:'" + cityList.get(i).getDistrictName() + "',open:true,id:" + cityList.get(i).getDistrictCode()+"}";
                                    if (i != cityList.size() - 1) {
                                        temp = temp + ",";
                                    }
                                }
                            }
                            temp = temp + "]}";
                        } else {
                            temp = temp + "}";
                        }
                    resBody = resBody + temp;
                    continue;
                }
                return resHead + resBody + resFoot;
            } else {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public String getUpdateCity(String code) throws Exception {
        try {
            String[] productCity=code.split(",");
            List<String> productDisList=new ArrayList<String>();
            for(int i=0;i<productCity.length;i++){
              District district = districtMapper.findByStringID(productCity[i]);
                productDisList.add(district.getParentCode());
            }
            productDisList =removeDuplicate(productDisList);
            List<District> provinceList =  districtMapper.findByParentStringID(null);
            List<District> cityList = districtMapper.findAll();
            String resHead = "[{name:'城市列表', open: true,  isParent:true}";
            String resBody = "";
            String resFoot = "]";
            if (cityList.size() > 0) {
                for (District district : provinceList) {
                    String res = "false";
                    for(int j=0;j<productDisList.size();j++){
                        if(district.getDistrictCode().equals(productDisList.get(j))){
                            res="true";
                            break;
                        }
                    }
                    String temp = ",{name:'" + district.getDistrictName() + "', open: "+res+", id: " + district.getDistrictCode();
                    if (cityList.size() > 0) {
                        temp = temp + ",children: [";
                        for (int i = 0; i < cityList.size(); i++) {
                            if(cityList.get(i).getParentCode()!=null&&cityList.get(i).getParentCode().equals(district.getDistrictCode())){
                                temp = temp + "{name:'" + cityList.get(i).getDistrictName() + "',open:true,id:" + cityList.get(i).getDistrictCode()+"}";
                                if (i != cityList.size() - 1) {
                                    temp = temp + ",";
                                }
                            }
                        }
                        temp = temp + "]}";
                    } else {
                        temp = temp + "}";
                    }
                    resBody = resBody + temp;
                    continue;
                }
                return resHead + resBody + resFoot;
            } else {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public String getViewCity(String code) throws Exception {
        try {
            String[] productCity=code.split(",");
            List<String> productDisList=new ArrayList<String>();
            List<District> provinceList =new ArrayList<District>();
            List<District> cityList =new ArrayList<District>();
            for(int i=0;i<productCity.length;i++){
                District district = districtMapper.findByStringID(productCity[i]);
                productDisList.add(district.getParentCode());
                cityList.add(district);
            }
            productDisList =removeDuplicate(productDisList);

            for(int i=0;i<productDisList.size();i++){
                District district = districtMapper.findByStringID(productDisList.get(i));
                provinceList.add(district);
            }
            String resHead = "[{name:'城市列表', open: true,  isParent:true}";
            String resBody = "";
            String resFoot = "]";
            if (cityList.size() > 0) {
                for (District district : provinceList) {
                    String temp = ",{name:'" + district.getDistrictName() + "', open: true, id: " + district.getDistrictCode();
                    if (cityList.size() > 0) {
                        temp = temp + ",children: [";
                        for (int i = 0; i < cityList.size(); i++) {
                            if(cityList.get(i).getParentCode()!=null&&cityList.get(i).getParentCode().equals(district.getDistrictCode())){
                                temp = temp + "{name:'" + cityList.get(i).getDistrictName() + "',open:true,id:" + cityList.get(i).getDistrictCode()+"}";
                                if (i != cityList.size() - 1) {
                                    temp = temp + ",";
                                }
                            }
                        }
                        temp = temp + "]}";
                    } else {
                        temp = temp + "}";
                    }
                    resBody = resBody + temp;
                    continue;
                }
                return resHead + resBody + resFoot;
            } else {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
    @Override
    public District findByName(String name) {
        return null;
    }

    @Override
    public List<District> findAll() {
        return null;
    }

    @Override
    public District findByID(Integer id) {
        return null;
    }

    @Override
    public List<District> findByQuery(BaseQuery data) {
        return null;
    }

    @Override
    public PageInfo<District> findByPage(BaseQuery data) {
        return null;
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
    public void add(District data) throws Exception {

    }

    @Override
    public void update(District data) throws Exception {

    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(District data) throws Exception {
        return 0;
    }

}

