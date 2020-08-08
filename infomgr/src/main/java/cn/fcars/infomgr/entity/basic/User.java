package cn.fcars.infomgr.entity.basic;

import cn.fcars.infomgr.jsr303.IsMobile;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by fanyp on 2018/11/25.
 */
public class User {

    private Integer userID;
    @NotNull(message="不为空")
    @Size(min = 2, max = 15,message="长度范围2-15")
    private String userName;
    @NotNull(message="密码不为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$",message = "密码长度大于6位，由数字和字母组成，不含特殊字符")
    private String userPassword;
    @NotNull(message="不为空")
    @Size(min = 2, max = 15,message="长度范围2-15")
    private String userTrueName;
    @IsMobile(required = false)
    private String userTel;
    private Integer userErrorCount;
    private String userStatus;
    private String isDelete;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userEffective;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private Dep dep;
    public User() {
    }

    public User(Integer userID, String userName, String userPassword, String userTrueName, String userTel, Integer userErrorCount, String userStatus, String isDelete, Date userEffective, Date createTime, Date updateTime, Dep dep, Account account) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userTrueName = userTrueName;
        this.userTel = userTel;
        this.userErrorCount = userErrorCount;
        this.userStatus = userStatus;
        this.isDelete = isDelete;
        this.userEffective = userEffective;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.dep = dep;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userId) {
        this.userID = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserTrueName() {
        return userTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        this.userTrueName = userTrueName;
    }

    public Integer getUserErrorCount() {
        return userErrorCount;
    }

    public void setUserErrorCount(Integer userErrorCount) {
        this.userErrorCount = userErrorCount;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }


    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Date getUserEffective() {
        return userEffective;
    }

    public void setUserEffective(Date userEffective) {
        this.userEffective = userEffective;
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

    public Dep getDep() {
        return dep;
    }

    public void setDep(Dep dep) {
        this.dep = dep;
    }
}
