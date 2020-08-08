package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.Perm;
import cn.fcars.infomgr.entity.basic.RolePerm;
import cn.fcars.infomgr.mapper.basic.PermMapper;
import cn.fcars.infomgr.mapper.basic.RolePermMapper;
import cn.fcars.infomgr.service.basic.PermService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermServiceImpl implements PermService {

    @Autowired
    PermMapper permMapper;
    @Autowired
    RolePermMapper rolePermMapper;
    @Autowired
    Static aStatic;

    @Override
    public Perm findByID(Integer id) {
        return permMapper.findByID(id);
    }

    @Override
    public Perm findByStringID(String id) {
        return null;
    }

    @Override
    public Perm findByName(String name) {

        Perm data=permMapper.findByName(name);

        return data;
    }

    @Override
    public List<Perm> findAll() {
        return permMapper.findAll();
    }

    @Override
    public List<Perm> findByQuery(BaseQuery permisQuery) {
        return permMapper.findByQuery(permisQuery);
    }

    @Override
    public PageInfo<Perm> findByPage(BaseQuery permQuery) {
        Integer pageNum=1;
        if (permQuery.getPageNum()!=null) {
            pageNum=permQuery.getPageNum();
        }
        if("0".equals(permQuery.getMenuID()))
        {
            permQuery.setMenuID(null);
        }
        Integer pageSize = aStatic.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Perm> permList = findByQuery(permQuery);
        PageInfo<Perm> pageInfo = new PageInfo(permList);
        return pageInfo;
    }

    @Override
    public Integer check(String name) {
        return permMapper.check(name);
    }

    @Override
    public boolean checkExist(String id, String name) {
        return false;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Perm data) throws RuntimeException{
        try {
            if (findByName(data.getPermName()) != null) {
                throw new RuntimeException("已有此名称");
            }
            permMapper.add(data);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Perm perm) throws RuntimeException{
        try {
            Perm permBase = findByID(perm.getPermID());
            permBase.setMenu(perm.getMenu());
            permBase.setPermToken(perm.getPermToken());
            permBase.setPermSxh(perm.getPermSxh());
            permMapper.update(permBase);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int delete(Perm data) {
        return permMapper.delete(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByID(Integer id) throws RuntimeException {
        try {
            List<RolePerm> rolePermList = rolePermMapper.findByParam(null, id);
            if (rolePermList.size() > 0) {
                rolePermMapper.deleteByPermID(id);
            }
            permMapper.deleteByID(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
