package cn.fcars.infomgr.controller.basic.perm;

import cn.fcars.infomgr.common.*;
import cn.fcars.infomgr.entity.basic.Account;
import cn.fcars.infomgr.entity.basic.Menu;
import cn.fcars.infomgr.entity.basic.Perm;
import cn.fcars.infomgr.entity.basic.RolePerm;
import cn.fcars.infomgr.service.basic.AccountService;
import cn.fcars.infomgr.service.basic.MenuService;
import cn.fcars.infomgr.service.basic.PermService;
import cn.fcars.infomgr.service.basic.RolePermService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class PermController {

    @Autowired
    PermService permService;
    @Autowired
    MenuService menuService;
    @Autowired
    RolePermService rolePermService;

    @RequestMapping("/perms")
    @RequiresPermissions("perms")
    public String list(BaseQuery permQuery, Model model, HttpServletRequest request)
    {
        request.getSession().setAttribute("currentMenu","perms");
        Object object = request.getSession().getAttribute("permQuery");
        if(null==permQuery.getMenuID()&&null!=object)
        {
            permQuery=(BaseQuery)object;
        }
        try {
            PageInfo<Perm> pageInfo = permService.findByPage(permQuery);
            model.addAttribute("pageInfo", pageInfo);
            List<Menu> menuList=menuService.findAll();
            model.addAttribute("menuList", menuList);
            List<Menu> menuParentList=menuService.findParentMenu();
            model.addAttribute("menuParentList", menuParentList);
            request.getSession().setAttribute("permQuery",permQuery);
            return "perm/list";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @GetMapping("/perm")
    @RequiresPermissions("rest[perm:read]")
    public String toAddPage(HttpSession session)
    {
        List<Menu> menuList = menuService.findAll();
        session.setAttribute("menuList",menuList);
        List<Menu> menuParentList=menuService.findParentMenu();
        session.setAttribute("menuParentList", menuParentList);
        return  "perm/add";
    }

    @PostMapping("/perm")
    @RequiresPermissions("rest[perm:post]")
    public String add(@Valid Perm perm, BindingResult result, Model model, HttpSession session)
    {
        model.addAttribute("perm",perm);
        try {
            if (result.hasErrors()){
                List<ObjectError> errorList = result.getAllErrors();
                for(ObjectError error : errorList){
                    throw new Exception("输入参数不合规");
                }
            }
            permService.add(perm);
            session.removeAttribute("menuList");
            session.removeAttribute("menuParentList");
            return "redirect:/perms";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "perm/add";
        }
    }

    @GetMapping("/perm/{id}")
    @RequiresPermissions("rest[perm:read]")
    public String toUpdatePage(@PathVariable Integer id, HttpSession session,Model model)
    {
        try {
            Perm perm = permService.findByID(id);
            model.addAttribute("perm",perm);
            List<Menu> menuList = menuService.findAll();
            session.setAttribute("menuList",menuList);
            List<Menu> menuParentList=menuService.findParentMenu();
            session.setAttribute("menuParentList", menuParentList);
            return "perm/update";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PutMapping("/perm")
    @RequiresPermissions("rest[perm:put]")
    public String update(@Valid  Perm perm,BindingResult result,Model model ,HttpSession session,RedirectAttributes att)
    {
        model.addAttribute("perm",perm);
        try {
            if (result.hasErrors()){
                List<ObjectError> errorList = result.getAllErrors();
                for(ObjectError error : errorList){
                    throw new Exception("输入参数不合规");
                }
            }
            permService.update(perm);
            session.removeAttribute("menuList");
            session.removeAttribute("menuParentList");
            return "redirect:/perms";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "perm/update";
        }
    }
    @DeleteMapping("/perm/{id}")
    @RequiresPermissions("rest[perm:delete]")
    @ResponseBody
    public ResultMsg delete(@PathVariable Integer id)
    {
        try {
            permService.deleteByID(id);
            return new ResultMsg(true, "删除成功");
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"删除失败");
        }
    }

    @RequestMapping("/permCheck")
    @ResponseBody
    public ResultMsg check(@RequestBody Map<String,String> map, Model model)
    {
        try {
            String permName=map.get("permName");
            if( permService.findByName(permName)==null) {
                return new ResultMsg(true,"");
            }
            else {
                return new ResultMsg(false,"已有此名称");
            }
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"数据异常");
        }
    }
}
