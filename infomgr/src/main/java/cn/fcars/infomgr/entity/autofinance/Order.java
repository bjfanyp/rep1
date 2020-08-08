package cn.fcars.infomgr.entity.autofinance;

import cn.fcars.infomgr.entity.basic.User;

import javax.validation.constraints.*;

/**
 * Created by fanyp on 2018/11/25.
 */
public class Order {
    private String orderID;
    @NotNull(message = "不为空")
    @Size(max = 30,message = "长度不超过30")
    private String rbID;
    @NotNull(message = "不为空")
    @Size(max = 10,message = "长度不超过10")
    private String orderCustom;
    @Pattern(regexp = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$",message = "身份证号码不正确")
    private String orderCustomID;
    @DecimalMax(value = "200000",message = "首付不高于20万")
    private Double orderPrepay;
    @DecimalMax(value = "400000",message = "贷款不高于40万")
    @DecimalMin(value = "5000",message = "贷款不低于5千")
    private Double orderLoanvalue;
    private Double orderPins;
    private Double orderYwins;
    private Double orderSerpay;
    private Double orderRisk;
    private Double orderConsloan;
    private Double orderFktotal;
    private String orderSfdk;
    private String orderZt;
    private String isDelete;
    private User user;

    public String getOrderZt() {
        return orderZt;
    }

    public void setOrderZt(String orderZt) {
        this.orderZt = orderZt;
    }
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getRbID() {
        return rbID;
    }

    public void setRbID(String rbID) {
        this.rbID = rbID;
    }

    public String getOrderCustom() {
        return orderCustom;
    }

    public void setOrderCustom(String orderCustom) {
        this.orderCustom = orderCustom;
    }

    public String getOrderCustomID() {
        return orderCustomID;
    }

    public void setOrderCustomID(String orderCustomID) {
        this.orderCustomID = orderCustomID;
    }

    public Double getOrderPrepay() {
        return orderPrepay;
    }

    public void setOrderPrepay(Double orderPrepay) {
        this.orderPrepay = orderPrepay;
    }

    public Double getOrderLoanvalue() {
        return orderLoanvalue;
    }

    public void setOrderLoanvalue(Double orderLoanvalue) {
        this.orderLoanvalue = orderLoanvalue;
    }

    public Double getOrderPins() {
        return orderPins;
    }

    public void setOrderPins(Double orderPins) {
        this.orderPins = orderPins;
    }

    public Double getOrderYwins() {
        return orderYwins;
    }

    public void setOrderYwins(Double orderYwins) {
        this.orderYwins = orderYwins;
    }

    public Double getOrderSerpay() {
        return orderSerpay;
    }

    public void setOrderSerpay(Double orderSerpay) {
        this.orderSerpay = orderSerpay;
    }

    public Double getOrderRisk() {
        return orderRisk;
    }

    public void setOrderRisk(Double orderRisk) {
        this.orderRisk = orderRisk;
    }

    public Double getOrderConsloan() {
        return orderConsloan;
    }

    public void setOrderConsloan(Double orderConsloan) {
        this.orderConsloan = orderConsloan;
    }

    public Double getOrderFktotal() {
        return orderFktotal;
    }

    public void setOrderFktotal(Double orderFktotal) {
        this.orderFktotal = orderFktotal;
    }

    public String getOrderSfdk() {
        return orderSfdk;
    }

    public void setOrderSfdk(String orderSfdk) {
        this.orderSfdk = orderSfdk;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
