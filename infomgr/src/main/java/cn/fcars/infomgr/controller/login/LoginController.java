package cn.fcars.infomgr.controller.login;


import cn.fcars.infomgr.common.LoginPara;
import cn.fcars.infomgr.common.ResultMsg;
import cn.fcars.infomgr.common.Static;
import cn.fcars.infomgr.entity.basic.*;
import cn.fcars.infomgr.service.basic.*;
import com.github.pagehelper.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by fanyp on 2018/11/25.
 */
@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    Static aStatic;
    @Autowired
    MenuService menuService;
    @Autowired
    PermService permService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RolePermService rolePermService;

    public static final String SessionSecurityCode = "imageCode";
    public static final String SessionCurrentUser = "currentUser";
    public static final String SessionCurrentMenu = "currentMenu";
    public static final String SessionMenuList = "menuList";
    public static final String SessionMenuParentList = "menuParentList";
    public static final String userAdmin = "admin";
    public static final String userZt0 = "0";
    public static final String userZt1 = "1";
    public static final Integer userErrorCount = 0;

    @RequestMapping("/login")
    public String login(@Valid LoginPara loginPara,BindingResult result,Model model,HttpServletRequest request)
    {
        model.addAttribute("loginPara",loginPara);
        User user=null;
        try {
            if(result.hasErrors())
            {
                List<ObjectError> errorList = result.getAllErrors();
                model.addAttribute("loginPara",loginPara);
                for(ObjectError error : errorList){
                    return "forward:/login.jsp";
                }
            }
            String sessionTemp =(String)request.getSession().getAttribute("imageCode");
            if(!sessionTemp.equals(loginPara.getYzm().toUpperCase()))
            {
                throw new Exception("验证码不正确");
            }
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token=new UsernamePasswordToken(loginPara.getUserName(),loginPara.getUserPassword());
            subject.login(token);
            user = userService.findByName(loginPara.getUserName());
            user.setUserErrorCount(userErrorCount);
            user.setUserStatus(userZt0);
            userService.update(user);
            request.getSession().setAttribute(SessionCurrentUser,user);
            Session session = subject.getSession(true);
            session.setAttribute(SessionCurrentUser,user);
            session.removeAttribute(SessionSecurityCode);
            return "redirect:/main";
        }
        catch (Exception e)
        {
            String excptionClassName=e.getClass().getName();
            if(UnknownAccountException.class.getName().equals(excptionClassName))
            {
                excptionClassName="账号不存在";
            }
            else if(IncorrectCredentialsException.class.getName().equals(excptionClassName))
            {
                if(user==null) {
                    user = userService.findByName(loginPara.getUserName());
                }
                int errorcount=user.getUserErrorCount();
                if(errorcount<aStatic.logonerrornum) {
                    errorcount++;
                    user.setUserErrorCount(errorcount);
                    if(errorcount==aStatic.logonerrornum)
                    {
                        user.setUserStatus(userZt1);//超出次数进行锁定
                    }
                }
                user.setUserErrorCount(errorcount);
                try {
                    userService.update(user);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                int err=5-errorcount;
                if(err!=0) {
                    excptionClassName="用户名密码错误,还可以尝试" + err + "次";
                }else
                {
                    excptionClassName="账号已被锁定";
                }
            }
            else if(DisabledAccountException.class.getName().equals(excptionClassName))
            {
                excptionClassName="账号已被禁用";
            }
            else if(LockedAccountException.class.getName().equals(excptionClassName))
            {
                excptionClassName="账号已被禁用，请等待"+aStatic.sdtime+"分钟";
            }
            else if(ExcessiveAttemptsException.class.getName().equals(excptionClassName))
            {
                excptionClassName="登录失败次数过多";
            }
            else if(ExpiredCredentialsException.class.getName().equals(excptionClassName))
            {
                excptionClassName="凭证过期";
            }
            else
            {
                if(e.getMessage().contains("账号不能为空"))
                {
                    excptionClassName = "账号不能为空";
                }
                else if(e.getMessage().contains("密码不能为空"))
                {
                    excptionClassName = "密码不能为空";
                }
                else if(e.getMessage().contains("验证码不能为空"))
                {
                    excptionClassName = "验证码不能为空";
                }
                else if(e.getMessage().contains("验证码不正确"))
                {
                    model.addAttribute("yzmerrorMsg","验证码不正确");
                    return "forward:/login.jsp";
                }
                else {
                    excptionClassName = "其他异常";
                }
            }
            model.addAttribute("errorMsg",excptionClassName);
            return "forward:/login.jsp";
        }
    }

    @RequestMapping("/main")
    public String toMainPage(HttpSession session,Model model) throws Exception
    {
        session.setAttribute(SessionCurrentMenu,"main");

        User user = (User)session.getAttribute(SessionCurrentUser);
        List<Menu> menuList = new ArrayList<>();
        List<Menu> menuParentList = new ArrayList<>();
        if(userAdmin.equals(user.getUserName())) {
            menuList=menuService.findAll();
            menuParentList=menuService.findParentMenu();
        }
        else
        {
            Set<Integer> menuSet=new HashSet<>();
            Set<Integer> menuParentSet=new HashSet<>();
            List<UserRole> userRoleList = userRoleService.findByPara(user.getUserID(), null);
            if(userRoleList.size()>0) {
                for (int i = 0; i < userRoleList.size(); i++) {
                    List<RolePerm> rolePermList = rolePermService.findByParam(userRoleList.get(i).getRole().getRoleID(), null);
                    for (int j = 0; j < rolePermList.size(); j++) {
                        Perm perm = permService.findByID(rolePermList.get(j).getPerm().getPermID());
                        menuSet.add(perm.getMenu().getMenuID());
                    }
                }
            }
            if(menuSet.size()>0)
            {
                Iterator it = menuSet.iterator();
                while(it.hasNext())
                {
                    Menu menu=menuService.findByID(Integer.parseInt(it.next().toString()));
                    menuList.add(menu);
                    int num = menuParentSet.size();
                    menuParentSet.add(menu.getParentMenu().getMenuID());
                    if(num<menuParentSet.size()) {
                        menuParentList.add(menu.getParentMenu());
                    }
                }
            }
        }
        session.setAttribute(SessionMenuList, menuList);
        session.setAttribute(SessionMenuParentList, menuParentList);
        return "main";
    }
}
