package cn.fcars.infomgr.controller.basic.role;

import cn.fcars.infomgr.common.*;
import cn.fcars.infomgr.entity.basic.Menu;
import cn.fcars.infomgr.entity.basic.Perm;
import cn.fcars.infomgr.entity.basic.Role;
import cn.fcars.infomgr.entity.basic.RolePerm;
import cn.fcars.infomgr.service.basic.MenuService;
import cn.fcars.infomgr.service.basic.PermService;
import cn.fcars.infomgr.service.basic.RolePermService;
import cn.fcars.infomgr.service.basic.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    RolePermService rolePermService;
    @Autowired
    PermService permService;

    @RequestMapping("/roles")
    @RequiresPermissions("roles")
    public String list(BaseQuery roleQuery, Model model, HttpSession session)
    {
        session.setAttribute("currentMenu","roles");
        Object object = session.getAttribute("roleQuery");
        if(null!=object&&roleQuery.getRoleName()==null)
        {
            roleQuery=(BaseQuery)object;
        }
        try {
            PageInfo<Role> pageInfo = roleService.findByPage(roleQuery);
            model.addAttribute("pageInfo", pageInfo);
            session.setAttribute("roleQuery",roleQuery);
            return "role/list";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @GetMapping("/role")
    @RequiresPermissions("rest[role:read]")
    public String toAddPage(HttpSession session,Model model)
    {
        try {
            return "role/add";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @PostMapping("/role")
    @RequiresPermissions("rest[role:post]")
    public String add(@Valid Role role, BindingResult result, String permChoice,  Model model)
    {
        model.addAttribute("role",role);
        model.addAttribute("permChoice",permChoice);
        try {
            if (result.hasErrors()){
                List<ObjectError> errorList = result.getAllErrors();
                for(ObjectError error : errorList){
                    throw new Exception("输入参数不合规");
                }
            }
            roleService.add(role,permChoice);
            return "redirect:/roles";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "role/add";
        }
    }

    @GetMapping("/role/{id}")
    @RequiresPermissions("rest[role:read]")
    public String toUpdatePage(@PathVariable Integer id, Model model)
    {
        try {
            Role role = roleService.findByID(id);
            model.addAttribute("role", role);
            return "role/update";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PutMapping("/role")
    @RequiresPermissions("rest[role:put]")
    public String update(@Valid Role role,BindingResult result,String permChoice, Model model)
    {
        model.addAttribute("role",role);
        model.addAttribute("permChoice",permChoice);
        try {
            if (result.hasErrors()){
                List<ObjectError> errorList = result.getAllErrors();
                model.addAttribute("role",role);
                for(ObjectError error : errorList){
                    throw new Exception("输入参数不合规");
                }
            }
            roleService.update(role,permChoice);
            return "redirect:/roles";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "role/update";
        }
    }

    @DeleteMapping("/role/{id}")
    @RequiresPermissions("rest[role:delete]")
    @ResponseBody
    public ResultMsg delete(@PathVariable Integer id)
    {
        try {
            roleService.deleteByID(id);
            return new ResultMsg(true,"删除成功");
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"删除失败");
        }
    }

    @RequestMapping("/roleCheck")
    @ResponseBody
    public ResultMsg check(@RequestBody Map<String,String> map, Model model)
    {
        try {
            String roleName=map.get("roleName");
            if( roleService.findByName(roleName)==null) {
                return new ResultMsg(true,"");
            }
            else {
                return new ResultMsg(false,"已有此权限名称");
            }
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"数据异常");
        }
    }
}
