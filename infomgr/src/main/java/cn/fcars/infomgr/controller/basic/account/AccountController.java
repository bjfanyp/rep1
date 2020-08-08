package cn.fcars.infomgr.controller.basic.account;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.common.ResultMsg;
import cn.fcars.infomgr.entity.basic.Account;
import cn.fcars.infomgr.service.basic.AccountService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping("/accounts")
    @RequiresPermissions("accounts")
    public String list(BaseQuery accountQuery, Model model, HttpSession session)
    {
        session.setAttribute("currentMenu","accounts");
        Object object = session.getAttribute("accountQuery");
        if(null!=object&&null==accountQuery.getAccountName())
        {
            accountQuery=(BaseQuery)object;
        }
        try {
            PageInfo<Account> pageInfo = accountService.findByPage(accountQuery);
            session.setAttribute("accountQuery",accountQuery);
            model.addAttribute("pageInfo", pageInfo);
            return "account/list";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception","程序异常");
            return "forward:/error.jsp";
        }
    }

    @GetMapping("/account")
    @RequiresPermissions("rest[account:read]")
    public String toAddPage(HttpSession session, Model model)
    {
        try {
            return "account/add";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }

    @PostMapping("/account")
    @RequiresPermissions("rest[account:post]")
    public String add(@Valid Account account, BindingResult result, Model model)
    {
        List<ObjectError> errorList = result.getAllErrors();
        model.addAttribute("account",account);
        try {
            if (result.hasErrors()){
                for(ObjectError error : errorList){
                    throw new Exception("输入参数不合规");
                }
            }
            accountService.add(account);
            return "redirect:/accounts";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "account/add";
        }
    }


    @PostMapping("accounts/{id}")
    @ResponseBody
    public Account getAccount(@PathVariable Integer id)
    {
        return accountService.findByID(id);
    }


    @GetMapping("/account/{id}")
    @RequiresPermissions("rest[account:read]")
    public String toUpdatePage(@PathVariable Integer id,  Model model)
    {
        try {
            Account account = accountService.findByID(id);
            model.addAttribute("account",account);
            return "account/update";
        }
        catch (Exception e)
        {
            model.addAttribute("exception",e.getMessage());
            return "forward:/error.jsp";
        }
    }
    @PutMapping("/account")
    @RequiresPermissions("rest[account:put]")
    public String update(@Valid  Account account,BindingResult result,Model model)
    {
        model.addAttribute("account",account);
        List<ObjectError> errorList = result.getAllErrors();
        try {
            if (result.hasErrors()){
                for(ObjectError error : errorList){
                    throw new Exception("输入参数不合规");
                }
            }
            accountService.update(account);
            return "redirect:/accounts";
        }
        catch (Exception e)
        {
            String errorMsg=e.getMessage();
            if(errorMsg.length()>10)
            {
                errorMsg="程序异常，保存失败";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "account/update";
        }
    }

    @DeleteMapping("/account/{id}")
    @RequiresPermissions("rest[account:delete]")
    @ResponseBody
    public ResultMsg delete(@PathVariable Integer id)
    {
        try {
            accountService.deleteByID(id);
            return new ResultMsg(true, "删除成功");
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"删除失败");
        }
    }


    @RequestMapping("/accountCheck")
    @ResponseBody
    public ResultMsg check(@RequestBody Map<String,String> map, Model model)
    {
        try {
            String accountName=map.get("accountName");
            String accountType=map.get("accountType");
            if( accountService.findByPara(accountName,accountType)==null) {
                return new ResultMsg(true,"");
            }
            else {
                return new ResultMsg(false,"已有此账号");
            }
        }
        catch (Exception e)
        {
            return new ResultMsg(false,"数据异常");
        }
    }
}
