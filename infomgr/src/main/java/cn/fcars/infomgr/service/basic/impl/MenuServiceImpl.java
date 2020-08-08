package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.Menu;
import cn.fcars.infomgr.entity.basic.Perm;
import cn.fcars.infomgr.entity.basic.RolePerm;
import cn.fcars.infomgr.mapper.basic.MenuMapper;
import cn.fcars.infomgr.mapper.basic.RolePermMapper;
import cn.fcars.infomgr.service.basic.MenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    Static aStatic;
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RolePermMapper rolePermMapper;
    @Override
    public Menu findByID(Integer id) {
        return menuMapper.findByID(id);
    }

    @Override
    public Menu findByStringID(String id) {
        return null;
    }

    @Override
    public Menu findByName(String name) {
        return menuMapper.findByName(name);
    }

    @Override
    public List<Menu> findAll() {
        return menuMapper.findAll();
    }

    @Override
    public List<Menu> findByQuery(BaseQuery query) {
        return menuMapper.findByQuery(query);
    }

    @Override
    public PageInfo<Menu> findByPage(BaseQuery menuQuery) {
        Integer pageSize = aStatic.getPageSize();
        if(menuQuery.getPageNum()==null)
        {
            menuQuery.setPageNum(1);
        }
        if("0".equals(menuQuery.getMenuParentID()))
        {
            menuQuery.setMenuParentID(null);
        }
        PageHelper.startPage(menuQuery.getPageNum(), pageSize);
        List<Menu> menuList = findByQuery(menuQuery);
        PageInfo<Menu> pageInfo = new PageInfo(menuList);
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
    @Transactional(rollbackFor = Exception.class)
    public void add(Menu menu) throws RuntimeException{
        try {
            if (findByName(menu.getMenuName()) != null) {
                throw new Exception("已有此名称");
            }
            if(menu.getParentMenu().getMenuID()==0)
            {
                menu.setParentMenu(null);
            }
            menu.setCreateTime(new Date());
            menu.setUpdateTime(new Date());
            if(menu.getMenuIcon()!=null&&menu.getMenuIcon().equals("0"))
            {
                menu.setMenuIcon(null);
            }
            menu.setIsDelete("0");
            menuMapper.add(menu);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Menu menu) throws RuntimeException{
        try {
            Menu menuBase =menuMapper.findByID(menu.getMenuID());
            menuBase.setParentMenu(menu.getParentMenu());
            menuBase.setMenuUrl(menu.getMenuUrl());
            menuBase.setMenuSxh(menu.getMenuSxh());
            if(menu.getMenuIcon()!=null&&menu.getMenuIcon().equals("0"))
            {
                menuBase.setMenuIcon(null);
            }
            else
            {
                menuBase.setMenuIcon(menu.getMenuIcon());
            }
            if(menu.getParentMenu()!=null&&menu.getParentMenu().getMenuID()==0)
            {
                menuBase.setParentMenu(null);
            }
            menuBase.setUpdateTime(new Date());
            menuMapper.update(menuBase);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByID(Integer id) throws RuntimeException{
        try {
            Menu menu=menuMapper.findByID(id);
            if(menu.getParentMenu()==null) {
                List<Menu> menuList=menuMapper.findByParentID(menu.getMenuID());
                for(Menu base :menuList)
                {
                    if(base.getIsDelete().equals("0")) {
                        base.setIsDelete("1");
                        menuMapper.update(base);
                    }
                }
            }
            menu.setIsDelete("1");
            menu.setUpdateTime(new Date());
            menuMapper.update(menu);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Menu> findParentMenu() {
        return menuMapper.findParent();
    }

    @Override
    public List<Menu> findByParentID(Integer id) {
        return menuMapper.findByParentID(id);
    }

    @Override
    public String findMenuStr(Integer id) throws Exception {
        try {
            List<RolePerm> rolePermList =  rolePermMapper.findByParam(id, null);
            List<Menu> menuList = findAll();
            String resHead = "[{name:'权限列表', open: true,  isParent:true}";
            String resBody = "";
            String resFoot = "]";
            if (menuList.size() > 0) {
                for (Menu menu : menuList) {
                    String temp = "";
                    if (menu.getParentMenu() == null) {
                        temp = ",{name:'" + menu.getMenuName() + "', open: true, id: " + menu.getMenuID();
                        List<Menu> realMenuList = findByParentID(menu.getMenuID());
                        if (realMenuList.size() > 0) {
                            temp = temp + ",children: [";
                            for (int i = 0; i < realMenuList.size(); i++) {
                                temp = temp + "{name:'" + realMenuList.get(i).getMenuName() + "',open:true,id:" + realMenuList.get(i).getMenuID();
                                temp = temp + ",children: [";
                                if (realMenuList.get(i).getPermList().size() > 0) {
                                    List<Perm> permList = realMenuList.get(i).getPermList();
                                    for (int m = 0; m < permList.size(); m++) {
                                        String check = "";
                                        if (m == 0) {
                                            for (int k = 0; k < rolePermList.size(); k++) {
                                                if (rolePermList.get(k).getPerm().getPermID().equals(permList.get(m).getPermID())) {
                                                    check = ",checked:true";
                                                    break;
                                                }
                                            }
                                            temp = temp + "{name:'" + permList.get(m).getPermName() + "',id:" + permList.get(m).getPermID()   + check+"}";
                                        } else {
                                            for (int k = 0; k < rolePermList.size(); k++) {
                                                if (rolePermList.get(k).getPerm().getPermID().equals(permList.get(m).getPermID())) {
                                                    check = ",checked:true";
                                                    break;
                                                }
                                            }
                                            temp = temp + ",{name:'" + permList.get(m).getPermName() + "',id:" + permList.get(m).getPermID() +  check + "}";
                                        }
                                    }
                                }
                                temp = temp + "]";
                                temp = temp + "}";
                                if (i != realMenuList.size() - 1) {
                                    temp = temp + ",";
                                }
                            }
                            temp = temp + "]}";
                        } else {
                            temp = temp + "}";
                        }
                    }
                    resBody = resBody + temp;
                    continue;
                }

                return resHead + resBody + resFoot;
            } else {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public int delete(Menu data) {
        return 0;
    }
}
