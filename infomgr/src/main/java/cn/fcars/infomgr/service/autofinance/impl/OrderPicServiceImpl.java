package cn.fcars.infomgr.service.autofinance.impl;

import cn.fcars.infomgr.common.AutoFinanceQuery;
import cn.fcars.infomgr.common.Static;
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
import cn.fcars.infomgr.service.autofinance.OrderPicService;
import cn.fcars.infomgr.service.autofinance.OrderService;
import cn.fcars.infomgr.service.basic.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderPicServiceImpl implements OrderPicService {

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
    public OrderPic findByID(Integer id) {
        return null;
    }

    @Override
    public OrderPic findByStringID(String id) {
        return null;
    }

    @Override
    public OrderPic findByName(String name) {
        return null;
    }

    @Override
    public List<OrderPic> findAll() {
        return null;
    }

    @Override
    public List<OrderPic> findByQuery(AutoFinanceQuery data) {
        return orderPicMapper.findByQuery(data);
    }

    @Override
    public PageInfo<OrderPic> findByPage(AutoFinanceQuery data) {
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
    public void add(OrderPic data) throws Exception {

    }

    @Override
    public void update(OrderPic data) throws Exception {

    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(OrderPic data) throws Exception {
        return 0;
    }
}
