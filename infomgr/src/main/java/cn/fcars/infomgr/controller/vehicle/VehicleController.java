package cn.fcars.infomgr.controller.vehicle;


import cn.fcars.infomgr.common.*;
import cn.fcars.infomgr.entity.basic.*;
import cn.fcars.infomgr.entity.basic.Account;
import cn.fcars.infomgr.entity.vehicle.BaseVehicle;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.service.basic.*;
import cn.fcars.infomgr.service.vehicle.VehicleMgrService;
import cn.fcars.infomgr.service.vehicle.VehicleService;
import cn.fcars.infomgr.service.vehicle.impl.VehicleServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by fanyp on 2018/11/25.
 */
@Controller
public class VehicleController {
    @Autowired
    VehicleService vehicleService;
    @Autowired
    VehicleMgrService vehicleMgrService;

    public static final String SessionSecurityCode = "imageCode";
    public static final String SessionCurrentUser = "currentUser";
    public static final String SessionCurrentMenu = "currentMenu";
    public static final String SessionMenuList = "menuList";
    public static final String SessionMenuParentList = "menuParentList";
    public static final String userAdmin = "admin";
    public static final String userZt0 = "0";
    public static final String userZt1 = "1";
    public static final Integer userErrorCount = 0;

    @RequestMapping("/baseList")
//    @RequiresPermissions("getBaseVehicleList")
    public String baselist(BaseQuery baseQuery, Model model, HttpSession session)
    {
        try {
            Object object = session.getAttribute("VehicleBaseQuery");
            if(object!=null&&baseQuery.getPinPaiID()==null&&baseQuery.getPageNum()==null) {
                baseQuery=(BaseQuery)object;
            }
            List<BaseVehicle> basePinPaiVehicleList = vehicleService.findByParentStringID(null);
            model.addAttribute("pinPai",basePinPaiVehicleList);
            session.setAttribute("VehicleBaseQuery",baseQuery);
            PageInfo<VehicleInfo> pageInfo =  vehicleMgrService.findByPageBase(baseQuery);
            model.addAttribute("pageInfo", pageInfo);
            return "vehicle/baseList";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }
    @RequestMapping("/baseVehicle/{id}")
    public String toUpdatePage(@PathVariable String id, Model model)
    {
        try {
            VehicleInfo info =new VehicleInfo();
            info.setVehicleID(id);
            BaseVehicle vehicle = vehicleService.findByStringID(id);
            if(vehicle.getParentBaseID()==null){
                info.setPinPai(vehicle.getBaseContent());
                info.setCheXing(null);
                info.setCheKuan(null);
            }
            if(vehicle.getParentBaseID()!=null){
                BaseVehicle vehicle1 = vehicleService.findByStringID(vehicle.getParentBaseID());
                if(vehicle1.getParentBaseID()==null){
                    info.setPinPai(vehicle1.getBaseContent());
                    info.setCheXing(vehicle.getBaseContent());
                    info.setCheKuan(null);
                }
                else{
                    BaseVehicle vehicle2 = vehicleService.findByStringID(vehicle1.getParentBaseID());
                        info.setPinPai(vehicle2.getBaseContent());
                        info.setCheXing(vehicle1.getBaseContent());
                        info.setCheKuan(vehicle.getBaseContent());
                        List<BaseVehicle> pinPai = vehicleService.findByParentStringID(null);
                }
            }
            model.addAttribute("info", info);
            model.addAttribute("vehicle", vehicle);
            return "vehicle/baseUpdate";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PutMapping("/baseVehicle")
//    @RequiresPermissions("rest[dep:put]")
    public String update(@Valid  VehicleInfo info,String baseFirstLetter,String baseOrder,String photoUrl,BindingResult result,HttpSession session ,Model model)
    {
        model.addAttribute("info",info);
        try {

            if(info.getPinPai()==null){
                throw new Exception("品牌不能为空");
            }
            if(info.getPinPai().length()>128){
                throw new Exception("品牌不能超过128位");
            }
            BaseVehicle vehicle = vehicleService.findByStringID(info.getVehicleID());

            vehicle.setBaseContent(info.getPinPai());

            if(vehicle.getParentBaseID()==null){
                if(!vehicleService.checkExist(info.getVehicleID(),info.getPinPai())){
                    throw new Exception("已含有此品牌，不能修改");
                }
                vehicle.setBaseFirstLetter(baseFirstLetter);
                vehicle.setBaseImageUrl(photoUrl);
                vehicle.setBaseOrder(baseOrder);
                vehicleService.update(vehicle);
            }
            if(vehicle.getParentBaseID()!=null){
                if(info.getCheXing()==null){
                    throw new Exception("车型不能为空");
                }
                if(info.getCheXing().length()>128){
                    throw new Exception("车型不能超过128位");
                }
                BaseVehicle vehicle1 = vehicleService.findByStringID(vehicle.getParentBaseID());
                if(vehicle1.getParentBaseID()==null){
                    vehicle.setBaseContent(info.getCheXing());
                    if(!vehicleService.checkExist(info.getVehicleID(),info.getCheXing())){
                        throw new Exception("已含有此车型，不能修改");
                    }
                    vehicle.setBaseOrder(baseOrder);
                    vehicleService.update(vehicle);
                }
                if(vehicle1.getParentBaseID()!=null){
                    if(info.getCheKuan()==null){
                        throw new Exception("车款不能为空");
                    }
                    if(info.getCheKuan().length()>128){
                        throw new Exception("车款不能超过128位");
                    }
                    if(!vehicleService.checkExist(info.getVehicleID(),info.getCheKuan())){
                        throw new Exception("已含有此车款，不能修改");
                    }
                    vehicle.setBaseContent(info.getCheKuan());
                    vehicle.setBaseOrder(baseOrder);
                    vehicleService.update(vehicle);
                }
            }

            return "redirect:/baseList";
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

    @DeleteMapping("/baseVehicle/{id}")
//    @RequiresPermissions("rest[account:delete]")
    @ResponseBody
    public ResultMsg delete(@PathVariable String id)
    {
        try {
            vehicleService.deleteByStringID(id);
            return new ResultMsg(true, "删除成功");
        }
        catch (Exception e) {
            return new ResultMsg(false,"删除失败");
        }
    }
    @GetMapping("/baseVehicle")
//    @RequiresPermissions("rest[account:read]")
    public String toAddPage(HttpSession session, Model model)
    {
        try {
            List<BaseVehicle> baseVehicleList = vehicleService.findByParentStringID(null);
            model.addAttribute("pinPai",baseVehicleList);
            return "vehicle/baseAdd";
        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PostMapping("/baseVehicle")
//    @RequiresPermissions("rest[dep:post]")
    public String add(@Valid VehicleInfo info,String baseFirstLetter,String baseOrder,String photoUrl,BindingResult result, HttpSession session,Model model)
    {
        session.setAttribute("infomation",info);
        try {
            if(info.getPinPai()==null||info.getPinPai().equals("")){
                throw new Exception("品牌不能为空");
            }
            if(info.getPinPai().length()>128){
                throw new Exception("品牌不能超过128位");
            }
            if(info.getCheXing()==null||info.getCheXing().equals(""))
            {
                if(info.getCheKuan()!=null&&!info.getCheKuan().equals("")){
                    throw new Exception("若车款不为空，则车型不能为空");
                }
                if(!vehicleService.checkExist(null,info.getPinPai())){
                    throw new Exception("已含有此品牌，不能添加");
                }
                BaseVehicle baseVehicle = new  BaseVehicle();
                baseVehicle.setBaseID(String.valueOf(new Date().getTime()));
                baseVehicle.setBaseZt("1");
                baseVehicle.setBaseContent(info.getPinPai());
                baseVehicle.setParentBaseID(null);
                baseVehicle.setBaseFirstLetter(baseFirstLetter);
                baseVehicle.setBaseOrder(baseOrder);
                baseVehicle.setBaseImageUrl(photoUrl);
                vehicleService.add(baseVehicle);
            }
            else {
                if(info.getCheKuan()==null||info.getCheKuan().equals("")) {
                    BaseVehicle baseVehiclePinPai=vehicleService.findByName(info.getPinPai());
                    if (baseVehiclePinPai==null) {
                        throw new Exception("品牌未添加，不能直接添加车型");
                    }
                    if (info.getCheXing().length() > 128) {
                        throw new Exception("车型不能超过128位");
                    }
                    if (!vehicleService.checkExist(null, info.getCheXing())) {
                        throw new Exception("已含有此车型，不能添加");
                    }
                    BaseVehicle baseVehicle = new BaseVehicle();
                    baseVehicle.setBaseID(String.valueOf(new Date().getTime()));
                    baseVehicle.setBaseZt("1");
                    baseVehicle.setBaseContent(info.getCheXing());
                    baseVehicle.setParentBaseID(baseVehiclePinPai.getBaseID());
                    baseVehicle.setBaseOrder(baseOrder);
                    vehicleService.add(baseVehicle);
                }
                else{
                    BaseVehicle baseVehiclePinPai=vehicleService.findByName(info.getPinPai());
                    if (baseVehiclePinPai==null) {
                        throw new Exception("品牌未添加，不能直接添加车型");
                    }
                    BaseVehicle baseVehicleCheXing=vehicleService.findByName(info.getCheXing());
                    if (baseVehicleCheXing==null) {
                        throw new Exception("车型未添加，不能直接添加车款");
                    }
                    if (info.getCheKuan().length() > 128) {
                        throw new Exception("车款不能超过128位");
                    }
                    BaseVehicle baseVehicle = new BaseVehicle();
                    baseVehicle.setBaseID(String.valueOf(new Date().getTime()));
                    baseVehicle.setBaseZt("1");
                    baseVehicle.setBaseContent(info.getCheKuan());
                    baseVehicle.setParentBaseID(baseVehicleCheXing.getBaseID());
                    baseVehicle.setBaseOrder(baseOrder);
                    vehicleService.add(baseVehicle);
                }
            }
            return "redirect:/baseVehicle";
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
    @RequestMapping("/getVehicleInfo")
    @ResponseBody
    public ResultData getCheXing(@RequestBody Map<String,String> map, Model model) {

        String id = map.get("id");
        ResultData data = new ResultData();
        List<BaseVehicle> baseVehicleList =  vehicleService.findByParentStringID(id);
        data.setCode(true);
        data.setData(baseVehicleList);
        return data;
    }
}
