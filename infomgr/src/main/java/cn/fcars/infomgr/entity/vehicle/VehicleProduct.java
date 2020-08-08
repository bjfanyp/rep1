package cn.fcars.infomgr.entity.vehicle;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class VehicleProduct {
    private String PID;
    private int pCount;
    private int pKTCount;
    private int sellPrice;
    private int advancePrice;
    private String allowArea;
    private String zt;
    private VehicleInfo info;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fbDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date yxq;

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public int getpCount() {
        return pCount;
    }

    public void setpCount(int pCount) {
        this.pCount = pCount;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getAdvancePrice() {
        return advancePrice;
    }

    public void setAdvancePrice(int advancePrice) {
        this.advancePrice = advancePrice;
    }

    public String getAllowArea() {
        return allowArea;
    }

    public void setAllowArea(String allowArea) {
        this.allowArea = allowArea;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public VehicleInfo getInfo() {
        return info;
    }

    public void setInfo(VehicleInfo info) {
        this.info = info;
    }

    public Date getFbDate() {
        return fbDate;
    }

    public void setFbDate(Date fbDate) {
        this.fbDate = fbDate;
    }

    public Date getYxq() {
        return yxq;
    }

    public void setYxq(Date yxq) {
        this.yxq = yxq;
    }

    public int getpKTCount() {
        return pKTCount;
    }

    public void setpKTCount(int pKTCount) {
        this.pKTCount = pKTCount;
    }
}
