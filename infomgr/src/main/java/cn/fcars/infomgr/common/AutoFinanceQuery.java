package cn.fcars.infomgr.common;

/**
 * Created by fanyp on 2018/12/2.
 */
public class AutoFinanceQuery {
    private String  rbID;
    private String  orderZt;
    private Integer  depID;
    private Integer userID;
    private Integer pageNum;
    private String orderID;
    private String picType;

    public String getRbID() {
        return rbID;
    }

    public void setRbID(String rbID) {
        this.rbID = rbID;
    }

    public String getOrderZt() {
        return orderZt;
    }

    public void setOrderZt(String orderZt) {
        this.orderZt = orderZt;
    }

    public Integer getDepID() {
        return depID;
    }

    public void setDepID(Integer depID) {
        this.depID = depID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }
}
