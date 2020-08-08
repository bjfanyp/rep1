package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.Account;
import cn.fcars.infomgr.entity.basic.Store;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.entity.basic.UserAccount;
import cn.fcars.infomgr.mapper.basic.AccountMapper;
import cn.fcars.infomgr.mapper.basic.StoreMapper;
import cn.fcars.infomgr.mapper.basic.UserAccountMapper;
import cn.fcars.infomgr.service.basic.AccountService;
import cn.fcars.infomgr.service.basic.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreMapper storeMapper;

    @Override
    public Store findByID(Integer id) {
        return null;
    }

    @Override
    public Store findByStringID(String id) throws Exception {
        return storeMapper.findByStringID(id);
    }

    @Override
    public Store findByName(String name) {
        return null;
    }

    @Override
    public List<Store> findAll() {
        return storeMapper.findAll();
    }

    @Override
    public List<Store> findByQuery(BaseQuery data) {
        return null;
    }

    @Override
    public PageInfo<Store> findByPage(BaseQuery data) {
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
    public void add(Store data) throws Exception {

    }

    @Override
    public void update(Store data) throws Exception {

    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(Store data) throws Exception {
        return 0;
    }
}
