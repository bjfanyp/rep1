package cn.fcars.infomgr.entity.basic;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created by fanyp on 2018/12/11.
 */
public class Menu {
    private Integer menuID;
    @NotNull(message = "不能为空")
    @Size(max = 30,message = "长度不能超过30")
    private String menuName;
    private Menu parentMenu;
    @Size(max = 50,message = "长度不能超过50")
    private String menuUrl;
    @NotNull(message = "不能为空")
    @Range(min=1,max = 1000,message = "不能超过1000")
    private Integer menuSxh;
    private String menuIcon;
    private String isDelete;
    private Date createTime;
    private Date updateTime;
    private List<Perm> permList;

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public List<Perm> getPermList() {
        return permList;
    }

    public void setPermList(List<Perm> permList) {
        this.permList = permList;
    }

    public Integer getMenuID() {
        return menuID;
    }

    public void setMenuID(Integer menuID) {
        this.menuID = menuID;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getMenuSxh() {
        return menuSxh;
    }

    public void setMenuSxh(Integer menuSxh) {
        this.menuSxh = menuSxh;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
