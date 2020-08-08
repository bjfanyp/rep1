package cn.fcars.infomgr.controller.basic.menu;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.ResultMsg;
import cn.fcars.infomgr.entity.basic.Enumerator;
import cn.fcars.infomgr.entity.basic.Menu;
import cn.fcars.infomgr.service.basic.EnumeratorService;
import cn.fcars.infomgr.service.basic.MenuService;
import cn.fcars.infomgr.service.basic.RolePermService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {

    @Autowired
    MenuService menuService;

    @Autowired
    RolePermService rolePermService;

    @Autowired
    EnumeratorService enumeratorService;

    @RequestMapping("/menus")
    @RequiresPermissions("menus")
    public String list(BaseQuery menuQuery, Model model, HttpSession session)
    {
        session.setAttribute("currentMenu","menus");
        Object object = session.getAttribute("menuQuery");
        if(null!=object&&null==menuQuery.getMenuParentID())
        {
            menuQuery=(BaseQuery)object;
        }
        try {
            PageInfo<Menu> pageInfo = menuService.findByPage(menuQuery);
            model.addAttribute("pageInfo", pageInfo);
            List<Menu> parentMenuList=menuService.findParentMenu();
            model.addAttribute("parentMenuList", parentMenuList);
            session.setAttribute("menuQuery",menuQuery);
            session.removeAttribute("parentMenuList");
            session.removeAttribute("enumeratorList");
            return "menu/list";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @GetMapping("/menu")
    @RequiresPermissions("rest[menu:read]")
    public String toAddPage(HttpSession session)
    {
        List<Menu> parentMenuList=menuService.findParentMenu();
        session.setAttribute("parentMenuList", parentMenuList);
        List<Enumerator> enumeratorList = enumeratorService.findByPara("menu","icon");
        session.setAttribute("enumeratorList", enumeratorList);
        return  "menu/add";
    }

    @PostMapping("/menu")
    @RequiresPermissions("rest[menu:post]")
    public String add(@Valid Menu menu, BindingResult result, HttpSession session,Model model)
    {
        model.addAttribute("menu",menu);
        try {
            if (result.hasErrors()){
                List<ObjectError> errorList = result.getAllErrors();

                for(ObjectError error : errorList){
                    throw new Exception("输入参数不合规");
                }
            }
            menuService.add(menu);
            session.removeAttribute("parentMenuList");
            session.removeAttribute("enumeratorList");
            return "redirect:/menus";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "menu/add";
        }
    }

    @GetMapping("/menu/{id}")
    @RequiresPermissions("rest[menu:read]")
    public String toUpdatePage(@PathVariable Integer id, HttpSession session, Model model)
    {
        try {
            Menu menu = menuService.findByID(id);
            model.addAttribute("menu",menu);
            List<Menu> parentMenuList=menuService.findParentMenu();
            session.setAttribute("parentMenuList", parentMenuList);
            List<Enumerator> enumeratorList = enumeratorService.findByPara("menu","icon");
            session.setAttribute("enumeratorList", enumeratorList);
            return "menu/update";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PutMapping("/menu")
    @RequiresPermissions("rest[menu:put]")
    public String update(@Valid  Menu menu,BindingResult result,HttpSession session,Model model)
    {
        model.addAttribute("menu",menu);
        try {
            if (result.hasErrors()){
                List<ObjectError> errorList = result.getAllErrors();
                for(ObjectError error : errorList){
                    throw new Exception("输入参数不合规");
                }
            }
            menuService.update(menu);
            return "redirect:/menus";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "menu/update";
        }
    }

    @RequestMapping("/menuCheck")
    @ResponseBody
    public ResultMsg check(@RequestBody Map<String,String> map, Model model)
    {
        try {
            String menuName=map.get("menuName");
            if( menuService.findByName(menuName)==null) {
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

    @DeleteMapping("/menu/{id}")
    @RequiresPermissions("rest[menu:delete]")
    @ResponseBody
    public ResultMsg delete(@PathVariable Integer id)
    {
        try {
            menuService.deleteByID(id);
            return new ResultMsg(true, "删除成功");
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"删除失败");
        }
    }

    @RequestMapping("/findMenuStr/{id}")
    @ResponseBody
    public String findMenuStr(@PathVariable Integer id)
    {
        try {
            return menuService.findMenuStr(id);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
