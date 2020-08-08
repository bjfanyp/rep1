package cn.fcars.infomgr.controller.basic.user;
import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.ResultMsg;
import cn.fcars.infomgr.entity.basic.Dep;
import cn.fcars.infomgr.entity.basic.Role;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.entity.basic.UserRole;
import cn.fcars.infomgr.service.basic.DepService;
import cn.fcars.infomgr.service.basic.RoleService;
import cn.fcars.infomgr.service.basic.UserRoleService;
import cn.fcars.infomgr.service.basic.UserService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Pattern;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.validation.Valid;
/**
 * Created by fanyp on 2018/11/25.
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    DepService depService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleService userRoleService;

    @RequestMapping("/users")
    @RequiresPermissions("users")
    public String list(BaseQuery userQuery, Model model,HttpSession session)
    {
        try {
            //设置session的菜单属性
            session.setAttribute("currentMenu","users");
            Object object = session.getAttribute("userQuery");
            if(null==userQuery.getDepID()&&null!=object)
            {
                userQuery=(BaseQuery)object;
            }
            PageInfo<User> pageInfo = userService.findByPage(userQuery);
            model.addAttribute("pageInfo", pageInfo);
            List<Dep> depList = depService.findAll();
            model.addAttribute("depList",depList);
            session.setAttribute("userQuery",userQuery);
            session.removeAttribute("depList");
            session.removeAttribute("roleList");
            session.removeAttribute("userRole");
            return "user/list";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @GetMapping("/user1")
    @ResponseBody
    public String test(HttpServletRequest request)
    {
        try {
             List<User> userList = userService.findAll();
             return request.getParameter("callback")+"("+JSON.toJSONString(userList)+")";
         }
         catch (Exception e)
         {
             return null;
         }
    }

    @GetMapping("/user")
    @RequiresPermissions("rest[user:read]")
    public String toAddPage(HttpSession session, Model model)
    {
        try {
            List<Dep> depList = depService.findAll();
            session.setAttribute("depList",depList);
            List<Role> roleList = roleService.findAll();
            session.setAttribute("roleList",roleList);
            Calendar c = Calendar.getInstance();
            Date date = new Date();
            c.setTime(date);
            c.add(Calendar.YEAR, 1);
            date = c.getTime();
            model.addAttribute("date", date);
            return "user/add";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @PostMapping("/user")
    @RequiresPermissions("rest[user:post]")
    public String add(@Valid User user,BindingResult result,Integer roleID,HttpSession session,Model model)
    {
        model.addAttribute("user",user);
        model.addAttribute("roleID",roleID);
        try {
            if (result.hasErrors()){
                List<ObjectError> errorList = result.getAllErrors();
                for(ObjectError error : errorList){
                    //密码是默认的在此处不用校验
                   if(!error.getDefaultMessage().contains("密码"))
                   {
                       throw new Exception("输入参数不合规");
                   }
                }
           }
            userService.add(user,roleID);
            session.removeAttribute("depList");
            session.removeAttribute("roleList");
            return "redirect:/users";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "user/add";
        }
    }


    @RequestMapping("/user/{id}")
    @RequiresPermissions("rest[user:read]")
    public String toUpdatePage(@PathVariable Integer id, HttpSession session, Model model)
    {
        try {
            User user = userService.findByID(id);
            model.addAttribute("user",user);

            List<Dep> depList = depService.findAll();
            session.setAttribute("depList", depList);

            List<Role> roleList = roleService.findAll();
            session.setAttribute("roleList",roleList);

            List<UserRole> userRoleList = userRoleService.findByPara(id,null);
            if(userRoleList.size()>0) {
                session.setAttribute("userRole", userRoleList.get(0));
            }
            else {
                session.setAttribute("userRole",null);
            }
            return "user/update";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }


    @PutMapping("/user")
    @RequiresPermissions("rest[user:put]")
    public String update(@Valid  User userInput,BindingResult result,Integer roleID,HttpSession session,Model model)
    {
        model.addAttribute("user",userInput);
        model.addAttribute("roleID",roleID);

        try {
            if(roleID==null)
            {
                throw new Exception("权限不能为空");
            }
            if (result.hasErrors()){
                List<ObjectError> errorList= result.getAllErrors();
                for(ObjectError error : errorList){
                    if(!error.getDefaultMessage().contains("密码"))
                    {
                        throw new Exception("输入参数不合规");
                    }
                }
            }
            userService.update(userInput,roleID);
            session.removeAttribute("depList");
            session.removeAttribute("roleList");
            session.removeAttribute("userRole");
            return "redirect:/users";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "user/update";
        }
    }
    @DeleteMapping("/user/{id}")
    @RequiresPermissions("rest[user:delete]")
    @ResponseBody
    public ResultMsg delete(@PathVariable Integer id)
    {
        try {
            /*
             *user_role表中的信息不执行相关删除操作，防止误删除造成数据信息的丢失。
             */
            userService.deleteByID(id);
            return new ResultMsg(true,"删除成功");
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"删除失败");
        }
    }
    @RequestMapping("/reset/{id}")
    @RequiresPermissions("reset")
    @ResponseBody
    public ResultMsg reset(@PathVariable Integer id)
    {
        try {
            userService.reset(id);
            return new ResultMsg(true, "重置密码成功");
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"重置密码失败");
        }
    }
    @RequestMapping("/userCheck")
    @ResponseBody
    public ResultMsg check(@RequestBody Map<String,String> map,Model model)
    {
        try {
            String username=map.get("userName");
            Integer check= userService.check(username);
            if(check==0) {
                return new ResultMsg(true,"");
            }
            else {
                return new ResultMsg(false,"已有此用户名");
            }
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"数据异常");
        }
    }

    @GetMapping("/updatePw")
    public String toUpdatePage()
    {
        return "user/updatePw";
    }

    @ResponseBody
    @PostMapping("/updatePw")
    public ResultMsg updatePwd(@RequestBody Map<String,String> map)
    {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            String username = currentUser.getPrincipal().toString();
            User bUser = userService.findByName(username);
            String oldPw=map.get("oldPw");
            String newPw=map.get("newPw");
            String dfPw=map.get("dfPw");
            if(StringUtil.isEmpty(oldPw))
            {
                return new ResultMsg(false, "原密码不能为空");
            }
            if(StringUtil.isEmpty(newPw))
            {
                return new ResultMsg(false, "新密码不能为空");
            }
            if(StringUtil.isEmpty(dfPw))
            {
                return new ResultMsg(false, "确认密码不能为空");
            }
            if(!newPw.equals(dfPw))
            {
                return new ResultMsg(false, "确认密码不一致");
            }
            String pattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$";
            if(!Pattern.matches(pattern, newPw))
            {
                return new ResultMsg(false, "新密码长度要大于6位，由数字和字母组成，不含特殊字符");
            }
            String password=new SimpleHash("MD5",oldPw, ByteSource.Util.bytes(username),3).toString();

            if (!bUser.getUserPassword().equals(password)) {
                return new ResultMsg(false, "原密码不正确");
            } else {
                newPw=new SimpleHash("MD5",newPw, ByteSource.Util.bytes(username),3).toString();

                bUser.setUserPassword(newPw);
                bUser.setUserErrorCount(0);
                bUser.setUpdateTime(new Date());
                userService.update(bUser);
                return new ResultMsg(true, "操作完成");
            }
        }
        catch (Exception e)
        {
            return new ResultMsg(false, "异常，操作失败");
        }
    }
}
