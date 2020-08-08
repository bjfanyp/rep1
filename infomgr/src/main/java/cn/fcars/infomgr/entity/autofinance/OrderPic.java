package cn.fcars.infomgr.entity.autofinance;

import cn.fcars.infomgr.entity.basic.Pic;
import cn.fcars.infomgr.entity.basic.User;

import javax.validation.constraints.*;

/**
 * Created by fanyp on 2018/11/25.
 */
public class OrderPic {
    private Integer id;
    private String orderZt;
    private Order order;
    private Pic pic;

    public OrderPic() {
    }

    public OrderPic(Integer id, String orderZt, Order order, Pic pic) {
        this.id = id;
        this.orderZt = orderZt;
        this.order = order;
        this.pic = pic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderZt() {
        return orderZt;
    }

    public void setOrderZt(String orderZt) {
        this.orderZt = orderZt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Pic getPic() {
        return pic;
    }

    public void setPic(Pic pic) {
        this.pic = pic;
    }
}
