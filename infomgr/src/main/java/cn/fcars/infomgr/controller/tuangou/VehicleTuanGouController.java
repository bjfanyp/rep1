package cn.fcars.infomgr.controller.tuangou;


import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.ResultData;
import cn.fcars.infomgr.common.ResultMsg;
import cn.fcars.infomgr.entity.basic.District;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.mapper.tuangou.VehicleTuanGouMapper;
import cn.fcars.infomgr.service.basic.DistrictService;
import cn.fcars.infomgr.service.tuangou.VehicleTuanGouService;
import cn.fcars.infomgr.service.vehicle.VehicleMgrService;
import cn.fcars.infomgr.service.vehicle.VehicleProductService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
public class VehicleTuanGouController {
    @Autowired
    VehicleTuanGouService tuanGouService;
    @Autowired
    VehicleProductService productService;
    @Autowired
    DistrictService districtService;

    @RequestMapping("/vehicleTuanGouList")
//    @RequiresPermissions("getBaseVehicleList")
    public String vehicleTuanGouList(BaseQuery baseQuery, Model model, HttpSession session)
    {
        try {
            Object object = session.getAttribute("VehicleTuanGouQuery");
            if(null!=object&&null==baseQuery.getPinPaiID()) {
                baseQuery=(BaseQuery)object;
            }
            PageInfo<VehicleTuanGou> pageInfo = tuanGouService.findByPage(baseQuery);
            model.addAttribute("pageInfo", pageInfo);

            List<VehicleProduct> pinPaiList=productService.getPinPai();
            model.addAttribute("pinPai",pinPaiList);
            if(baseQuery.getPinPaiID()!=null&&!baseQuery.getPinPaiID().equals("")){
                List<VehicleProduct> cheXingList=productService.getCheXing(baseQuery.getPinPaiID());
                model.addAttribute("cheXing",cheXingList);
            }

            session.setAttribute("VehicleTuanGouQuery",baseQuery);

            return "tuangou/tuanGouList";
        }
        catch (Exception e){
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @PostMapping("/vehicleTuanGou")
//    @RequiresPermissions("rest[dep:post]")
    public String add(@Valid VehicleTuanGou tuanGou,String PID,BindingResult result, HttpSession session,Model model)
    {
        try {
            VehicleProduct vehicleProduct =productService.findByStringID(PID);
            User temp =(User)session.getAttribute("currentUser");
            if(!tuanGouService.checkUserIDExist(PID,temp.getUserID())){
                throw new Exception("当前用户已经发起此商品的团购，不能再次发起");
            }
            if(tuanGou.getTgCount()<6){
                throw new Exception("开团数量不能少于6辆");
            }
            if(tuanGou.getTgCount()>12){
                throw new Exception("开团数量不能多于12辆");
            }
            if(tuanGou.getTgCount()>vehicleProduct.getpKTCount()){
                throw new Exception("开团数量不能多于可团购数量");
            }
            if(tuanGou.getTgJZDate()==null){
                throw new Exception("截至日期不能为空");
            }
            if(tuanGou.getTgJZDate().getTime()>vehicleProduct.getYxq().getTime()){
                throw new Exception("截至日期不能超过有效期");
            }
            tuanGou.setTgID(String.valueOf(new Date().getTime()));
            tuanGou.setTgFbDate(new Date());
            tuanGou.setTgZt("1");
            tuanGou.setIsDelete("0");
            tuanGou.setProduct(vehicleProduct);
            tuanGou.setUser(temp);
            tuanGou.setTgYtCount(0);
            tuanGouService.add(tuanGou);

            return "redirect:/vehicleProductList";
        }
        catch (Exception e) {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>30) {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("exception",errorMsg);
            return "forward:/error.jsp";
        }
    }

    @RequestMapping("/tuanGou/{id}")
    public String toAddPage(@PathVariable String id, Model model)
    {
        try {
            VehicleProduct product = productService.findByStringID(id);
            model.addAttribute("product", product);
            List<District> districtList= new ArrayList<District>();
            String[] citys = product.getAllowArea().split(",");
            for(int i=0;i<citys.length;i++){
                districtList.add(districtService.findByStringID(citys[i]));
            }
            model.addAttribute("citys", districtList);
            return "tuangou/tuanGouAdd";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @GetMapping("/vehicleTuanGou/{id}")
    public String toViewPage(@PathVariable String id, Model model)
    {
        try {
            VehicleTuanGou tuanGou =tuanGouService.findByStringID(id);
            model.addAttribute("tuanGou",tuanGou);
            return "tuangou/tuanGouView";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @RequestMapping("/vehicleTuanGouUpdate/{id}")
    public String toUpdatePage(@PathVariable String id, Model model)
    {
        try {
            VehicleTuanGou tuanGou =tuanGouService.findByStringID(id);
            model.addAttribute("tuanGou",tuanGou);
            return "tuangou/tuanGouUpdate";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PutMapping("/vehicleTuanGou")
//    @RequiresPermissions("rest[dep:put]")
    public String update(@Valid VehicleTuanGou tuanGou,BindingResult result,HttpSession session ,Model model)
    {
        try {
            if(tuanGou.getTgCount()<6){
                throw new Exception("开团数量不能少于6辆");
            }
            if(tuanGou.getTgCount()>12){
                throw new Exception("开团数量不能多于12辆");
            }
            tuanGouService.update(tuanGou);
            return "redirect:/vehicleTuanGouList";
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
    @DeleteMapping("/vehicleTuanGou/{id}")
    @ResponseBody
    public ResultMsg delete(@PathVariable String id)
    {
        try {
            tuanGouService.deleteByStringID(id);
            return new ResultMsg(true, "删除成功");
        }
        catch (Exception e) {
            return new ResultMsg(false,"删除失败");
        }
    }

}