package cn.fcars.infomgr.service.autofinance.impl;

import cn.fcars.infomgr.common.*;
import cn.fcars.infomgr.entity.autofinance.Order;
import cn.fcars.infomgr.entity.autofinance.OrderOperate;
import cn.fcars.infomgr.entity.autofinance.OrderPic;
import cn.fcars.infomgr.entity.basic.*;
import cn.fcars.infomgr.mapper.autofinance.OrderMapper;
import cn.fcars.infomgr.mapper.autofinance.OrderOperateMapper;
import cn.fcars.infomgr.mapper.autofinance.OrderPicMapper;
import cn.fcars.infomgr.mapper.basic.EnumeratorMapper;
import cn.fcars.infomgr.mapper.basic.MaxNoMapper;
import cn.fcars.infomgr.mapper.basic.PicMapper;
import cn.fcars.infomgr.service.autofinance.OrderService;
import cn.fcars.infomgr.service.basic.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.HasPermissionTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    Static aStatic;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Autowired
    MaxNoMapper maxNoMapper;
    @Autowired
    OrderOperateMapper orderOperateMapper;
    @Autowired
    OrderPicMapper orderPicMapper;
    @Autowired
    EnumeratorMapper enumeratorMapper;
    @Autowired
    PicMapper picMapper;

    @Override
    public Order findByStringID(String id) {
        return orderMapper.findByStringID(id);
    }

    @Override
    public PageInfo<Order> findByPage(AutoFinanceQuery data) {
        try {
            Integer pageNum = 1;
            if (data.getPageNum() != null) {
                pageNum=data.getPageNum();
            }
            if ("0".equals(data.getOrderZt())) {
                data.setOrderZt(null);
            }
            if (data.getDepID() != null) {
                if (data.getDepID() == 0) {
                    data.setDepID(null);
                }
            }
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(true);
            User user = (User) session.getAttribute("currentUser");
            if (user.getDep().getDepType().equals("10")||user.getDep().getDepType().equals("20")) {
               data.setUserID(user.getUserID());
            } else {
                if (data.getUserID()!=null&&0==data.getUserID()) {
                    data.setUserID(null);
                }
            }
            Integer pageSize = aStatic.getPageSize();
            PageHelper.startPage(pageNum, pageSize);
            List<Order> orderList = findByQuery(data);
            PageInfo<Order> pageInfo = new PageInfo(orderList);
            return pageInfo;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer check(String rbID) {
        return null;
    }

    @Override
    public boolean checkExist(String id, String name) {
        return false;
    }

    @Override
    public List<Order> findAll() {
        return orderMapper.findAll();
    }

    @Override
    public List<Order> findByQuery(AutoFinanceQuery data) {
        return orderMapper.findByQuery(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Order order) throws RuntimeException {
        try {
            if (findByPara(order.getRbID()).size()>0) {
                throw new RuntimeException("已有此人保订单");
            }
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(true);
            User user = (User) session.getAttribute("currentUser");
            String orderID=createOrderID(user.getDep());
            if(orderID==null) {
                throw new RuntimeException("程序异常");
            }
            order.setOrderID(orderID);
            order.setOrderZt("00");
            order.setIsDelete("0");
            order.setUser(user);
            orderMapper.add(order);

            AutoFinanceQuery orderOperateQuery=new AutoFinanceQuery();
            orderOperateQuery.setOrderID(orderID);
            orderOperateQuery.setOrderZt(order.getOrderZt());

            List<OrderOperate> orderOperateList = orderOperateMapper.findByQuery(orderOperateQuery);
            if(orderOperateList.size()==0)
            {
                OrderOperate orderOperate=new OrderOperate();
                orderOperate.setOrder(order);
                orderOperate.setOrderZt(order.getOrderZt());
                Date date=new Date();
                orderOperate.setCreateTime(date);
                orderOperate.setUpdateTime(date);
                orderOperateMapper.add(orderOperate);
            }

            List<Enumerator> enumeratorList = enumeratorMapper.findByPara("order",order.getOrderZt());
            String picTypeData=enumeratorList.get(0).getDmsx();
            if(!StringUtils.isEmpty(picTypeData))
            {
                String[] picTypes=picTypeData.split(",");
                for(int i=0;i<picTypes.length;i++)
                {
                    List<Enumerator> enumPicList = enumeratorMapper.findByPara("pic",picTypes[i]);
                    String picType=enumPicList.get(0).getDmz();
                    String picName=enumPicList.get(0).getDmsx();
                    Pic pic=new Pic();
                    pic.setPicName(picName);
                    pic.setPicType(picType);
                    picMapper.add(pic);
                    AutoFinanceQuery orderPicQuery=new AutoFinanceQuery();
                    orderPicQuery.setOrderID(order.getOrderID());
                    orderPicQuery.setOrderZt(order.getOrderZt());
                    orderPicQuery.setPicType(picType);
                    List<OrderPic> orderPics = orderPicMapper.findByQuery(orderPicQuery);
                    if(orderPics.size()==0)
                    {
                        OrderPic orderPic=new OrderPic();
                        orderPic.setOrder(order);
                        orderPic.setOrderZt(order.getOrderZt());
                        orderPic.setPic(pic);
                        orderPicMapper.add(orderPic);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Order order) throws RuntimeException {
        try {
            Order orderData = findByStringID(order.getOrderID());
            if(!orderData.getOrderZt().equals("01"))
            {
                throw new RuntimeException("当前状态不能提交");
            }
            orderData.setOrderCustom(order.getOrderCustom());
            orderData.setOrderCustomID(order.getOrderCustomID());
            orderData.setOrderPrepay(order.getOrderPrepay());
            orderData.setOrderLoanvalue(order.getOrderLoanvalue());
            orderData.setOrderSfdk(order.getOrderSfdk());
            orderData.setOrderPins(order.getOrderPins());
            orderData.setOrderYwins(order.getOrderYwins());
            orderData.setOrderSerpay(order.getOrderSerpay());
            orderData.setOrderRisk(order.getOrderRisk());
            orderData.setOrderConsloan(order.getOrderConsloan());
            orderData.setOrderFktotal(order.getOrderFktotal());
            orderMapper.update(orderData);

            AutoFinanceQuery autoFinanceQuery=new AutoFinanceQuery();
            autoFinanceQuery.setOrderID(order.getOrderID());
            List<OrderOperate> dataList=orderOperateMapper.findByPara(autoFinanceQuery);
            if(dataList.size()==0) {
               OrderOperate orderOperate=new OrderOperate();
                orderOperate.setOrderZt(orderData.getOrderZt());
                orderOperate.setOrder(orderData);
                orderOperate.setCreateTime(new Date());
                orderOperate.setUpdateTime(new Date());
                orderOperate.setCollect(0);
                orderOperate.setLoan(orderData.getOrderFktotal());
                orderOperateMapper.add(orderOperate);
            }
            else
            {
                dataList.get(0).setUpdateTime(new Date());
                dataList.get(0).setOrder(orderData);
                dataList.get(0).setOrderZt(orderData.getOrderZt());
                dataList.get(0).setCollect(0);
                dataList.get(0).setLoan(orderData.getOrderFktotal());
                orderOperateMapper.update(dataList.get(0));
            }
            List<Enumerator> enumeratorList = enumeratorMapper.findByPara("order",orderData.getOrderZt());
            if(enumeratorList.size()>0) {
                String picTypeData = enumeratorList.get(0).getDmsx();
                if (!StringUtils.isEmpty(picTypeData)) {
                    String[] picTypes = picTypeData.split(",");
                    for (int i = 0; i < picTypes.length; i++) {
                        List<Enumerator> enumPicList = enumeratorMapper.findByPara("pic", picTypes[i]);
                        String picType = enumPicList.get(0).getDmz();
                        String picName = enumPicList.get(0).getDmsx();
                        AutoFinanceQuery orderPicQuery = new AutoFinanceQuery();
                        orderPicQuery.setOrderID(order.getOrderID());
                        orderPicQuery.setOrderZt(orderData.getOrderZt());
                        orderPicQuery.setPicType(picType);
                        List<OrderPic> orderPics = orderPicMapper.findByQuery(orderPicQuery);
                        if (orderPics.size() == 0) {
                            Pic pic = new Pic();
                            pic.setPicName(picName);
                            pic.setPicType(picType);
                            picMapper.add(pic);
                            OrderPic orderPic = new OrderPic();
                            orderPic.setOrder(order);
                            orderPic.setOrderZt(orderData.getOrderZt());
                            orderPic.setPic(pic);
                            orderPicMapper.add(orderPic);
                        }
                    }
                }
            }
        }
      catch (Exception e)
      {
          e.printStackTrace();
          throw new RuntimeException(e.getMessage());
      }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateData(Order order) throws RuntimeException {
        try {
            orderMapper.update(order);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public TimeProcess findProcess(String id) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeProcess timeProcess=new TimeProcess();
        Order order =findByStringID(id);
        if(order!=null)
        {
            Integer orderZt=Integer.parseInt(order.getOrderZt());
            AutoFinanceQuery orderOperateQuery=new AutoFinanceQuery();
            orderOperateQuery.setOrderID(id);
            List<OrderOperate> orderOperateList = orderOperateMapper.findByPara(orderOperateQuery);
            if(orderOperateList.size()>0)
            {
                for(int i=0;i<orderOperateList.size();i++)
                {
                    String tmp =orderOperateList.get(i).getOrderZt();
                    if(Integer.parseInt(tmp)<=orderZt)
                    {
                        if(tmp.equals("01"))
                        {
                            timeProcess.setTjTime(format.format(orderOperateList.get(i).getUpdateTime()));
                        }
                        if(tmp.equals("10"))
                        {
                            timeProcess.setQrTime(format.format(orderOperateList.get(i).getUpdateTime()));
                        }
                        if(tmp.equals("11"))
                        {
                            timeProcess.setQrTime(format.format(orderOperateList.get(i).getUpdateTime()));
                        }
                        if(tmp.equals("20"))
                        {
                            timeProcess.setCkTime(format.format(orderOperateList.get(i).getUpdateTime()));
                        }
                        if(tmp.equals("30"))
                        {
                            timeProcess.setFsTime(format.format(orderOperateList.get(i).getUpdateTime()));
                        }
                        if(tmp.equals("40"))
                        {
                            timeProcess.setFkTime(format.format(orderOperateList.get(i).getUpdateTime()));
                        }
                    }
                }
            }
        }
        return timeProcess;
    }
    @Override
    public List<Order> findByPara(String rbID) {
        return orderMapper.findByPara(rbID);
    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByStringID(String id)  throws RuntimeException{
        try {
            Order order = orderMapper.findByStringID(id);
            if(order.getOrderZt().equals("00"))
            {
                order.setIsDelete("1");
                orderMapper.update(order);
            }
            else {
                throw new Exception("当前状态不能删除");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int delete(Order data) throws Exception {
        return 0;
    }

    @Override
    public Order findByID(Integer id) {
        return null;
    }

    @Override
    public Order findByName(String name) {
        return null;
    }
    public String createOrderID(Dep dep)
    {
        try {
            Integer depID = dep.getDepID();
            String rtnStr="";
            SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
            String dateStr=format.format(new Date());
            Integer num =getMaxNo(dep);
            if(num!=0)
            {
                String numStr=String.format("%03d", num);
                rtnStr=depID+dateStr+numStr;
                return rtnStr;
            }
            else
            {
                return "";
            }
        }
        catch (Exception e)
        {
            return "";
        }
    }
    public int getMaxNo(Dep dep)
    {
        try{
            MaxNo maxNo = maxNoMapper.findByDepID(dep.getDepID());
            Date dateNow=new Date();
            int res=0;
            if(maxNo!=null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (format.format(dateNow).equals(format.format(maxNo.getUpdateTime()))) {
                    res=maxNo.getMaxNo();
                }
                else
                {
                    res=1;
                }
                maxNo.setMaxNo(res + 1);
                maxNo.setUpdateTime(dateNow);
                maxNoMapper.update(maxNo);
                return res;
            }
            else
            {
                return 0;
            }
        }
        catch(Exception e) {
            return 0;
        }
    }
}
