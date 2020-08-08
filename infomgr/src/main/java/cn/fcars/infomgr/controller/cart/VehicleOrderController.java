package cn.fcars.infomgr.controller.cart;


import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.ResultMsg;
import cn.fcars.infomgr.entity.basic.District;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.entity.cart.VehicleOrder;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.service.basic.DistrictService;
import cn.fcars.infomgr.service.cart.VehicleOrderService;
import cn.fcars.infomgr.service.tuangou.VehicleTuanGouService;
import cn.fcars.infomgr.service.vehicle.VehicleProductService;
import com.github.pagehelper.PageInfo;
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

/**
 * Created by fanyp on 2018/11/25.
 */
@Controller
public class VehicleOrderController {
    @Autowired
    VehicleOrderService orderService;
    @Autowired
    VehicleTuanGouService tuanGouService;
    @Autowired
    VehicleProductService productService;
    @Autowired
    DistrictService districtService;

    @RequestMapping("/vehicleOrderList")
//    @RequiresPermissions("getBaseVehicleList")
    public String vehicleOrderList(BaseQuery baseQuery, Model model, HttpSession session)
    {
        try {
            Object object = session.getAttribute("VehicleOrderQuery");
            if(null!=object&&null==baseQuery.getPinPaiID()) {
                baseQuery=(BaseQuery)object;
            }
//            baseQuery.setOrderZt("1");
            PageInfo<VehicleOrder> pageInfo = orderService.findByPage(baseQuery);
            for(int i=0;i<pageInfo.getList().size();i++){
                if(pageInfo.getList().get(i).getZt().equals("1")){
                    pageInfo.getList().get(i).setZtDes("未付款");
                }
                if(pageInfo.getList().get(i).getZt().equals("2")){
                    pageInfo.getList().get(i).setZtDes("已付定金");
                }
                if(pageInfo.getList().get(i).getZt().equals("3")){
                    pageInfo.getList().get(i).setZtDes("定金已退");
                }
                if(pageInfo.getList().get(i).getZt().equals("4")){
                    pageInfo.getList().get(i).setZtDes("待付全款");
                }
                if(pageInfo.getList().get(i).getZt().equals("5")){
                    pageInfo.getList().get(i).setZtDes("付款待确认");
                }
                if(pageInfo.getList().get(i).getZt().equals("6")){
                    pageInfo.getList().get(i).setZtDes("全款已退");
                }
                if(pageInfo.getList().get(i).getZt().equals("7")){
                    pageInfo.getList().get(i).setZtDes("待收货");
                }
                if(pageInfo.getList().get(i).getZt().equals("8")){
                    pageInfo.getList().get(i).setZtDes("已完结");
                }
            }
            model.addAttribute("pageInfo", pageInfo);

            List<VehicleProduct> pinPaiList=productService.getPinPai();
            model.addAttribute("pinPai",pinPaiList);
            if(baseQuery.getPinPaiID()!=null&&!baseQuery.getPinPaiID().equals("")){
                List<VehicleProduct> cheXingList=productService.getCheXing(baseQuery.getPinPaiID());
                model.addAttribute("cheXing",cheXingList);
            }
            session.setAttribute("VehicleOrderQuery",baseQuery);
            return "cart/vehicleOrderList";
        }
        catch (Exception e){
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }
    @RequestMapping("/vehicleOrderAdd/{id}")
    public String toAddPage(@PathVariable String id, Model model)
    {
        try {
            VehicleTuanGou tuanGou = tuanGouService.findByStringID(id);
            model.addAttribute("tuanGou", tuanGou);
            return "cart/vehicleOrderAdd";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @RequestMapping("/vehicleOrderAdvance/{id}")
    public String vehicleOrderAdvance(@PathVariable String id, Model model)
    {
        try {
            VehicleOrder order =orderService.findByStringID(id);
            order.setZt("2");
            orderService.update(order);
            VehicleTuanGou tuanGou =order.getTuanGou();
            tuanGou.setTgYtCount(tuanGou.getTgYtCount()+order.getCount());
            tuanGouService.update(tuanGou);
            if(tuanGou.getTgYtCount()==tuanGou.getTgCount()){
                tuanGou.setTgZt("3");
                tuanGouService.update(tuanGou);
                List<VehicleOrder> orderList = orderService.findByTuanGouID(order.getTuanGou().getTgID());
                for(int j=0;j<orderList.size();j++){
                    VehicleOrder vehicleOrder = orderList.get(j);
                    vehicleOrder.setZt("4");
                    orderService.update(vehicleOrder);
                }
            }
            return "redirect:/vehicleOrderList";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @RequestMapping("/vehicleOrderPrice/{id}")
    public String vehicleOrderPrice(@PathVariable String id, Model model)
    {
        try {
            VehicleOrder order =orderService.findByStringID(id);
            order.setZt("5");
            orderService.update(order);
            List<VehicleOrder> orderList = orderService.findByTuanGouID(order.getTuanGou().getTgID());
            for(int j=0;j<orderList.size();j++){
                if(!orderList.get(j).getZt().equals("5"))
                {
                    break;
                }
                if(j==orderList.size()-1){
                  VehicleTuanGou tuanGou = order.getTuanGou();
                  tuanGou.setTgZt("3");
                  tuanGouService.update(tuanGou);
                }
            }
            return "redirect:/vehicleOrderList";
        }
        catch (Exception e) {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @RequestMapping("/vehicleOrderAllBack/{id}")
    public String vehicleOrderAllBack(@PathVariable String id, Model model)
    {
        try {
            VehicleOrder order =orderService.findByStringID(id);
            order.setZt("6");
            orderService.update(order);
            return "redirect:/vehicleOrderList";
        }
        catch (Exception e) {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @RequestMapping("/vehicleOrderConfirm/{id}")
    public String vehicleOrderConfirm(@PathVariable String id, Model model)
    {
        try {
            VehicleOrder order =orderService.findByStringID(id);
            order.setZt("7");
            orderService.update(order);
            return "redirect:/vehicleOrderList";
        }
        catch (Exception e) {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @RequestMapping("/vehicleOrderEnd/{id}")
    public String vehicleOrderEnd(@PathVariable String id, Model model)
    {
        try {
            VehicleOrder order =orderService.findByStringID(id);
            order.setZt("8");
            orderService.update(order);
            return "redirect:/vehicleOrderList";
        }
        catch (Exception e) {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PostMapping("/vehicleOrder")
//    @RequiresPermissions("rest[dep:post]")
    public String add(@Valid VehicleTuanGou tg,int count,BindingResult result, HttpSession session,Model model)
    {
        try {
            VehicleTuanGou vehicleTuanGou = tuanGouService.findByStringID(tg.getTgID());
            if(count<1){
                throw new Exception("数量不能低于1");
            }
            if(count>vehicleTuanGou.getTgCount()){
                throw new Exception("数量不能超过"+vehicleTuanGou.getTgCount());
            }
            Date now =new Date();
            VehicleOrder order = new VehicleOrder();
            order.setId(String.valueOf(now.getTime()));
            order.setCount(count);
            order.setTotalAdvance(vehicleTuanGou.getProduct().getAdvancePrice()*count);
            order.setTotalPrice(vehicleTuanGou.getProduct().getSellPrice()*count);
            order.setZt("1");
            order.setCreateTime(now);
            order.setUpdateTime(now);
            order.setTuanGou(vehicleTuanGou);
            order.setIsDelete("0");
            order.setUser((User)session.getAttribute("currentUser"));
            orderService.add(order);
            return "redirect:/vehicleOrderList";
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

    @RequestMapping("/vehicleOrderUpdate/{id}")
    public String toUpdatePage(@PathVariable String id, Model model)
    {
        try {
            VehicleOrder order = orderService.findByStringID(id);
            model.addAttribute("order",order);
            return "cart/vehicleOrderUpdate";
        }
        catch (Exception e) {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PutMapping("/vehicleOrder")
//    @RequiresPermissions("rest[dep:put]")
    public String update(@Valid VehicleOrder order,BindingResult result,HttpSession session ,Model model)
    {
        try {
            if(order.getCount()<1){
                throw new Exception("数量不能少于1");
            }
            VehicleOrder vehicleOrder = orderService.findByStringID(order.getId());
            if(order.getCount()>vehicleOrder.getTuanGou().getTgCount()){
                throw new Exception("数量不能多于"+vehicleOrder.getTuanGou().getTgCount());
            }
            Date now =new Date();
            vehicleOrder.setCount(order.getCount());
            vehicleOrder.setTotalAdvance(vehicleOrder.getTuanGou().getProduct().getAdvancePrice()*vehicleOrder.getCount());
            vehicleOrder.setTotalPrice(vehicleOrder.getTuanGou().getProduct().getSellPrice()*vehicleOrder.getCount());
            vehicleOrder.setZt("1");
            vehicleOrder.setUpdateTime(now);
            vehicleOrder.setIsDelete("0");
            orderService.update(vehicleOrder);
            return "redirect:/vehicleOrderList";
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
    @DeleteMapping("/vehicleOrder/{id}")
    @ResponseBody
    public ResultMsg delete(@PathVariable String id)
    {
        try {
            orderService.deleteByStringID(id);
            return new ResultMsg(true, "删除成功");
        }
        catch (Exception e) {
            return new ResultMsg(false,"删除失败");
        }
    }

}