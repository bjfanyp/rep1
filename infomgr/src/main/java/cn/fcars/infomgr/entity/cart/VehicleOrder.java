package cn.fcars.infomgr.entity.cart;

import cn.fcars.infomgr.entity.basic.District;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class VehicleOrder {
    private String id;
    private int count;
    private int totalAdvance;
    private int totalPrice;
    private String zt;
    private String ztDes;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private VehicleTuanGou tuanGou;
    private User user;
    private String isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalAdvance() {
        return totalAdvance;
    }

    public void setTotalAdvance(int totalAdvance) {
        this.totalAdvance = totalAdvance;
    }


    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
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

    public VehicleTuanGou getTuanGou() {
        return tuanGou;
    }

    public void setTuanGou(VehicleTuanGou tuanGou) {
        this.tuanGou = tuanGou;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getZtDes() {
        return ztDes;
    }

    public void setZtDes(String ztDes) {
        this.ztDes = ztDes;
    }
}
