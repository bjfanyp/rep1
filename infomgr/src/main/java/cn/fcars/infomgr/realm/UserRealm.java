package cn.fcars.infomgr.realm;

import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.*;
import cn.fcars.infomgr.service.basic.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 编写自定义Realm
 * @author dxyd
 *
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    @Autowired
    PermService permService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RolePermService rolePermService;
    @Autowired
    Static aStatic;

    @Override
    public String getName() {
        return "UserRealm";
    }


    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户名
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        Set<String> stringSet = new HashSet<>();
        Set<String> roleSet= new HashSet<>();
        if(!"admin".equals(primaryPrincipal.toString())) {
            User user = userService.findByName(primaryPrincipal.toString());
            List<UserRole> userRoleList = userRoleService.findByPara(user.getUserID(), null);
            for (int i = 0; i < userRoleList.size(); i++) {
                roleSet.add(userRoleList.get(i).getRole().getRoleName());
                List<RolePerm> rolePermList = rolePermService.findByParam(userRoleList.get(i).getRole().getRoleID(), null);
                for (int j = 0; j < rolePermList.size(); j++) {
                    Perm perm = permService.findByID(rolePermList.get(j).getPerm().getPermID());
                    stringSet.add(perm.getPermToken());
                }
            }
        }
        else
        {
            List<Perm> permList = permService.findAll();
            for(Perm perm : permList)
            {
                stringSet.add(perm.getPermToken());
            }
            List<Role> roleList= roleService.findAll();
            for(Role role : roleList)
            {
                roleSet.add(role.getRoleName());
            }
        }
        SimpleAuthorizationInfo simpleAuthenticationInfo=new SimpleAuthorizationInfo();
        simpleAuthenticationInfo.setStringPermissions(stringSet);
        simpleAuthenticationInfo.setRoles(roleSet);
        return simpleAuthenticationInfo;
    }

    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username=(String)authenticationToken.getPrincipal();
        if(username!=null) {
            User user =userService.findByName(username);
            if (user == null) {
                return null;
            }
            if(user.getUserStatus().equals("2"))
            {
                throw new DisabledAccountException();
            }
            if(user.getUserStatus().equals("1"))
            {
                Date date=user.getUpdateTime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MINUTE,aStatic.sdtime);
                date=calendar.getTime();
                if(new Date().getTime()<date.getTime())
                {
                    throw new LockedAccountException();
                }
            }
            if(new Date().getTime()>user.getUserEffective().getTime())
            {
                throw new ExpiredCredentialsException();
            }
            SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(username,user.getUserPassword(), ByteSource.Util.bytes(username),getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}