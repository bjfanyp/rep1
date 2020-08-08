package cn.fcars.infomgr.controller.webapi;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.*;
import cn.fcars.infomgr.entity.cart.VehicleOrder;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.entity.vehicle.BaseVehicle;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.service.basic.*;
import cn.fcars.infomgr.service.cart.VehicleOrderService;
import cn.fcars.infomgr.service.tuangou.VehicleTuanGouService;
import cn.fcars.infomgr.service.vehicle.VehicleProductService;
import cn.fcars.infomgr.service.vehicle.VehicleService;
import cn.fcars.infomgr.util.HttpAdapter;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 经纪人管理
 * @author zp
 *
 */

@Controller
@RequestMapping("/webapi")
public class WebapiController {
//	Logger log=Logger.getLogger(AgentUserController.class);
	@Autowired
	UserService userService;
    @Autowired
    VehicleProductService productService;
    @Autowired
    VehicleService vehicleService;
	@Autowired
	VehicleTuanGouService tuanGouService;
    @Autowired
    VehicleOrderService orderService;
	@Autowired
	DistrictService districtService;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	AccountService accountService;
	@Autowired
	AppVersionService appVersionService;
	@Autowired
	FeedbackService feedbackService;
    @Autowired
    Static aStatic;

    @RequestMapping(value="category/getCarBrands",method=RequestMethod.GET)
    public void getCarBrands(HttpServletRequest request, HttpServletResponse response){
        try{
            List<BaseVehicle> pinPaiList = vehicleService.getPinPai();
			HttpAdapter.printlnMessagge(response,true,pinPaiList);
        }catch(Exception e){
            HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
        }
    }
	@RequestMapping(value="category/getProductCarBrands",method=RequestMethod.GET)
	public void getProductCarBrands(HttpServletRequest request, HttpServletResponse response){
		try{
			List<BaseVehicle> pinPaiList = vehicleService.getProductPinPai();
			HttpAdapter.printlnMessagge(response,true,pinPaiList);
		}catch(Exception e){
			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}
	@RequestMapping(value="category/getCarSeries/{id}",method = RequestMethod.GET)
	public void getCarSeries(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response){
		try{
			List<BaseVehicle> cheXingList = vehicleService.getCheXing(id);
			HttpAdapter.printlnMessagge(response,true,cheXingList);
		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}
	@RequestMapping(value="category/getProductCarSeries/{id}",method = RequestMethod.GET)
	public void getProductCarSeries(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response){
		try{
			List<BaseVehicle> cheXingList = vehicleService.getProductCheXing(id);
			HttpAdapter.printlnMessagge(response,true,cheXingList);
		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="category/getCarTypes/{id}",method = RequestMethod.GET)
	public void getCarTypes(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response){
		try{
			List<BaseVehicle> cheKuanList = vehicleService.getCheKuan(id);
			HttpAdapter.printlnMessagge(response,true,cheKuanList);
		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="category/getProductCarTypes/{id}",method = RequestMethod.GET)
	public void getProductCarTypes(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response){
		try{
			List<BaseVehicle> cheKuanList = vehicleService.getProductCheKuan(id);
			HttpAdapter.printlnMessagge(response,true,cheKuanList);
		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}
}