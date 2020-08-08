package cn.fcars.infomgr.controller.vehicle;


import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.ResultData;
import cn.fcars.infomgr.common.ResultMsg;
import cn.fcars.infomgr.entity.basic.District;
import cn.fcars.infomgr.entity.vehicle.BaseVehicle;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.service.basic.DistrictService;
import cn.fcars.infomgr.service.tuangou.VehicleTuanGouService;
import cn.fcars.infomgr.service.vehicle.VehicleMgrService;
import cn.fcars.infomgr.service.vehicle.VehicleProductService;
import cn.fcars.infomgr.service.vehicle.VehicleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import net.sf.ehcache.util.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by fanyp on 2018/11/25.
 */
@Controller
public class VehicleProductController {
    @Autowired
    VehicleMgrService vehicleService;
    @Autowired
    VehicleProductService productService;
    @Autowired
    DistrictService districtService;
    @Autowired
    VehicleTuanGouService tuanGouService;

    @RequestMapping("/vehicleProduct/getCheXing")
    @ResponseBody
    public ResultData getvehicleProductCheXing(@RequestBody Map<String,String> map)
    {
        try {
            String id = map.get("pinPaiID");
            List<VehicleProduct> vehicleInfoList = productService.getCheXing(id);
            return new ResultData(true, vehicleInfoList,"获取成功");
        }
        catch (Exception e)
        {
            return new ResultData(false,null,"获取失败");
        }
    }

    @RequestMapping("/vehicleProductList")
//    @RequiresPermissions("getBaseVehicleList")
    public String vehicleProductList(BaseQuery baseQuery, Model model, HttpSession session)
    {
        try {
            Object object = session.getAttribute("ProductBaseQuery");
            if(object!=null&&baseQuery.getPinPaiID()==null) {
                baseQuery=(BaseQuery)object;
            }
            PageInfo<VehicleProduct> pageInfo = productService.findByPage(baseQuery);
            model.addAttribute("pageInfo",pageInfo);
            List<VehicleProduct> pinPaiList=productService.getPinPai();
            model.addAttribute("pinPai",pinPaiList);
            if(baseQuery.getPinPaiID()!=null&&!baseQuery.getPinPaiID().equals("")){
                List<VehicleProduct> cheXingList=productService.getCheXing(baseQuery.getPinPaiID());
                model.addAttribute("cheXing",cheXingList);
            }
            session.setAttribute("ProductBaseQuery",baseQuery);
            return "vehicle/productList";
        }
        catch (Exception e){
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @GetMapping("/vehicleProduct")
//    @RequiresPermissions("rest[account:read]")
    public String toAddPage(HttpSession session, Model model)
    {
        try {
            List<VehicleInfo> pinPaiList = vehicleService.getPinPai();
            model.addAttribute("pinPai",pinPaiList);
            if(pinPaiList.size()>0){
                List<VehicleInfo> cheXingList = vehicleService.getCheXing(pinPaiList.get(0).getPinPaiID());
                model.addAttribute("cheXing",cheXingList);
                if(cheXingList.size()>0){
                    List<VehicleInfo> cheKuanList = vehicleService.getCheKuan(pinPaiList.get(0).getPinPaiID(),cheXingList.get(0).getCheXingID());
                    model.addAttribute("cheKuan",cheKuanList);
                    List<VehicleInfo> vehicleInfoList = vehicleService.getVehicleInfo(pinPaiList.get(0).getPinPaiID(),cheXingList.get(0).getCheXingID(),cheKuanList.get(0).getCheKuanID());
                    model.addAttribute("vehicleInfo",vehicleInfoList);
                }
            }
            Calendar calendar =Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH,2);
            model.addAttribute("yxq",calendar.getTime());
            return "vehicle/productAdd";
        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PostMapping("/vehicleProduct")
//    @RequiresPermissions("rest[dep:post]")
    public String add(@Valid VehicleProduct product,VehicleInfo info, BindingResult result, HttpSession session,Model model)
    {
        try {
            if(product.getpCount()<6){
                throw new Exception("数量不能低于6辆");
            }
            if(product.getAdvancePrice()<1000){
                throw new Exception("定金不能低于1000");
            }
            if(product.getSellPrice()<10000){
                throw new Exception("销售价格不能低于10000");
            }
            if(product.getYxq()==null){
                throw new Exception("未设置有效期");
            }
            if(product.getAllowArea()==null){
                throw new Exception("未设置销售区域");
            }
            VehicleInfo newInfo = vehicleService.getRealVehicleInfo(info.getPinPaiID(),info.getCheXingID(),info.getCheKuanID(),info.getColorCode());
            if(newInfo!=null){
                product.setInfo(newInfo);
            }
            product.setFbDate(new Date());
            product.setZt("1");
            product.setpKTCount(product.getpCount());
            product.setPID(String.valueOf(new Date().getTime()));
            productService.add(product);
            return "redirect:/vehicleProductList";
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

    @RequestMapping("/vehicleViewProduct/{id}")
    public String toViewPage(@PathVariable String id, Model model)
    {
        try {
            VehicleProduct productInfo =productService.findByStringID(id);
            model.addAttribute("productInfo", productInfo);
            return "vehicle/productView";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }

    @RequestMapping("/vehicleProduct/{id}")
    public String toUpdatePage(@PathVariable String id, Model model)
    {
        try {
            VehicleProduct productInfo =productService.findByStringID(id);
            model.addAttribute("productInfo", productInfo);
            return "vehicle/productUpdate";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PutMapping("/vehicleProduct")
//    @RequiresPermissions("rest[dep:put]")
    public String update(@Valid VehicleProduct product,BindingResult result,HttpSession session ,Model model)
    {
        try {
            if(tuanGouService.findByProductID(product.getPID()).size()>0){
                throw new Exception("当前商品已经开团，不能修改信息");
            }
            if(product.getpCount()<6){
                throw new Exception("数量不能低于6辆");
            }
            if(product.getAdvancePrice()<1000){
                throw new Exception("定金不能低于1000");
            }
            if(product.getSellPrice()<10000){
                throw new Exception("销售金额不能低于10000");
            }
            if(product.getYxq()==null){
                throw new Exception("未设置有效期");
            }
            if(product.getAllowArea()==null){
                throw new Exception("未设置销售区域");
            }
            VehicleProduct vehicleProduct =productService.findByStringID(product.getPID());
            vehicleProduct.setpCount(product.getpCount());
            vehicleProduct.setSellPrice(product.getSellPrice());
            vehicleProduct.setAdvancePrice(product.getAdvancePrice());
            vehicleProduct.setYxq(product.getYxq());
            vehicleProduct.setAllowArea(product.getAllowArea());
            productService.update(vehicleProduct);
            return "redirect:/vehicleProductList";
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
    @DeleteMapping("/vehicleProduct/{id}")
//    @RequiresPermissions("rest[account:delete]")
    @ResponseBody
    public ResultMsg delete(@PathVariable String id)
    {
        try {
            if(tuanGouService.findByProductID(productService.findByStringID(id).getPID()).size()>0){
                throw new Exception("当前商品已经开团，不能删除");
            }
            productService.deleteByStringID(id);
            return new ResultMsg(true, "删除成功");
        }
        catch (Exception e) {
            return new ResultMsg(false,"删除失败");
        }
    }

    @RequestMapping("/getCity")
    @ResponseBody
    public String getCity()
    {
        try {
            String cityList = districtService.getCity();
            return cityList;
        }
        catch (Exception e) {
            return null;
        }
    }
    @RequestMapping("/getUpdateCity")
    @ResponseBody
    public String getUpdateCity(String city)
    {
        try {
            String cityList = districtService.getUpdateCity(city);
            return cityList;
        }
        catch (Exception e) {
            return null;
        }
    }
    @RequestMapping("/getViewCity")
    @ResponseBody
    public String getViewCity(String city)
    {
        try {
            String cityList = districtService.getViewCity(city);
            return cityList;
        }
        catch (Exception e) {
            return null;
        }
    }
}