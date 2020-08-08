package cn.fcars.infomgr.service.autofinance.impl;


import cn.fcars.infomgr.common.AutoFinanceQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.autofinance.Order;
import cn.fcars.infomgr.entity.autofinance.OrderOperate;
import cn.fcars.infomgr.entity.autofinance.OrderPic;
import cn.fcars.infomgr.entity.basic.Enumerator;
import cn.fcars.infomgr.entity.basic.Pic;
import cn.fcars.infomgr.mapper.autofinance.OrderMapper;
import cn.fcars.infomgr.mapper.autofinance.OrderOperateMapper;
import cn.fcars.infomgr.mapper.autofinance.OrderPicMapper;
import cn.fcars.infomgr.mapper.basic.EnumeratorMapper;
import cn.fcars.infomgr.mapper.basic.MaxNoMapper;
import cn.fcars.infomgr.mapper.basic.PicMapper;
import cn.fcars.infomgr.service.autofinance.OrderOperateService;
import cn.fcars.infomgr.service.basic.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class OrderOperateServiceImpl implements OrderOperateService {

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
    public OrderOperate findByID(Integer id) {
        return null;
    }

    @Override
    public OrderOperate findByStringID(String id) {
        return null;
    }

    @Override
    public OrderOperate findByName(String name) {
        return null;
    }

    @Override
    public List<OrderOperate> findAll() {
        return null;
    }

    @Override
    public List<OrderOperate> findByQuery(AutoFinanceQuery data) {
        return orderOperateMapper.findByQuery(data);
    }

    @Override
    public PageInfo<OrderOperate> findByPage(AutoFinanceQuery data) {
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
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(OrderOperate data) throws Exception {
        try{
            Integer id=data.getId();
            Date now =new Date();
            Order order =orderMapper.findByStringID(data.getOrder().getOrderID());
            String sfdk =order.getOrderSfdk();
            if(order.getOrderZt().equals("01")) {
                if (sfdk.equals("0")) {
                    order.setOrderZt("11");
                } else {
                    order.setOrderZt("10");
                }
            }
            else if(order.getOrderZt().equals("10")||order.getOrderZt().equals("11"))
            {
                order.setOrderZt("20");
            }
            else if(order.getOrderZt().equals("20"))
            {
                order.setOrderZt("30");
            }
            else if(order.getOrderZt().equals("30"))
            {
                order.setOrderZt("40");
            }
            orderMapper.update(order);
            data.setUpdateTime(now);
            data.setOrder(order);
            data.setOrderZt(order.getOrderZt());
            AutoFinanceQuery autoFinanceQuery=new AutoFinanceQuery();
            autoFinanceQuery.setOrderID(order.getOrderID());
            autoFinanceQuery.setOrderZt(order.getOrderZt());
            if(orderOperateMapper.findByPara(autoFinanceQuery).size()==0) {
                data.setCreateTime(now);
                orderOperateMapper.add(data);
            }
            else
            {
                orderOperateMapper.update(data);
            }
            List<Enumerator> enumeratorList = enumeratorMapper.findByPara("order",order.getOrderZt());
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
                        orderPicQuery.setOrderZt(data.getOrderZt());
                        orderPicQuery.setPicType(picType);
                        List<OrderPic> orderPics = orderPicMapper.findByQuery(orderPicQuery);
                        if (orderPics.size() == 0) {
                            Pic pic = new Pic();
                            pic.setPicName(picName);
                            pic.setPicType(picType);
                            picMapper.add(pic);
                            OrderPic orderPic = new OrderPic();
                            orderPic.setOrder(order);
                            orderPic.setOrderZt(data.getOrderZt());
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
    public void add(OrderOperate data) throws Exception {
        try{
            orderOperateMapper.add(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OrderOperate data) throws Exception {
       try{

           orderOperateMapper.update(data);

       }
       catch (Exception e)
       {
           e.printStackTrace();
           throw new RuntimeException(e.getMessage());
       }
    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(OrderOperate data) throws Exception {
        return 0;
    }

    @Override
    public List<OrderOperate> findByPara(AutoFinanceQuery data) {
        return orderOperateMapper.findByPara(data);
    }
}
