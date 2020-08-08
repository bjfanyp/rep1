package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.AppVersion;
import cn.fcars.infomgr.entity.basic.Dep;
import cn.fcars.infomgr.entity.basic.MaxNo;
import cn.fcars.infomgr.mapper.basic.AppVersionMapper;
import cn.fcars.infomgr.mapper.basic.DepMapper;
import cn.fcars.infomgr.mapper.basic.MaxNoMapper;
import cn.fcars.infomgr.service.basic.AppVersionService;
import cn.fcars.infomgr.service.basic.DepService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    Static aStatic;
    @Autowired
    AppVersionMapper appVersionMapper;


    @Override
    public AppVersion findByID(Integer id) {
        return null;
    }

    @Override
    public AppVersion findByStringID(String id) throws Exception {
        return null;
    }

    @Override
    public AppVersion findByName(String name) {
        return null;
    }

    @Override
    public List<AppVersion> findAll() {
        return null;
    }

    @Override
    public List<AppVersion> findByQuery(BaseQuery data) {
        return appVersionMapper.findByQuery(data);
    }

    @Override
    public PageInfo<AppVersion> findByPage(BaseQuery data) {
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
    public void add(AppVersion data) throws Exception {

    }

    @Override
    public void update(AppVersion data) throws Exception {

    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(AppVersion data) throws Exception {
        return 0;
    }
}
