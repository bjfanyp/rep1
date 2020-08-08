package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.Dep;
import cn.fcars.infomgr.entity.basic.MaxNo;
import cn.fcars.infomgr.mapper.basic.DepMapper;
import cn.fcars.infomgr.mapper.basic.MaxNoMapper;
import cn.fcars.infomgr.service.basic.DepService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DepServiceImpl implements DepService {

    @Autowired
    Static aStatic;
    @Autowired
    DepMapper depMapper;
    @Autowired
    MaxNoMapper maxNoMapper;

    @Override
    public Dep findByID(Integer id) {
        return depMapper.findByID(id);
    }

    @Override
    public Dep findByStringID(String id) {
        return null;
    }

    @Override
    public Dep findByName(String name) {
        return depMapper.findByName(name);
    }

    @Override
    public List<Dep> findAll() {
        return depMapper.findAll();
    }

    @Override
    public List<Dep> findByQuery(BaseQuery t1data) {
        return depMapper.findByQuery(t1data);
    }

    @Override
    public PageInfo<Dep> findByPage(BaseQuery depQuery) {
        Integer pageSize =  aStatic.getPageSize();
        Integer pageNum=1;
        if (depQuery.getPageNum()!=null) {
            pageNum=depQuery.getPageNum();
        }
        if (depQuery.getDepType()!=null && depQuery.getDepType().equals("0")) {
            depQuery.setDepType(null);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Dep> depList = depMapper.findByQuery(depQuery);
        PageInfo<Dep> pageInfo = new PageInfo(depList);
        return pageInfo;
    }

    @Override
    public Integer check(String name) {
        return depMapper.check(name);
    }

    @Override
    public boolean checkExist(String id, String name) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Dep dep) throws RuntimeException {
       try {
           if (findByName(dep.getDepName()) != null) {
               throw new Exception("当前部门名已存在，不能保存");
           }
           Date date=new Date();
           if (dep.getAccount().getAccountID() == 0) {
               dep.setAccount(null);
           }
           dep.setIsDelete("0");
           dep.setCreateTime(date);
           dep.setUpdateTime(date);
           depMapper.add(dep);
           MaxNo maxNo = maxNoMapper.findByDepID(dep.getDepID());
           if (maxNo == null) {
               maxNo = new MaxNo();
               maxNo.setMaxNo(1);
               maxNo.setDep(dep);
               maxNo.setCreateTime(date);
               maxNo.setUpdateTime(date);
               maxNoMapper.add(maxNo);
           }
       }
       catch (Exception e)
       {
           throw new RuntimeException(e.getMessage());
       }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dep dep) throws RuntimeException {
        try {
            Dep depBase = findByID(dep.getDepID());
            Date date = new Date();
            depBase.setUpdateTime(date);
            depBase.setDepType(dep.getDepType());
            depBase.setDepQdfwf(dep.getDepQdfwf());
            if (dep.getAccount().getAccountID() == 0) {
                depBase.setAccount(null);
            }
            else {
                depBase.setAccount(dep.getAccount());
            }
            depMapper.update(depBase);
            MaxNo maxNo = maxNoMapper.findByDepID(dep.getDepID());
            if (maxNo == null) {
                maxNo = new MaxNo();
                maxNo.setDep(depBase);
                maxNo.setMaxNo(1);
                maxNo.setCreateTime(date);
                maxNo.setUpdateTime(date);
                maxNoMapper.add(maxNo);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByID(Integer id) throws RuntimeException {
        try {
            Date now = new Date();
            Dep dep = findByID(id);
            dep.setIsDelete("1");
            dep.setUpdateTime(now);
            depMapper.update(dep);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int delete(Dep data) {
        return 0;
    }
}
