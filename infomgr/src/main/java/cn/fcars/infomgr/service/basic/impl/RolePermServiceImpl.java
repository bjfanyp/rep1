package cn.fcars.infomgr.service.basic.impl;


import cn.fcars.infomgr.entity.basic.RolePerm;
import cn.fcars.infomgr.mapper.basic.RolePermMapper;
import cn.fcars.infomgr.service.basic.RolePermService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolePermServiceImpl implements RolePermService {

    @Autowired
    RolePermMapper rolePermMapper;

    @Override
    public RolePerm findByID(Integer id) {
        return rolePermMapper.findByID(id);
    }

    @Override
    public RolePerm findByStringID(String id) {
        return null;
    }

    public List<RolePerm> findByParam(Integer roleID,Integer permID) {
        return rolePermMapper.findByParam(roleID,permID);
    }

    @Override
    public RolePerm findByName(String name) {
        return rolePermMapper.findByName(name);
    }

    @Override
    public List<RolePerm> findAll() {
        return rolePermMapper.findAll();
    }

    @Override
    public List<RolePerm> findByQuery(String data) {
        return null;
    }

    @Override
    public PageInfo<RolePerm> findByPage(String data) {
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
    public void add(RolePerm menu) {
         rolePermMapper.add(menu);
    }


    @Override
    public void update(RolePerm data) {

         rolePermMapper.update(data);
    }

    @Override
    public int delete(RolePerm data) {
        return rolePermMapper.delete(data);
    }

    @Override
    public void deleteByID(Integer id) {
         rolePermMapper.deleteByID(id);
    }

    public int deleteByPermID(Integer id) {
        return rolePermMapper.deleteByPermID(id);
    }

    public int deleteByRoleID(Integer id) {
        return rolePermMapper.deleteByRoleID(id);
    }
}
