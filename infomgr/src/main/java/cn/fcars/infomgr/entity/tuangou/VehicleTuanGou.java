package cn.fcars.infomgr.entity.tuangou;

import cn.fcars.infomgr.entity.basic.District;
import cn.fcars.infomgr.entity.basic.User;
import cn.fcars.infomgr.entity.vehicle.VehicleInfo;
import cn.fcars.infomgr.entity.vehicle.VehicleProduct;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class VehicleTuanGou {
    private String tgID;
    private int tgCount;
    private int tgYtCount;
    private String tgZt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tgFbDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tgJZDate;
    private VehicleProduct product;
    private User user;
    private District city;
    private String isDelete;

    public String getTgID() {
        return tgID;
    }

    public void setTgID(String tgID) {
        this.tgID = tgID;
    }

    public int getTgCount() {
        return tgCount;
    }

    public void setTgCount(int tgCount) {
        this.tgCount = tgCount;
    }

    public District getCity() {
        return city;
    }

    public void setCity(District city) {
        this.city = city;
    }

    public String getTgZt() {
        return tgZt;
    }

    public void setTgZt(String tgZt) {
        this.tgZt = tgZt;
    }

    public Date getTgFbDate() {
        return tgFbDate;
    }

    public void setTgFbDate(Date tgFbDate) {
        this.tgFbDate = tgFbDate;
    }

    public Date getTgJZDate() {
        return tgJZDate;
    }

    public void setTgJZDate(Date tgJZDate) {
        this.tgJZDate = tgJZDate;
    }

    public VehicleProduct getProduct() {
        return product;
    }

    public void setProduct(VehicleProduct product) {
        this.product = product;
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

    public int getTgYtCount() {
        return tgYtCount;
    }

    public void setTgYtCount(int tgYtCount) {
        this.tgYtCount = tgYtCount;
    }
}
