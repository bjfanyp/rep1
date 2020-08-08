package cn.fcars.infomgr.entity.basic;

import javax.validation.constraints.*;
import java.util.Date;
/**
 * Created by fanyp on 2018/11/25.
 */
public class Dep {
    private Integer depID;
    @NotNull(message="部门名称不为空")
    @Size(min = 2, max = 15,message="部门名称长度范围2-20")
    private String depName;
    private String depType;
    private String isDelete;
    @DecimalMax(value = "100",message = "小于100")
    @DecimalMin(value = "0",message = "大于0")
    private double depQdfwf;
    private Date createTime;
    private Date updateTime;
    private Account account;

    public Dep() {
    }

    public Integer getDepID() {
        return depID;
    }

    public void setDepID(Integer depID) {
        this.depID = depID;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepType() {
        return depType;
    }

    public void setDepType(String depType) {
        this.depType = depType;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public double getDepQdfwf() {
        return depQdfwf;
    }

    public void setDepQdfwf(double depQdfwf) {
        this.depQdfwf = depQdfwf;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
