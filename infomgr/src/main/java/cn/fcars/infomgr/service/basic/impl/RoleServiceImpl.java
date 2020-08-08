package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.Perm;
import cn.fcars.infomgr.entity.basic.Role;
import cn.fcars.infomgr.entity.basic.RolePerm;
import cn.fcars.infomgr.mapper.basic.RoleMapper;
import cn.fcars.infomgr.mapper.basic.RolePermMapper;
import cn.fcars.infomgr.service.basic.RolePermService;
import cn.fcars.infomgr.service.basic.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMenper;
    @Autowired
    RolePermMapper rolePermMapper;
    @Autowired
    RolePermService rolePermService;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    Static aStatic;

    @Override
    public Role findByID(Integer id) {
        return roleMenper.findByID(id);
    }

    @Override
    public Role findByStringID(String id) {
        return null;
    }

    @Override
    public Role findByName(String name) {

        Role role=roleMenper.findByName(name);

        return role;
    }

    @Override
    public List<Role> findAll() {
        return roleMenper.findAll();
    }

    @Override
    public List<Role> findByQuery(BaseQuery data) {
        return roleMenper.findByQuery(data);
    }

    @Override
    public PageInfo<Role> findByPage(BaseQuery data) {
        Integer pageSize = aStatic.getPageSize();
        Integer pageNum=1;
        if (data.getPageNum()==null) {
            pageNum=1;
        }
        else {
            pageNum=data.getPageNum();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = findByQuery(data);
        PageInfo<Role> pageInfo = new PageInfo(roleList);
        return pageInfo;
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
    public void add(Role data) {

    }

    @Transactional(rollbackFor=Exception.class)
    public void add(Role role,String permChoice) throws RuntimeException {
        try {
            if (findByName(role.getRoleName()) != null) {
                throw new RuntimeException("已有此权限名称");
            }
            Date date=new Date();
            role.setIsDelete("0");
            role.setCreateTime(date);
            role.setUpdateTime(date);
            roleMenper.add(role);
            rolePermMapper.deleteByRoleID(role.getRoleID());
            String[] permChoiceStr = permChoice.split(",");
            for (int i = 0; i < permChoiceStr.length; i++) {
                RolePerm rolePerm = new RolePerm();
                Perm perm = new Perm();
                Integer permID = Integer.parseInt(permChoiceStr[i]);
                perm.setPermID(permID);
                rolePerm.setPerm(perm);
                rolePerm.setRole(role);
                rolePermMapper.add(rolePerm);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void update(Role role, String permChoice) throws RuntimeException {
        try{
            Role roleBase=findByID(role.getRoleID());
            roleBase.setRoleDescript(role.getRoleDescript());
            roleBase.setUpdateTime(new Date());
            roleMenper.update(roleBase);
            rolePermMapper.deleteByRoleID(role.getRoleID());
            String[] permChoiceStr = permChoice.split(",");
            for (int i = 0; i < permChoiceStr.length; i++) {
                if(!StringUtils.isEmpty(permChoiceStr[i]))
                {
                    Integer permID = Integer.parseInt(permChoiceStr[i]);
                    RolePerm rolePerm = new RolePerm();
                    Perm perm = new Perm();
                    perm.setPermID(permID);
                    rolePerm.setPerm(perm);
                    rolePerm.setRole(roleBase);
                    rolePermMapper.add(rolePerm);
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(Role data) {
         roleMenper.update(data);
    }

    @Override
    public int delete(Role data) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor=Exception.class )
    public void deleteByID(Integer id) throws RuntimeException {
        try {
            List<RolePerm> rolePermList = rolePermMapper.findByParam(id,null);
            if(rolePermList.size()>0)
            {
                rolePermMapper.deleteByRoleID(id);
            }
            //roleMapper.deleteByID(id);
            Role role=roleMapper.findByID(id);
            role.setIsDelete("1");
            roleMapper.update(role);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }
}
