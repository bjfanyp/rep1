package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.entity.basic.UserRole;
import cn.fcars.infomgr.mapper.basic.UserRoleMapper;
import cn.fcars.infomgr.service.basic.UserRoleService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleMapper userRoleMenper;

    @Override
    public UserRole findByID(Integer id) {
        return userRoleMenper.findByID(id);
    }

    @Override
    public UserRole findByStringID(String id) {
        return null;
    }

    @Override
    public UserRole findByName(String name) {
        return null;
    }

    @Override
    public List<UserRole> findAll() {
        return userRoleMenper.findAll();
    }

    @Override
    public List<UserRole> findByQuery(Null data) {
        return null;
    }

    @Override
    public PageInfo<UserRole> findByPage(Null data) {
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
    public void add(UserRole userRole) {
         userRoleMenper.add(userRole);
    }


    @Override
    public void update(UserRole data) {

         userRoleMenper.update(data);
    }

    @Override
    public int delete(UserRole data) {
        return 0;
    }

    @Override
    public void deleteByID(Integer id) {
    }

    public List<UserRole> findByPara(@Param("userID") Integer userID, @Param("roleID") Integer roleID)
    {
        return  userRoleMenper.findByPara(userID,roleID);
    }


}
