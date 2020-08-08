package cn.fcars.infomgr.controller.gprs;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class GprsController {
    @RequestMapping("gprs1")
    @RequiresPermissions("gprs1")
    public String gprs1(HttpSession session)
    {
        session.setAttribute("currentMenu","gprs1");
        return "gprs/gprs1";
    }
    @RequestMapping("gprs2")
    @RequiresPermissions("gprs2")
    public String gprs2(HttpSession session)
    {
        session.setAttribute("currentMenu","gprs2");
        return "gprs/gprs2";
    }
}
