package cn.fcars.infomgr.controller.agent;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.*;
import cn.fcars.infomgr.entity.cart.VehicleOrder;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import cn.fcars.infomgr.service.basic.*;
import cn.fcars.infomgr.service.cart.VehicleOrderService;
import cn.fcars.infomgr.service.tuangou.VehicleTuanGouService;
import cn.fcars.infomgr.service.vehicle.VehicleProductService;
import cn.fcars.infomgr.util.HttpAdapter;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/agent")
public class AgentController {
//	Logger log=Logger.getLogger(AgentUserController.class);
	private static final String INDEX = "admin/agent_manager/agent_user";
	private static final String ADD = "admin/agent_manager/agent_user_add";
	private static final String CRADLEINDEX = "admin/agent_manager/agent_user_cradle";
	private static final String REFERRALCODE = "admin/agent_manager/agent_user_referralcode";
	@Autowired
	UserService userService;
    @Autowired
    VehicleProductService productService;
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
	@RequestMapping(value = "user/indexs", method = RequestMethod.GET)
	public ModelAndView indexs(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView(INDEX);
		Integer pageIndex = 1;
		Integer pageSize =10;
		if (null != request.getParameter("pageIndex")) {
			pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		}
		if (null != request.getParameter("pageSize")) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		
		String strWhere="1=1 ";
		String strOrder="u.created_time desc";
		
		String agentName=request.getParameter("agentName");//真实姓名，用户名
		if(agentName!=null && agentName.length()>0){
			strWhere+="and u.agent_user_name like('%"+agentName+"%') or u.agent_real_name like('%"+agentName+"%') ";
			modelAndView.addObject("agentName",agentName);
		}
				
		String regionId=request.getParameter("regionId");//大区
		if(regionId!=null && regionId.length()>0){
			strWhere+="and o.region_id='"+regionId+"' ";
			modelAndView.addObject("regionId",regionId);
		}
				
		String agentStatus=request.getParameter("agentStatus");//状态
		if(agentStatus!=null && agentStatus.length()>0){
			strWhere+="and u.agent_status='"+agentStatus+"' ";
			modelAndView.addObject("agentStatus",agentStatus);
		}
		
		String referralCode=request.getParameter("referralCode");//级别
		if(referralCode!=null && referralCode.length()>0){
			if("1".equals(referralCode)){
				strWhere+="and u.referral_code=''";
				modelAndView.addObject("referralCode",referralCode);				
			}else if("2".equals(referralCode)){
				strWhere+="and u.referral_code>''";
				modelAndView.addObject("referralCode",referralCode);				
			}
		}		
				
		System.out.println(strWhere);
//		List<AgentUser> agentUsers = agentUserService.findByPageS(pageIndex, pageSize, strWhere,strOrder);
//		Integer total = agentUserService.counts(strWhere);
//
//		modelAndView.addAllObjects(HttpAdapter.pages(agentUsers, pageIndex,pageSize, total));
//		modelAndView.addObject("regions",selregion());//获得大区列表
		return modelAndView;
	}

	@RequestMapping(value="user/json/updateUserTrueName",method=RequestMethod.POST)
	public void updateUserTrueName(HttpServletRequest request, HttpServletResponse response){
		try{
			String name=request.getParameter("username");
			String trueName=request.getParameter("trueName");
			User user = userService.findByName(name);
			user.setUserTrueName(trueName);
			userService.update(user);
			HttpAdapter.printlnMessagge(response, true,null);
		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="user/json/regiser",method=RequestMethod.POST)
	public void register(HttpServletRequest request, HttpServletResponse response){
		try{
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String code=request.getParameter("code");
			HttpAdapter.printlnObject(response, userService.register(username, password, code));
		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value="user/json/login",method=RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response){
		try{
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			HttpAdapter.printlnObject(response, userService.login(username, password));
		}catch(Exception e){
			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value="user/json/exist/{username}",method=RequestMethod.GET)
	public void checkUsernameExist(@PathVariable(value="username") String username, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map=userService.checkUsernameExist(username);
		HttpAdapter.printlnObject(response, map);
	}
	
	@RequestMapping(value="user/del/{id}",method=RequestMethod.GET)
	public void del(@PathVariable(value="id") String id, HttpServletRequest request, HttpServletResponse response){
		try{
			Map<String, Object> map=new HashMap<String, Object>();
			boolean flag=false;
			String msg=null;
//			flag=agentUserService.delete(id);
			if (!flag) {
				msg="删除失败";
			}
			map.put("flag", flag);
			map.put("msg", msg);
			HttpAdapter.printlnObject(response,map );
		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value="user/json/fotgotPassword",method=RequestMethod.POST)
	public void fotgotPassword(HttpServletRequest request, HttpServletResponse response){
		try{
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			HttpAdapter.printlnObject(response, userService.fotgotPassword(username, password));
		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="tuanGou/json/loadTuanGouProductList",method=RequestMethod.GET)
	public void loadTuanGouProductList(HttpServletRequest request, HttpServletResponse response){
		try{
			HttpAdapter.printlnMessagge(response,true,tuanGouService.findAll());
		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="tuanGou/json/loadTuanGouProductPageList",method=RequestMethod.POST)
	public void loadTuanGouProductPageList(HttpServletRequest request, HttpServletResponse response){
		try{
			Integer pageIndex = 1;
			Integer pageSize =  aStatic.getPageSize();
			if (null != request.getParameter("pageIndex")) {
				pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			}
			if (null != request.getParameter("pageSize")) {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
			}

			BaseQuery baseQuery =new BaseQuery();
			baseQuery.setPageNum(pageIndex);
			baseQuery.setCheKuanID(request.getParameter("CheKuanID"));
			PageInfo<VehicleTuanGou> tgList = tuanGouService.findByPage(baseQuery);
			Integer total = (int)tgList.getTotal();
			HttpAdapter.printlnMessagge(response, true, HttpAdapter.pages(tgList.getList(),pageIndex,
					pageSize, total));

		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}
	@RequestMapping(value="tuanGou/json/loadTuanGouProduct/{id}",method=RequestMethod.GET)
	public void loadTuanGouProduct(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
		try{
			HttpAdapter.printlnMessagge(response,true,tuanGouService.findByStringID(id));
		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="vehicleProduct/json/createTuanGou",method=RequestMethod.POST)
	public void createTuanGou(HttpServletRequest request, HttpServletResponse response){
		try{
			String pID=request.getParameter("pID");
			String userName=request.getParameter("username");
			User user =userService.findByName(userName);
			if(!tuanGouService.checkUserIDExist(pID,user.getUserID())){
				throw new Exception("当前用户已经发起过此产品的团购，不能再次发起");
			}
			String city=request.getParameter("city");
			if(city==null||city.equals("")){
				throw new Exception("未选择提货城市");
			}
			int count =Integer.parseInt(request.getParameter("count"));
			if(count<6){
				throw new Exception("开团数量不能低于6");
			}
			VehicleProduct product =productService.findByStringID(pID);
			if(count>product.getpKTCount()){
				throw new Exception("开团数量不能超过"+product.getpKTCount());
			}
			Date now =new Date();
			VehicleTuanGou tuanGou = new VehicleTuanGou();
			tuanGou.setTgID(String.valueOf(now.getTime()));
			tuanGou.setTgCount(count);
			tuanGou.setProduct(product);
			District district =districtService.findByStringID(city);
			tuanGou.setCity(district);
			tuanGou.setTgFbDate(now);
			tuanGou.setTgJZDate(product.getYxq());
			tuanGou.setTgZt("1");
			tuanGou.setTgYtCount(0);
			tuanGou.setIsDelete("0");
			tuanGou.setUser(user);
			tuanGouService.add(tuanGou);
			Map<String,Object>map=new HashMap<String, Object>();
			map.put("flag",true );
			map.put("msg","保存成功");
			HttpAdapter.printlnMessagge(response,true,map);
		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="vehicleProduct/json/getProductDetail/{id}",method=RequestMethod.GET)
	public void getProductDetail(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
		try{
			VehicleProduct productDetail = productService.findByStringID(id);
			String[] cityTemp = productDetail.getAllowArea().split(",");
			List<District> cityDis=new ArrayList<District>();
			for(int i=0;i<cityTemp.length;i++) {
				cityDis.add(districtService.findByStringID(cityTemp[i]));
			}
			HttpAdapter.printlnDoubleMessagge(response,true,productDetail,cityDis);

		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}
    @RequestMapping(value="vehicleProduct/json/getCheXing",method=RequestMethod.GET)
    public void getCheXing(HttpServletRequest request, HttpServletResponse response){
        try{
            String pinPaiID = "";
            if (null != request.getParameter("pinPaiID")) {
				pinPaiID = request.getParameter("pinPaiID");
            }
			if(pinPaiID.equals("")){
				HttpAdapter.printlnMessagge(response,true,null);
				return;
			}
            List<VehicleProduct> cheXingList = productService.getCheXing(pinPaiID);
			HttpAdapter.printlnMessagge(response,true,cheXingList);

        }catch(Exception e){

            HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
        }
    }

	@RequestMapping(value="vehicleProduct/json/getProductOLD",method=RequestMethod.GET)
	public void getProductOLD(HttpServletRequest request, HttpServletResponse response){
		try{
			Integer pageIndex = 1;
			Integer pageSize =  aStatic.getPageSize();
			if (null != request.getParameter("pageIndex")) {
				pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			}
			if (null != request.getParameter("pageSize")) {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
			}
			BaseQuery baseQuery =new BaseQuery();
			baseQuery.setPageNum(pageIndex);
			PageInfo<VehicleProduct> productList = productService.findByPage(baseQuery);
			Integer total = (int)productList.getTotal();
			List<VehicleProduct> pinPaiList = productService.getPinPai();
			HttpAdapter.printlnMessagge(response, true, HttpAdapter.pagesNew(productList.getList(),pinPaiList ,pageIndex,
					pageSize, total));

		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="vehicleProduct/json/getProduct",method=RequestMethod.POST)
	public void getProduct(HttpServletRequest request, HttpServletResponse response){
		try{
			Integer pageIndex = 1;
			Integer pageSize =  aStatic.getPageSize();
			if (null != request.getParameter("pageIndex")) {
				pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			}
			if (null != request.getParameter("pageSize")) {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
			}
			BaseQuery baseQuery =new BaseQuery();
			baseQuery.setPageNum(pageIndex);
			baseQuery.setCheKuanID(request.getParameter("cheKuanID"));
			PageInfo<VehicleProduct> productList = productService.findByPage(baseQuery);
			Integer total = (int)productList.getTotal();
			HttpAdapter.printlnMessagge(response, true, HttpAdapter.pages(productList.getList() ,pageIndex,
					pageSize, total));

		}catch(Exception e){

			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}

    @RequestMapping(value="vehicleProduct/json/queryProduct",method=RequestMethod.POST)
    public void queryProduct(HttpServletRequest request, HttpServletResponse response){
        try{
            Integer pageIndex = 1;
            Integer pageSize =  aStatic.getPageSize();
            if (null != request.getParameter("pageIndex")) {
                pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
            }
            if (null != request.getParameter("pageSize")) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
            }
            BaseQuery baseQuery =new BaseQuery();
            String pinPaiID= request.getParameter("pinPaiID");
			String cheXingID= request.getParameter("cheXingID");
            baseQuery.setPinPaiID(pinPaiID);
            baseQuery.setCheXingID(cheXingID);
            baseQuery.setPageNum(pageIndex);
            PageInfo<VehicleProduct> productList = productService.findByPage(baseQuery);
            Integer total = (int)productList.getTotal();
            List<VehicleProduct> pinPaiList = productService.getPinPai();
            HttpAdapter.printlnMessagge(response, true, HttpAdapter.pages(productList.getList(),pageIndex,
                    pageSize, total));

        }catch(Exception e){

            HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
        }
    }
	@RequestMapping(value = "order/json/getMyOrderList", method = RequestMethod.POST)
	public void getMyOrderList(HttpServletRequest request, HttpServletResponse response) {
		try{
			Integer pageIndex = 1;
			Integer pageSize =10;
			if (null != request.getParameter("pageIndex")) {
				pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			}
			if (null != request.getParameter("pageSize")) {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
			}
			String state=request.getParameter("state");
			String userName=request.getParameter("username");
			BaseQuery baseQuery =new BaseQuery();
			baseQuery.setPageNum(pageIndex);
			baseQuery.setUserID(userService.findByName(userName).getUserID());
			baseQuery.setOrderZt(state);
			PageInfo<VehicleOrder> pageInfo = orderService.findByPage(baseQuery);
			HttpAdapter.printlnMessagge(response, true, HttpAdapter.pages(pageInfo.getList(), pageIndex, pageSize, (int)pageInfo.getTotal()));

		}catch (Exception e){
			HttpAdapter.printlnMessagge(response, false, e.getMessage());
		}
	}
    @RequestMapping(value="order/json/createPreOrder",method=RequestMethod.POST)
    public void createPreOrder(HttpServletRequest request, HttpServletResponse response){
        try{
            String id =request.getParameter("tgID");
            String userName =request.getParameter("username");
            int count = Integer.parseInt(request.getParameter("count"));
            VehicleTuanGou vehicleTuanGou = tuanGouService.findByStringID(id);
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
            User user =userService.findByName(userName);
            order.setUser(user);
            orderService.add(order);
            Map<String,Object>map=new HashMap<String, Object>();
            map.put("orderId", order.getId());
            map.put("flag",true );
            map.put("msg","保存成功");
            HttpAdapter.printlnMessagge(response,true,map);
        }catch(Exception e){
            HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
        }
    }
	@RequestMapping(value="order/json/delete/{id}",method=RequestMethod.GET)
	public void deleteOrder(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
		try{
			orderService.deleteByStringID(id);
			HttpAdapter.printlnMessagge(response,true,null);
		}catch(Exception e){
			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}
	@RequestMapping(value="order/json/load/{id}",method=RequestMethod.GET)
	public void loadOrder(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
		try{
			HttpAdapter.printlnMessagge(response,true,orderService.findByStringID(id));
		}catch(Exception e){
			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}
	@RequestMapping(value="setting/json/loadBank/{userName}",method=RequestMethod.GET)
	public void loadBank(@PathVariable String userName, HttpServletRequest request, HttpServletResponse response){
		try{
			BaseQuery data=new BaseQuery();
			data.setUserName(userName);
			List<UserAccount> userAccountList =userAccountService.findByQuery(data);
			HttpAdapter.printlnMessagge(response,true,userAccountList);
		}catch(Exception e){
			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}
	@RequestMapping(value="setting/json/operateBank",method=RequestMethod.POST)
	public void operateBank(HttpServletRequest request, HttpServletResponse response){
		try{
			String id = request.getParameter("id");
			String accountName = request.getParameter("accountName");
			String accountBank = request.getParameter("accountBank");
			String accountNumber = request.getParameter("accountNumber");
			String username = request.getParameter("username");

			Account account=new Account();
			account.setAccountType("2");
			account.setAccountName(accountName);
			account.setAccountBank(accountBank);
			account.setAccountNumber(accountNumber);
			account.setIsDelete("0");
			User user = userService.findByName(username);
			accountService.operaAccountAndUserAccount(id,account,user);

			HttpAdapter.printlnMessagge(response,true,null);
		}catch(Exception e){
			HttpAdapter.printlnMessagge(response, false, e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="json/checkAppVersion/{appId}",method=RequestMethod.GET)
	public void checkAppVersion(@PathVariable String appId, HttpServletRequest request, HttpServletResponse response){
		try{
			Map<String,Object>map=new HashMap<String, Object>();
			boolean flag=false;
			if(appId.length()>0){
				String strWhere="app_id='"+appId+"'";
				BaseQuery data =new BaseQuery();
				data.setAppID(appId);
				List<AppVersion> appVersion=appVersionService.findByQuery(data);
				if(appVersion.isEmpty()){
					map.put("appVersion", null);
				}else{
					map.put("appVersion", appVersion.get(0));
					flag=true;
				}
			}
			String path = request.getContextPath();
			//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            String basePath =request.getScheme()+"://192.169.3.167:"+request.getServerPort()+path+"/";
			map.put("basePath", basePath);

			map.put("flag", flag);
			HttpAdapter.printlnObject(response, map);
		}catch(Exception e){
			HttpAdapter.printlnObject(response, null);
		}
	}

	@RequestMapping(value="json/addFeedBack",method=RequestMethod.POST)
	public void addFeedBack(HttpServletRequest request, HttpServletResponse response){
		try{
			String username = request.getParameter("username");
			String app = request.getParameter("app");
			String content = request.getParameter("content");
			User user =userService.findByName(username);
			FeedBack feedBack =new FeedBack();
			feedBack.setId(String.valueOf(new Date().getTime()));
			feedBack.setFeedbackName(username);
			feedBack.setFeedbackPhone(user.getUserTrueName());
			feedBack.setFeedbackContent(content);
			feedBack.setCreateTime(new Date());
			feedBack.setFeedbackState("0");
			feedbackService.add(feedBack);
			HttpAdapter.printlnMessagge(response,true,null);
		}catch(Exception e){
			HttpAdapter.printlnMessagge(response,false,e.getMessage());
		}
	}
}