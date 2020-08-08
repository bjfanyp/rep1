package cn.fcars.infomgr.entity.basic;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * Created by fanyp on 2018/12/13.
 */
public class Perm {
    private Integer permID ;
    @NotNull(message = "不为空")
    @Size(max = 20,message = "长度不超过20")
    private String permName;
    @NotNull(message = "不为空")
    @Size(max = 30,message = "长度不超过30")
    private String permToken;
    @Range(min=1,max = 99999,message = "99999")
    private Integer permSxh;
    private Menu menu;

    public Integer getPermSxh() {
        return permSxh;
    }
    public void setPermSxh(Integer permSxh) {
        this.permSxh = permSxh;
    }

    public Integer getPermID() {
        return permID;
    }

    public void setPermID(Integer permID) {
        this.permID = permID;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getPermToken() {
        return permToken;
    }

    public void setPermToken(String permToken) {
        this.permToken = permToken;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
