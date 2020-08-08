package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.*;
import cn.fcars.infomgr.mapper.basic.*;
import cn.fcars.infomgr.service.basic.UserAccountService;
import cn.fcars.infomgr.service.basic.UserService;
import cn.fcars.infomgr.util.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    UserAccountMapper userAccountMapper;

    @Override
    public UserAccount findByID(Integer id) {
        return null;
    }

    @Override
    public UserAccount findByStringID(String id) throws Exception {
        return null;
    }

    @Override
    public UserAccount findByName(String name) {
        return null;
    }

    @Override
    public List<UserAccount> findAll() {
        return null;
    }

    @Override
    public List<UserAccount> findByQuery(BaseQuery data) {
        List<UserAccount> accounts = userAccountMapper.findByQuery(data);
        return accounts;
    }

    @Override
    public PageInfo<UserAccount> findByPage(BaseQuery data) {
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
    public void add(UserAccount data) throws Exception {

    }

    @Override
    public void update(UserAccount data) throws Exception {

    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(UserAccount data) throws Exception {
        return 0;
    }
}
