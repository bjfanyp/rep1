package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.entity.basic.Enumerator;
import cn.fcars.infomgr.mapper.basic.EnumeratorMapper;
import cn.fcars.infomgr.service.basic.EnumeratorService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class EnumeratorServiceImpl implements EnumeratorService {

    @Autowired
    EnumeratorMapper enumeratorMapper;

    @Override
    public List<Enumerator> findByPara (String gnlb,String dmlb){
        return enumeratorMapper.findByPara(gnlb,dmlb);
    }

    @Override
    public Enumerator findByID(Integer id) {
        return null;
    }

    @Override
    public Enumerator findByStringID(String id) {
        return null;
    }

    @Override
    public Enumerator findByName(String name) {
        return null;
    }

    @Override
    public List<Enumerator> findAll() {
        return null;
    }

    @Override
    public List<Enumerator> findByQuery(Null data) {
        return null;
    }

    @Override
    public PageInfo<Enumerator> findByPage(Null data) {
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
    public void add(Enumerator data) throws Exception {

    }

    @Override
    public void update(Enumerator data) throws Exception {

    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(Enumerator data) throws Exception {
        return 0;
    }
}
