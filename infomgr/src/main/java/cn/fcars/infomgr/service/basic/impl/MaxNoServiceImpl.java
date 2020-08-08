package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.entity.basic.MaxNo;
import cn.fcars.infomgr.mapper.basic.MaxNoMapper;
import cn.fcars.infomgr.service.basic.MaxNoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaxNoServiceImpl implements MaxNoService {

    @Autowired
    MaxNoMapper maxNoMapper;

    @Override
    public MaxNo findByID(Integer id) {
        return maxNoMapper.findByID(id);
    }

    @Override
    public MaxNo findByStringID(String id) {
        return null;
    }

    @Override
    public MaxNo findByName(String name) {
        return null;
    }

    @Override
    public List<MaxNo> findAll() {
        return null;
    }

    @Override
    public List<MaxNo> findByQuery(String query) {
        return null;
    }

    @Override
    public PageInfo<MaxNo> findByPage(String data) {
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
    public void add(MaxNo maxNo) {
         maxNoMapper.add(maxNo);
    }


    @Override
    public void update(MaxNo data) {

         maxNoMapper.update(data);
    }

    @Override
    public int delete(MaxNo data) {
        return 0;
    }

    @Override
    public void deleteByID(Integer id) {

    }

    @Override
    public MaxNo findByDepID(Integer id) {
        return maxNoMapper.findByDepID(id);
    }
}
