package cn.fcars.infomgr.controller.vehicle;


import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.ResultData;
import cn.fcars.infomgr.common.ResultMsg;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.Enumerator;
import cn.fcars.infomgr.entity.vehicle.BaseVehicle;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.mapper.vehicle.VehicleMgrMapper;
import cn.fcars.infomgr.service.basic.*;
import cn.fcars.infomgr.service.vehicle.VehicleMgrService;
import cn.fcars.infomgr.service.vehicle.VehicleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fanyp on 2018/11/25.
 */
@Controller
public class VehicleMgrController {

    public static final String SessionSecurityCode = "imageCode";
    public static final String SessionCurrentUser = "currentUser";
    public static final String SessionCurrentMenu = "currentMenu";
    public static final String SessionMenuList = "menuList";
    public static final String SessionMenuParentList = "menuParentList";
    public static final String userAdmin = "admin";
    public static final String userZt0 = "0";
    public static final String userZt1 = "1";
    public static final Integer userErrorCount = 0;

    @Autowired
    VehicleMgrService vehicleService;
    @Autowired
    VehicleService baseService;
    @Autowired
    EnumeratorService enumeratorService;
    @RequestMapping("/vehicleMgrList")
//    @RequiresPermissions("getBaseVehicleList")
    public String vehicleMgr(BaseQuery baseQuery, Model model, HttpSession session)
    {
        try {
            Object object = session.getAttribute("VehicleMgrQuery");
            if(object!=null&&baseQuery.getPinPaiID()==null) {
                baseQuery=(BaseQuery)object;
            }
            PageInfo<VehicleInfo> pageInfo = vehicleService.findByPage(baseQuery);
            List<VehicleInfo> pinPaiList=vehicleService.getPinPai();
            model.addAttribute("pinPaiList",pinPaiList);
            if(baseQuery.getPinPaiID()!=null&&!baseQuery.getPinPaiID().equals("")){
                List<VehicleInfo> cheXingList=vehicleService.getCheXing(baseQuery.getPinPaiID());
                model.addAttribute("cheXingList",cheXingList);
                if(baseQuery.getCheXingID()!=null&&!baseQuery.getCheXingID().equals("")){
                    List<VehicleInfo> cheKuanList=vehicleService.getCheKuan(baseQuery.getPinPaiID(),baseQuery.getCheXingID());
                    model.addAttribute("cheKuanList",cheKuanList);
                }
            }
            model.addAttribute("pageInfo",pageInfo);
            session.setAttribute("VehicleMgrQuery",baseQuery);
            return "vehicle/vehicleMgrList";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }
    @RequestMapping("/vehicleMgr/{id}")
    public String toUpdatePage(@PathVariable String id, Model model,HttpSession session)
    {
        try {
            VehicleInfo vehicleInfo = vehicleService.findByStringID(id);
            model.addAttribute("vehicle",vehicleInfo);
            List<Enumerator> colorList = enumeratorService.findByPara("color","1008");
            model.addAttribute("color",colorList);
            return "vehicle/vehicleMgrUpdate";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PutMapping("/vehicleMgr")
//    @RequiresPermissions("rest[dep:put]")
    public String update(@Valid  VehicleInfo info,BindingResult result,HttpSession session ,Model model)
    {
        model.addAttribute("VehicleInfo",info);
        try {
            VehicleInfo vehicleInfo = vehicleService.findByStringID(info.getVehicleID());
            info.setPinPaiID(vehicleInfo.getPinPaiID());
            info.setCheXingID(vehicleInfo.getCheXingID());
            info.setCheKuanID(vehicleInfo.getCheKuanID());
            info.setPinPai(vehicleInfo.getPinPai());
            info.setCheXing(vehicleInfo.getCheXing());
            info.setCheKuan(vehicleInfo.getCheKuan());
            if(!vehicleService.checkExist(info.getVehicleID(),info.getPinPaiID(),info.getCheXingID(),info.getCheKuanID(),info.getColorCode()))
            {
                throw  new Exception("当前数据已存在，不能修改");
            }
            List<Enumerator> colorList = enumeratorService.findByPara("color","1008");
            for(int i=0;i<colorList.size();i++){
                if(colorList.get(i).getDmz().equals(info.getColorCode())){
                    info.setColor(colorList.get(i).getDmsx());
                    break;
                }
            }
            vehicleService.update(info);
            return "redirect:/vehicleMgrList";
        }
        catch (Exception e) {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>15)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("exception",errorMsg);
            return "forward:/error.jsp";
        }
    }

    @DeleteMapping("/vehicleMgr/{id}")
//    @RequiresPermissions("rest[account:delete]")
    @ResponseBody
    public ResultMsg delete(@PathVariable String id)
    {
        try {
            vehicleService.deleteByStringID(id);
            return new ResultMsg(true, "删除成功");
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"删除失败");
        }
    }

    @RequestMapping("/vehicleMgr/getCheXing")
    @ResponseBody
    public ResultData getVehicleMgrCheXing(@RequestBody Map<String,String> map)
    {
        try {
            String id = map.get("pinPaiID");
            List<VehicleInfo> vehicleInfoList = vehicleService.getCheXing(id);
            return new ResultData(true, vehicleInfoList,"获取成功");
        }
        catch (Exception e)
        {
            return new ResultData(false,null,"获取失败");
        }
    }
    @RequestMapping("/vehicleMgr/getCheKuan")
    @ResponseBody
    public ResultData getVehicleMgrCheKuan(@RequestBody Map<String,String> map)
    {
        try {
            String pinPai = map.get("pinPaiID");
            String cheXing = map.get("cheXingID");
            List<VehicleInfo> vehicleInfoList = vehicleService.getCheKuan(pinPai,cheXing);
            return new ResultData(true, vehicleInfoList,"获取成功");
        }
        catch (Exception e)
        {
            return new ResultData(false,null,"获取失败");
        }
    }
    @RequestMapping("/vehicleMgr/getVehicleInfo")
    @ResponseBody
    public ResultData getVehicleMgrInfo(@RequestBody Map<String,String> map)
    {
        try {
            String pinPaiID = map.get("pinPaiID");
            String cheXingID = map.get("cheXingID");
            String cheKuanID = map.get("cheKuanID");
            List<VehicleInfo> vehicleInfoList = vehicleService.getVehicleInfo(pinPaiID,cheXingID,cheKuanID);
            return new ResultData(true, vehicleInfoList,"获取成功");
        }
        catch (Exception e)
        {
            return new ResultData(false,null,"获取失败");
        }
    }
    @GetMapping("/vehicleMgr")
//    @RequiresPermissions("rest[account:read]")
    public String toAddPage(HttpSession session, Model model)
    {
        try {
            List<BaseVehicle> pinPaiList = baseService.findByParentStringID(null);
            model.addAttribute("pinPai",pinPaiList);
            model.addAttribute("pinPaiText",pinPaiList.get(0).getBaseContent());
            if(pinPaiList.size()>0){
                List<BaseVehicle> cheXingList = baseService.findByParentStringID(pinPaiList.get(0).getBaseID());
                model.addAttribute("cheXing",cheXingList);
                model.addAttribute("cheXingText",cheXingList.get(0).getBaseContent());
                if(cheXingList.size()>0){
                    List<BaseVehicle> cheKuanList = baseService.findByParentStringID(cheXingList.get(0).getBaseID());
                    model.addAttribute("cheKuan",cheKuanList);
                    model.addAttribute("cheKuanText",cheKuanList.get(0).getBaseContent());
                }
            }
            List<Enumerator> colorList = enumeratorService.findByPara("color","1008");
            model.addAttribute("color",colorList);
            return "vehicle/vehicleMgrAdd";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PostMapping("/vehicleMgr")
//    @RequiresPermissions("rest[dep:post]")
    public String add(VehicleInfo info,HttpServletRequest request, Model model)
    {
        try {
            info.getPinPai();
            info.setZt("1");
            if(!vehicleService.checkExist(null,info.getPinPaiID(),info.getCheXingID(),info.getCheKuanID(),info.getColorCode())){
                throw new Exception("当前车型已存在");
            }
            if(info.getZhidaojia()<=0){
                throw new Exception("指导价不正确，请重新输入");
            }
            List<Enumerator> colorList = enumeratorService.findByPara("color","1008");
            for(int i=0;i<colorList.size();i++){
                if(colorList.get(i).getDmz().equals(info.getColorCode())){
                    info.setColor(colorList.get(i).getDmsx());
                    break;
                }
            }
            info.setVehicleID(String.valueOf(new Date().getTime()));
            vehicleService.add(info);
            return "redirect:/vehicleMgrList";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>15)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("exception",errorMsg);
            return "forward:/error.jsp";
        }
    }
}
