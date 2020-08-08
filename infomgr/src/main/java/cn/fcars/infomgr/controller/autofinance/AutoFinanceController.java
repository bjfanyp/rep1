package cn.fcars.infomgr.controller.autofinance;


import cn.fcars.infomgr.common.*;
import cn.fcars.infomgr.entity.autofinance.Order;
import cn.fcars.infomgr.entity.autofinance.OrderOperate;
import cn.fcars.infomgr.entity.autofinance.OrderPic;
import cn.fcars.infomgr.entity.basic.Account;
import cn.fcars.infomgr.entity.basic.Dep;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.service.autofinance.OrderOperateService;
import cn.fcars.infomgr.service.autofinance.OrderPicService;
import cn.fcars.infomgr.service.autofinance.OrderService;
import cn.fcars.infomgr.service.basic.AccountService;
import cn.fcars.infomgr.service.basic.DepService;
import cn.fcars.infomgr.service.basic.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.validation.Valid;

@Controller
public class AutoFinanceController {

    @Autowired
    UserService userService;
    @Autowired
    DepService depService;
    @Autowired
    AccountService accountService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderOperateService orderOperateService;
    @Autowired
    OrderPicService orderPicService;

    @RequestMapping("/orders")
    @RequiresPermissions("orders")
    public String list(AutoFinanceQuery autoFinanceQuery, Model model, HttpSession session)
    {
        session.setAttribute("currentMenu","orders");
        Object object = session.getAttribute("autoFinanceQuery");
        if(null==autoFinanceQuery.getOrderZt()&&null!=object)
        {
            autoFinanceQuery=(AutoFinanceQuery)object;
        }
        try {
            PageInfo<Order> pageInfo = orderService.findByPage(autoFinanceQuery);
            model.addAttribute("pageInfo", pageInfo);
            Object sessionAttribute= session.getAttribute("depSessionList");
            if(session.getAttribute("depSessionListCnt")==null||Integer.parseInt(session.getAttribute("depSessionListCnt").toString())==0)
            {
                List<Dep> depListTmp = depService.findAll();
                List<Dep> depList=new ArrayList<>();
                for(Dep dep:depListTmp)
                {
                    if(dep.getDepType().equals("10")||dep.getDepType().equals("20"))
                    {
                        depList.add(dep);
                    }
                }
                session.setAttribute("depSessionList",depList);
                session.setAttribute("depSessionListCnt",depList.size());
            }
            if(session.getAttribute("userSessionListCnt")==null||Integer.parseInt(session.getAttribute("userSessionListCnt").toString())==0) {
                session.setAttribute("autoFinanceQuery", autoFinanceQuery);
                List<User> userListTmp = userService.findAll();
                List<User> userList = new ArrayList<>();
                for (User user : userListTmp) {
                    if (user.getDep().getDepType().equals("10") || user.getDep().getDepType().equals("20")) {
                        userList.add(user);
                    }
                }
                session.setAttribute("userSessionList", userList);
                session.setAttribute("userSessionListCnt", userList.size());
            }
            return "autofinance/list";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @GetMapping("/order")
    @RequiresPermissions("rest[order:read]")
    public String toAddPage(HttpSession session, Model model)
    {
        try {
            Account account = accountService.findByName("北京五车网电子商务有限公司");
            session.setAttribute("account", account);
            return "autofinance/add";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }
    @ResponseBody
    @RequestMapping("/orderCaculate")
    public ResultData orderCaculate(@RequestBody Map map, HttpSession session)
    {
        try {
            double loanvalue=Double.parseDouble(map.get("orderLoanvalue").toString()) ;
            double prepay=Double.parseDouble(map.get("orderPrepay").toString());
            String sfdk = map.get("orderSfdk").toString();
            User user =  (User)session.getAttribute("currentUser");
            //确定部门的服务费百分比
            double sepercent=user.getDep().getDepQdfwf()/100;//此处位百分数
            Order order=new Order();
            double pins=0;
            double ywins=0;
            double serpay=0;
            double risk=0;
            double conslan=0;
            double total=0;
            BigDecimal temp;
            if(user.getDep().getDepType().equals("10"))//直营
            {
                //履约险
                temp   =   new BigDecimal(loanvalue*1.1*2.5/100);
                pins =  temp.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                //人身意外保险
                temp   =   new BigDecimal(loanvalue*1.1*0.3/100);
                ywins =  temp.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                //服务费
                temp   =   new BigDecimal(loanvalue*sepercent);
                serpay =  temp.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                //风险保证金
                temp   =   new BigDecimal(loanvalue*1/100);
                risk =  temp.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if(user.getDep().getDepType().equals("20"))//渠道
            {
                //履约险
                temp   =   new BigDecimal(loanvalue*1.12*2.5/100);
                pins =  temp.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                //人身意外保险
                temp   =   new BigDecimal(loanvalue*1.12*0.3/100);
                ywins =  temp.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                //服务费
                temp   =   new BigDecimal(loanvalue*sepercent);
                serpay =  temp.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                //风险保证金
                risk =  0;
            }
            if("0".equals(sfdk))
            {
                conslan=0.0;
            }
            else
            {
                temp   =   new BigDecimal(loanvalue*0.5/100);
                conslan =  temp.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            temp=new BigDecimal(prepay+pins+ywins+serpay+risk+conslan);
            total=temp.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();

            order.setOrderPrepay(prepay);
            order.setOrderPins(pins);
            order.setOrderYwins(ywins);
            order.setOrderSerpay(serpay);
            order.setOrderRisk(risk);
            order.setOrderConsloan(conslan);
            order.setOrderFktotal(total);
            return new ResultData(true,order,"");
        }
        catch (Exception e)
        {
            return new ResultData(false,null,"程序异常");
        }
    }
    @PostMapping("/order")
    @RequiresPermissions("rest[order:post]")
    public String add(@Valid Order order, BindingResult result, HttpSession session,Model model)
    {
        model.addAttribute("order",order);
        List<ObjectError> errorList = result.getAllErrors();
        try {
            if (result.hasErrors()){
                for(ObjectError error : errorList){
                    throw new Exception("输入参数有误");
                }
            }
            orderService.add(order);
            session.removeAttribute("account");
            return "redirect:/orders";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "autofinance/add";
        }
    }
    @GetMapping("/orders/{id}")
    @RequiresPermissions("rest[orders:read]")
    public String toViewPage(@PathVariable("id") String id, HttpSession session, Model model)
    {
        try {
            Account account = accountService.findByName("北京五车网电子商务有限公司");
            model.addAttribute("account", account);
            Order order = orderService.findByStringID(id);
            model.addAttribute("order", order);
            AutoFinanceQuery orderPicQuery=new AutoFinanceQuery();
            orderPicQuery.setOrderID(id);
            List<OrderPic> orderPicList = orderPicService.findByQuery(orderPicQuery);
            model.addAttribute("orderPicList",orderPicList);
            return "autofinance/view";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @GetMapping("/order/{id}")
    @RequiresPermissions("rest[order:read]")
    public String toUpdatePage(@PathVariable("id") String id, HttpSession session, Model model)
    {
        try {
            Order order = orderService.findByStringID(id);
            if(!order.getOrderZt().equals("00"))
            {
                model.addAttribute("exception","当前申请单状态不能进入提交页面");
                return "forward:/error.jsp";
            }
            model.addAttribute("order", order);
            if(!order.getUser().getUserName().equals(((User)session.getAttribute("currentUser")).getUserName()))
            {
                model.addAttribute("exception","请使用创建用户提交申请单");
                return "forward:/error.jsp";
            }
            Account account = accountService.findByName("北京五车网电子商务有限公司");
            session.setAttribute("account", account);

            AutoFinanceQuery orderPicQuery=new AutoFinanceQuery();
            orderPicQuery.setOrderID(id);
            List<OrderPic> orderPicList = orderPicService.findByQuery(orderPicQuery);
            session.setAttribute("orderPicList",orderPicList);
            return "autofinance/update";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @PostMapping("orderCheck")
    @ResponseBody
    public ResultMsg orderCheck(@RequestBody Map map)
    {
        try{
            String rbID=map.get("rbID").toString();
            if(orderService.findByPara(rbID).size()==0)
            {
                return new ResultMsg(true,"");
            }
            else {
                throw new Exception("已有此人保订单");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResultMsg(false,e.getMessage());
        }
    }

    @PutMapping("/order")
    @RequiresPermissions("rest[order:put]")
    public String update(@Valid Order order, BindingResult result,HttpSession session, Model model)
    {
        model.addAttribute("order",order);

        List<ObjectError> errorList = result.getAllErrors();
        try {
            if (result.hasErrors()){
                for(ObjectError error : errorList){
                    throw new Exception("输入参数有误");
                }
            }
            orderService.update(order);
            session.removeAttribute("account");
            session.removeAttribute("orderPicList");
            return "redirect:/orders";
        }
        catch (Exception e)
        {
            String errMsg="";
            if(e.getMessage().length()>10)
            {
                errMsg="程序异常";
            }
            else {
                errMsg=e.getMessage();
            }
            model.addAttribute("errorMsg",errMsg);
            return "autofinance/update";
        }
    }
    @DeleteMapping("/order/{id}")
    @RequiresPermissions("rest[order:delete]")
    @ResponseBody
    public ResultMsg delete(@PathVariable String id)
    {
        try {
           orderService.deleteByStringID(id);
            return new ResultMsg(true,"");
        }
        catch (Exception e)
        {
            String errMsg="";
            if(e.getMessage().length()>15)
            {
                errMsg="程序异常";
            }
            else {
                errMsg=e.getMessage();
            }
            return new ResultMsg(false,errMsg);
        }
    }

    @GetMapping("/confirmAudit/{id}")
    @RequiresPermissions("rest[confirmAudit:read]")
    public String toConfirmAuditPage(@PathVariable("id") String id, HttpSession session, Model model)
    {
        try {
            Order order = orderService.findByStringID(id);
            if(order.getOrderZt().equals("00"))
            {
                model.addAttribute("exception","当前申请单状态不能进入收款确认页面");
                return "forward:/error.jsp";
            }
            model.addAttribute("order", order);
            AutoFinanceQuery orderPicQuery=new AutoFinanceQuery();
            orderPicQuery.setOrderID(id);
            List<OrderPic> orderPicList = orderPicService.findByQuery(orderPicQuery);
            session.setAttribute("orderPicList",orderPicList);
            AutoFinanceQuery orderOperateQuery= orderPicQuery;
            List<OrderOperate> tmp =orderOperateService.findByQuery(orderOperateQuery);
            List<OrderOperate> orderOperateList=new ArrayList<>();
            for(int i=0;i<tmp.size();i++)
            {
                if(tmp.get(i).getOrderZt().equals("01"))
                {
                    model.addAttribute("orderOperate_pre",tmp.get(i));
                }
                if(tmp.get(i).getOrderZt().equals("10")||tmp.get(i).getOrderZt().equals("11"))
                {
                    model.addAttribute("orderOperate",tmp.get(i));
                }
            }
            return "autofinance/confirm-audit";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @PostMapping("/confirmAudit")
    @RequiresPermissions("rest[confirmAudit:post]")
    public String confirmAudit(@Valid OrderOperate orderOperate,  BindingResult result,  HttpSession session, Model model)
    {
        model.addAttribute("orderOperate",orderOperate);
        Order order = orderService.findByStringID(orderOperate.getOrder().getOrderID());
        if(!order.getOrderZt().equals("01"))
        {
            model.addAttribute("exception","当前申请单状态不能执行确认操作");
            return "forward:/error.jsp";
        }
        model.addAttribute("order", order);
        try {
            List<ObjectError> errorList = result.getAllErrors();
            if (result.hasErrors()){
                for(ObjectError error : errorList){
                    throw new Exception("输入参数有误");
                }
            }
            orderOperateService.saveOrUpdate(orderOperate);
            session.removeAttribute("orderPicList");
            return "redirect:/orders";
        }
        catch (Exception e)
        {
            String errMsg="";
            if(e.getMessage().length()>15)
            {
                errMsg="程序异常";
            }
            else {
                errMsg=e.getMessage();
            }
            model.addAttribute("errorMsg",errMsg);
            return "autofinance/confirm-audit";
        }
    }

    @PutMapping("/confirmAudit/{id}")
    @RequiresPermissions("rest[confirmAudit:post]")
    @ResponseBody
    public ResultMsg confirmAuditBack(@PathVariable("id")String id, HttpSession session, Model model)
    {
       try
       {
            Order order = orderService.findByStringID(id);
            if(!order.getOrderZt().equals("01"))
            {
                throw new Exception("当前申请单状态不能执行驳回操作");
            }
            order.setOrderZt("00");
            orderService.updateData(order);
            session.removeAttribute("orderPicList");
            return new ResultMsg(true,"");
        }
        catch (Exception e)
        {
            String errMsg="";
            if(e.getMessage().length()>10)
            {
                errMsg="程序异常";
            }
            else {
                errMsg=e.getMessage();
            }
            return new ResultMsg(false,errMsg);
        }
    }


    @GetMapping("/riskAudit/{id}")
    @RequiresPermissions("rest[riskAudit:read]")
    public String toRiskAuditPage(@PathVariable("id") String id, HttpSession session, Model model)
    {
        try {
            Order order = orderService.findByStringID(id);
            if(order.getOrderZt().equals("00")||order.getOrderZt().equals("01"))
            {
                model.addAttribute("exception","当前申请单状态不能进入风控确认页面");
                return "forward:/error.jsp";
            }
            if (order.getUser().getDep().getDepType().equals("20")) {
                double loan = order.getOrderLoanvalue();
                BigDecimal temp = new BigDecimal(loan * 1.1 * 2.5 / 100);
                double pins = temp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                temp = new BigDecimal(loan * 1.1 * 0.3 / 100);
                double ywins = temp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                temp = new BigDecimal(loan * 1.1 * 0.5 / 100);
                double serpay = temp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                temp = new BigDecimal(loan / 100);
                double risk = temp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                order.setOrderPins(pins);
                order.setOrderYwins(ywins);
                order.setOrderSerpay(serpay);
                order.setOrderRisk(risk);
            }
            model.addAttribute("order", order);
            AutoFinanceQuery orderPicQuery=new AutoFinanceQuery();
            orderPicQuery.setOrderID(id);
            List<OrderPic> orderPicList = orderPicService.findByQuery(orderPicQuery);
            session.setAttribute("orderPicList",orderPicList);
            AutoFinanceQuery orderOperateQuery=orderPicQuery;
            List<OrderOperate> tmp =orderOperateService.findByQuery(orderOperateQuery);
            List<OrderOperate> orderOperateList=new ArrayList<>();
            OrderOperate orderOperate=null;
            for(int i=0;i<tmp.size();i++)
            {
                if(tmp.get(i).getOrderZt().equals("20"))
                {
                    model.addAttribute("orderOperate",tmp.get(i));
                }
                if(tmp.get(i).getOrderZt().equals("10")||tmp.get(i).getOrderZt().equals("11"))
                {
                    orderOperate=tmp.get(i);
                    model.addAttribute("orderOperate_pre",tmp.get(i));
                }
            }
            return "autofinance/risk-audit";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }
    @ResponseBody
    @PutMapping("/riskAudit/{id}")
    @RequiresPermissions("rest[riskAudit:post]")
    public ResultMsg riskAuditBack(@PathVariable("id")String id, HttpSession session, Model model)
    {
        try
        {
            Order order = orderService.findByStringID(id);
            if(!(order.getOrderZt().equals("10")||order.getOrderZt().equals("11")))
            {
                throw new Exception("当前申请单状态不能执行驳回操作");
            }
            order.setOrderZt("01");
            orderService.updateData(order);
            session.removeAttribute("orderPicList");
            return new ResultMsg(true,"");
        }
        catch (Exception e)
        {
            String errMsg="";
            if(e.getMessage().length()>10)
            {
                errMsg="程序异常";
            }
            else {
                errMsg=e.getMessage();
            }
            return new ResultMsg(false,errMsg);
        }
    }

    @PostMapping("/riskAudit")
    @RequiresPermissions("rest[riskAudit:post]")
    public String riskAudit(@Valid OrderOperate orderOperate,  BindingResult result,  HttpSession session, Model model)
    {
        model.addAttribute("orderOperate",orderOperate);
        Order order = orderService.findByStringID(orderOperate.getOrder().getOrderID());
        if(!(order.getOrderZt().equals("10")||order.getOrderZt().equals("11")))
        {
            model.addAttribute("exception","当前申请单状态不能执行确认操作");
            return "forward:/error.jsp";
        }
        model.addAttribute("order", order);
        try {
            List<ObjectError> errorList = result.getAllErrors();
            if (result.hasErrors()){
                for(ObjectError error : errorList){
                    throw new Exception("输入参数有误");
                }
            }
            orderOperateService.saveOrUpdate(orderOperate);
            session.removeAttribute("orderPicList");
            return "redirect:/orders";
        }
        catch (Exception e)
        {
            String errMsg="";
            if(e.getMessage().length()>15)
            {
                errMsg="程序异常";
            }
            else {
                errMsg=e.getMessage();
            }
            model.addAttribute("errorMsg",errMsg);
            return "autofinance/risk-audit";
        }
    }


    @GetMapping("/advanceAudit/{id}")
    @RequiresPermissions("rest[advanceAudit:read]")
    public String toAdvanceAuditAuditPage(@PathVariable("id") String id, HttpSession session, Model model)
    {
        try {
            Order order = orderService.findByStringID(id);
            if(order.getOrderZt().equals("00")||order.getOrderZt().equals("01")||order.getOrderZt().equals("10")||order.getOrderZt().equals("11"))
            {
                model.addAttribute("exception","当前状态不能进入放款页面");
                return "forward:/error.jsp";
            }
            model.addAttribute("order", order);
            AutoFinanceQuery orderPicQuery=new AutoFinanceQuery();
            orderPicQuery.setOrderID(id);
            List<OrderPic> orderPicList = orderPicService.findByQuery(orderPicQuery);
            session.setAttribute("orderPicList",orderPicList);
            List<OrderOperate> tmp =orderOperateService.findByQuery(orderPicQuery);
            for(int i=0;i<tmp.size();i++)
            {
                if(tmp.get(i).getOrderZt().equals("30"))
                {
                    model.addAttribute("orderOperate", tmp.get(i));
                    break;
                }
            }
            Account account =accountService.findByID(110);
            session.setAttribute("advanceAuditSessionaccount", account);
            return "autofinance/advance-audit";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }
    @ResponseBody
    @PutMapping("/advanceAudit/{id}")
    @RequiresPermissions("rest[advanceAudit:post]")
    public ResultMsg advanceAuditBack(@PathVariable("id")String id, HttpSession session, Model model)
    {
        try
        {
            Order order = orderService.findByStringID(id);
            if(!(order.getOrderZt().equals("20")))
            {
                throw new Exception("当前申请单状态不能执行驳回操作");
            }
            if (order.getOrderSfdk().equals("0")) {
                order.setOrderZt("11");
            } else {
                order.setOrderZt("10");
            }
            orderService.updateData(order);
            session.removeAttribute("orderPicList");
            session.removeAttribute("advanceAuditSessionaccount");
            return new ResultMsg(true,"");
        }
        catch (Exception e)
        {
            String errMsg="";
            if(e.getMessage().length()>15)
            {
                errMsg="程序异常";
            }
            else {
                errMsg=e.getMessage();
            }
            return new ResultMsg(false,errMsg);
        }
    }

    @PostMapping("/advanceAudit")
    @RequiresPermissions("rest[advanceAudit:post]")
    public String advanceAudit(@Valid OrderOperate orderOperate,  BindingResult result,  HttpSession session, Model model)
    {
        Order order = orderService.findByStringID(orderOperate.getOrder().getOrderID());
        if(!order.getOrderZt().equals("20"))
        {
            model.addAttribute("exception","当前申请单状态不能执行确认操作");
            return "forward:/error.jsp";
        }
        model.addAttribute("order", order);
        model.addAttribute("orderOperate",orderOperate);
        try {
            List<ObjectError> errorList = result.getAllErrors();
            if (result.hasErrors()){
                for(ObjectError error : errorList){
                    throw new Exception("输入参数有误");
                }
            }
            orderOperateService.saveOrUpdate(orderOperate);
            session.removeAttribute("orderPicList");
            session.removeAttribute("advanceAuditSessionaccount");
            return "redirect:/orders";
        }
        catch (Exception e)
        {
            String errMsg="";
            if(e.getMessage().length()>15)
            {
                errMsg="程序异常";
            }
            else {
                errMsg=e.getMessage();
            }
            model.addAttribute("errorMsg",errMsg);
            return "autofinance/advance-audit";
        }
    }

    @GetMapping("/grantLoan/{id}")
    @RequiresPermissions("rest[grantLoan:read]")
    public String tograntLoanPage(@PathVariable("id") String id, HttpSession session, Model model)
    {
        try {
            Order order = orderService.findByStringID(id);
            if(order.getOrderZt().equals("00")||order.getOrderZt().equals("01")||order.getOrderZt().equals("10")||order.getOrderZt().equals("11")||order.getOrderZt().equals("20"))
            {
                model.addAttribute("exception","当前状态不能进入放款页面");
                return "forward:/error.jsp";
            }
            model.addAttribute("order", order);
            AutoFinanceQuery orderPicQuery=new AutoFinanceQuery();
            orderPicQuery.setOrderID(id);
            List<OrderPic> orderPicList = orderPicService.findByQuery(orderPicQuery);
            session.setAttribute("orderPicList",orderPicList);
            Account account =accountService.findByID(110);
            session.setAttribute("grantLoanaccount", account);
            List<OrderOperate> tmp =orderOperateService.findByQuery(orderPicQuery);
            List<OrderOperate> orderOperateList=new ArrayList<>();
            OrderOperate orderOperate=null;
            for(int i=0;i<tmp.size();i++)
            {
                if(tmp.get(i).getOrderZt().equals("40"))
                {
                    model.addAttribute("orderOperate", orderOperate);
                }
                if(tmp.get(i).getOrderZt().equals("30"))
                {
                    model.addAttribute("orderOperate_pre",tmp.get(i));
                }
            }
            return "autofinance/grantLoan";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }
    @ResponseBody
    @PutMapping("/grantLoan/{id}")
    @RequiresPermissions("rest[grantLoan:post]")
    public ResultMsg grantLoanBack(@PathVariable("id")String id, HttpSession session, Model model)
    {
        try
        {
            Order order = orderService.findByStringID(id);
            if(!(order.getOrderZt().equals("30")))
            {
                throw new Exception("当前申请单状态不能执行驳回操作");
            }
            order.setOrderZt("20");
            orderService.updateData(order);
            session.removeAttribute("grantLoanaccount");
            session.removeAttribute("orderPicList");
            return new ResultMsg(true,"");
        }
        catch (Exception e)
        {
            String errMsg="";
            if(e.getMessage().length()>15)
            {
                errMsg="程序异常";
            }
            else {
                errMsg=e.getMessage();
            }
            return new ResultMsg(false,errMsg);
        }
    }

    @PostMapping("/grantLoan")
    @RequiresPermissions("rest[grantLoan:post]")
    public String grantLoan(@Valid OrderOperate orderOperate,  BindingResult result,  HttpSession session, Model model)
    {

        Order order = orderService.findByStringID(orderOperate.getOrder().getOrderID());
        if(!order.getOrderZt().equals("30"))
        {
            model.addAttribute("exception","当前申请单状态不能执行确认操作");
            return "forward:/error.jsp";
        }
        model.addAttribute("order", order);
        model.addAttribute("orderOperate",orderOperate);
        try {
            List<ObjectError> errorList = result.getAllErrors();
            if (result.hasErrors()){
                for(ObjectError error : errorList){
                    throw new Exception("输入参数有误");
                }
            }
            orderOperateService.saveOrUpdate(orderOperate);
            session.removeAttribute("grantLoanaccount");
            session.removeAttribute("orderPicList");
            return "redirect:/orders";
        }
        catch (Exception e)
        {
            String errMsg="";
            if(e.getMessage().length()>15)
            {
                errMsg="程序异常";
            }
            else {
                errMsg=e.getMessage();
            }
            model.addAttribute("errorMsg",errMsg);
            return "autofinance/grantLoan";
        }
    }

    @GetMapping("/processAudit/{id}")
    public String toProcessPage(@PathVariable("id") String id, Model model)
    {
        try {
            TimeProcess timeProcess = orderService.findProcess(id);
            model.addAttribute("timeProcess",timeProcess);
            return "autofinance/process-audit";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }
}
