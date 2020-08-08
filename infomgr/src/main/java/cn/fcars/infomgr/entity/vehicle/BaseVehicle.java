package cn.fcars.infomgr.entity.vehicle;

import cn.fcars.infomgr.entity.basic.Account;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


public class BaseVehicle {
    private String baseID;
    private String baseContent;
    private String baseZt;
    private String parentBaseID;
    private String baseImageUrl;
    private String baseFirstLetter;
    private String baseOrder;
    public BaseVehicle() {
    }

    public String getBaseID() {
        return baseID;
    }

    public void setBaseID(String baseID) {
        this.baseID = baseID;
    }

    public String getBaseContent() {
        return baseContent;
    }

    public void setBaseContent(String baseContent) {
        this.baseContent = baseContent;
    }

    public String getBaseZt() {
        return baseZt;
    }

    public void setBaseZt(String baseZt) {
        this.baseZt = baseZt;
    }

    public String getParentBaseID() {
        return parentBaseID;
    }

    public void setParentBaseID(String parentBaseID) {
        this.parentBaseID = parentBaseID;
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public String getBaseFirstLetter() {
        return baseFirstLetter;
    }

    public void setBaseFirstLetter(String baseFirstLetter) {
        this.baseFirstLetter = baseFirstLetter;
    }

    public String getBaseOrder() {
        return baseOrder;
    }

    public void setBaseOrder(String baseOrder) {
        this.baseOrder = baseOrder;
    }
}
