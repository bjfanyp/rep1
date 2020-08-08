package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.*;
import cn.fcars.infomgr.mapper.basic.DepMapper;
import cn.fcars.infomgr.mapper.basic.RoleMapper;
import cn.fcars.infomgr.mapper.basic.UserMapper;
import cn.fcars.infomgr.mapper.basic.UserRoleMapper;
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
public class UserServiceImpl implements UserService {

    @Autowired
    Static aStatic;
    @Autowired
    UserMapper userMapper;
    @Autowired
    DepMapper depMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public User findByID(Integer id) {
        return userMapper.findByID(id);
    }

    @Override
    public User findByStringID(String id) {
        return null;
    }

    @Override
    public User findByName(String name) {
        User user=userMapper.findByName(name);
        return user;
    }
    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public PageInfo<User> findByPage(BaseQuery userQuery) {

        Integer pageNum=1;
        if (userQuery.getPageNum()!=null) {
            pageNum=userQuery.getPageNum();
        }
        if (userQuery.getDepID()!=null && userQuery.getDepID()==0) {
            userQuery.setDepID(null);
        }
        Integer pageSize = aStatic.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.findByQuery(userQuery);
        for(User user :userList) {
            if(user.getUserStatus().equals("0"))
                user.setUserStatus("正常");
            else if(user.getUserStatus().equals("1"))
                user.setUserStatus("锁定");
            else if(user.getUserStatus().equals("2"))
                user.setUserStatus("禁用");
        }
        PageInfo<User> pageInfo = new PageInfo(userList);

        return pageInfo;
    }

    @Override
    public Integer check(String name) {
        return userMapper.check(name);
    }

    @Override
    public boolean checkExist(String id, String name) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(User user, Integer roleID){
        try {
            Date now = new Date();
            Integer depID = user.getDep().getDepID();
            Dep dep = depMapper.findByID(depID);
            user.setDep(dep);
            String passWord = new SimpleHash("MD5", "123456", ByteSource.Util.bytes(user.getUserName()), 3).toString();
            user.setUserPassword(passWord);
            user.setIsDelete("0");
            user.setUserErrorCount(0);
            user.setUserStatus("0");
            user.setCreateTime(now);
            user.setUpdateTime(now);
            if (userMapper.findByName(user.getUserName()) != null) {
                throw new RuntimeException("当前用户名已存在，不能保存");
            }
            userMapper.add(user);
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            Role role = roleMapper.findByID(roleID);
            userRole.setRole(role);
            userRoleMapper.add(userRole);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     *事务回滚：程序自动捕捉后抛出的异常或者手动抛出的RunException()后可以回滚，
     * 手动抛出的Exception不会产生回滚
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void update(User userInput, Integer roleID) {
        try {
            Date now = new Date();
            User user = findByID(userInput.getUserID());
            user.setDep(userInput.getDep());
            user.setUserTrueName(userInput.getUserTrueName());
            user.setUserTel(userInput.getUserTel());
            user.setUpdateTime(now);
            user.setUserEffective(userInput.getUserEffective());
            userMapper.update(user);
            List<UserRole> userRoleList = userRoleMapper.findByPara(userInput.getUserID(), null);
            if (userRoleList.size() != 0) {
                userRoleMapper.deleteByUserID(userInput.getUserID());
            }
            UserRole userRole = new UserRole();
            Role role = new Role();
            role.setRoleID(roleID);
            userRole.setRole(role);
            userRole.setUser(user);
            userRoleMapper.add(userRole);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByID(Integer id) throws Exception {
        try {
            Date now = new Date();
            User user = userMapper.findByID(id);
            user.setIsDelete("1");
            user.setUpdateTime(now);
            userMapper.update(user);
            userRoleMapper.deleteByUserID(id);
        }
        catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reset(Integer id){
        try {
            Date now=new Date();
            User user=userMapper.findByID(id);
            String pwd= new SimpleHash("MD5","123456", ByteSource.Util.bytes(user.getUserName()),3).toString();
            user.setUserPassword(pwd);
            user.setUpdateTime(now);
            userMapper.update(user);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        Date now = new Date();
        user.setUpdateTime(now);
        userMapper.update(user);
    }
    @Override
    public int delete(User data) {
        return 0;
    }

    @Override
    public void add(User data) {

    }

    @Override
    public List<User> findByQuery(BaseQuery data) {
        return null;
    }

    @Override
    public Map<String, Object> login(String username, String password) throws Exception {
        Map<String,Object>map=new HashMap<String, Object>();
        boolean flag=false;
        String msg=null;
        User user=userMapper.findByName(username);
        if (user!=null) {
            if (MD5Util.MD5Encode(username+password, "UTF-8").equals(user.getUserPassword())) {
                flag=true;
                AgentUser agentUser=new AgentUser();
                agentUser.setId(user.getUserID().toString());
                agentUser.setAgentUserName(user.getUserName());
                agentUser.setAgentPassword(password);
                agentUser.setAgentRealName(user.getUserTrueName());
                agentUser.setAgentStatus("2");
                map.put("agentUser", agentUser);
//                List<AgentRealNameAuth> oldAgentRealNameAuths=agentRealNameAuthMapper.findByAttr("agent_user_name='"+username+"'", "created_time desc");
//                if (!oldAgentRealNameAuths.isEmpty()) {
//                    map.put("agentUserAuth", oldAgentRealNameAuths.get(0));
//                }
            }else{
                msg="密码错误,请重新输入密码!";
            }

        }else {
            msg="用户名不存在,请重新输入!";
        }
        map.put("msg",msg);
        map.put("flag",flag);
        return map;
    }

    @Override
    public Map<String, Object> checkUsernameExist(String username) {
        Map<String,Object>map=new HashMap<String, Object>();
        boolean flag=false;
        String msg=null;
        User agentUsers=userMapper.findByName(username);
        if(agentUsers!=null&&agentUsers.getUserName()!=null){
            flag=false;
        }else {
            flag=true;
        }
        if (!flag) {
            msg="用户名已存在";
        }else {
            msg="检测通过";
        }
        map.put("msg",msg);
        map.put("flag",flag);
        return map;
    }

    @Override
    public Map<String, Object> register(String username, String password, String code) throws Exception {
        Map<String, Object>map=new HashMap<String, Object>();
        boolean flag=false;
        String msg=null;
        User agentUser=userMapper.findByName(username);
        if (agentUser==null) {
            agentUser=new User();
            agentUser.setUserName(username);
            agentUser.setUserTrueName(username);
            agentUser.setUserPassword(MD5Util.MD5Encode(username+password, "UTF-8"));
            agentUser.setUserStatus("0");
            agentUser.setUserTel(username);
            agentUser.setUserErrorCount(0);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2099,1,1);
            Date now = new Date();
            agentUser.setCreateTime(now);
            agentUser.setUpdateTime(now);
            agentUser.setUserEffective(calendar.getTime());
            agentUser.setIsDelete("0");
            agentUser.setDep( depMapper.findByName("汽车团购"));
            map.put("agentUser", agentUser);
            flag=userMapper.add(agentUser)>0?true:false;
            if (!flag) {
                msg="注册失败";
            }
        }else {
            msg="该手机号已经注册,请重新输入";
        }
        map.put("flag", flag);
        map.put("msg", msg);
        return map;
    }
    @Override
    public Map<String, Object> fotgotPassword(String username,String password) {
        User agentUsers=userMapper.findByName(username);
        Map<String,Object>map=new HashMap<String, Object>();
        boolean flag=false;
        String msg=null;
        if(agentUsers!=null){
            try {
                agentUsers.setUserPassword(MD5Util.MD5Encode(username+password,"UTF-8"));
                flag= userMapper.update(agentUsers)>0?true:false;
                if (!flag) {
                    msg="操作失败";
                }
            } catch (Exception e) {
                e.printStackTrace();
                msg=e.getLocalizedMessage();
            };
        }else{
            msg="用户不存在";
        }
        map.put("msg",msg);
        map.put("flag",flag);
        return map;
    }
}
