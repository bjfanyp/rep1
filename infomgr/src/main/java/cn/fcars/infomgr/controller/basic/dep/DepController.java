package cn.fcars.infomgr.controller.basic.dep;

import cn.fcars.infomgr.common.*;
import cn.fcars.infomgr.entity.basic.*;
import cn.fcars.infomgr.service.basic.AccountService;
import cn.fcars.infomgr.service.basic.DepService;
import cn.fcars.infomgr.service.basic.MaxNoService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class DepController {

    @Autowired
    DepService depService;
    @Autowired
    AccountService accountService;
    @Autowired
    MaxNoService maxNoService;

    @RequestMapping("/deps")
    @RequiresPermissions("deps")
    public String list(BaseQuery depQuery, Model model, HttpSession session)
    {
        session.setAttribute("currentMenu","deps");
        Object object =session.getAttribute("depQuery");
        if(null!=object&&null==depQuery.getDepType())
        {
            depQuery=(BaseQuery)object;
        }
        try {
            PageInfo<Dep> pageInfo = depService.findByPage(depQuery);
            model.addAttribute("pageInfo", pageInfo);
            session.setAttribute("depQuery",depQuery);
            session.removeAttribute("accountList");
            return "dep/list";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }


    @GetMapping("/dep")
    @RequiresPermissions("rest[dep:read]")
    public String toAddPage(HttpSession session)
    {
        BaseQuery accountQuery=new BaseQuery();
        accountQuery.setAccountType("0");
        List<Account> accountList = accountService.findByQuery(accountQuery);
        session.setAttribute("accountList", accountList);
        return  "dep/add";
    }

    @PostMapping("/dep")
    @RequiresPermissions("rest[dep:post]")
    public String add(@Valid Dep dep, BindingResult result, HttpSession session,Model model)
    {
        model.addAttribute("dep",dep);
        try {
            if (result.hasErrors()){
                List<ObjectError> errorList = result.getAllErrors();
                for(ObjectError error : errorList){
                    if(!error.getDefaultMessage().contains("服务费"))
                    {
                        throw new Exception("输入参数不合规");
                    }
                    else if(error.getDefaultMessage().contains("服务费")&& !StringUtils.isEmpty(dep.getDepQdfwf()))
                    {
                        throw new Exception("输入参数不合规");
                    }
                }
            }
            depService.add(dep);
            session.removeAttribute("accountList");
            return "redirect:/deps";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "dep/add";
        }
    }

    @RequestMapping("/dep/{id}")
    @RequiresPermissions("rest[dep:read]")
    public String toUpdatePage(@PathVariable Integer id,  HttpSession session,Model model)
    {
        try {
            Dep dep = depService.findByID(id);
            model.addAttribute("dep",dep);
            List<Account> accountList = accountService.findAll();
            session.setAttribute("accountList", accountList);
            return "dep/update";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }

    @PutMapping("/dep")
    @RequiresPermissions("rest[dep:put]")
    public String update(@Valid  Dep dep,BindingResult result,HttpSession session ,Model model)
    {
        model.addAttribute("dep",dep);
        try {
            if (result.hasErrors()){
                List<ObjectError> errorList = result.getAllErrors();
                for(ObjectError error : errorList){
                    if(!error.getDefaultMessage().contains("服务费"))
                    {
                        throw new Exception("输入参数不合规");
                    }
                    else if(error.getDefaultMessage().contains("服务费")&& !StringUtils.isEmpty(dep.getDepQdfwf()))
                    {
                        throw new Exception("输入参数不合规");
                    }
                }
            }
            depService.update(dep);
            session.removeAttribute("accountList");
            return "redirect:/deps";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "dep/update";
        }
    }
    @DeleteMapping("/dep/{id}")
    @RequiresPermissions("rest[dep:delete]")
    @ResponseBody
    public ResultMsg delete(@PathVariable Integer id)
    {
        try {
            depService.deleteByID(id);
            return new ResultMsg(true, "删除成功");
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"删除失败");
        }
    }
    @RequestMapping("/depCheck")
    @ResponseBody
    public ResultMsg check(@RequestBody Map<String,String> map, Model model)
    {
        try {
            String depName=map.get("depName");
            Integer check= depService.check(depName);
            if(check==0) {
                return new ResultMsg(true,"");
            }
            else {
                return new ResultMsg(false,"已有此部门名称");
            }
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"数据异常");
        }
    }
}
