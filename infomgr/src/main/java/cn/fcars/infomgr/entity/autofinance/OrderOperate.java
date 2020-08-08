package cn.fcars.infomgr.entity.autofinance;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.Date;

/**
 * Created by fanyp on 2018/12/5.
 */
public class OrderOperate {
    private Integer id;
    private String orderZt;
    private double collect;
    @DecimalMax(value = "400000",message = "不高于40万")
    private double loan;
    private Date createTime;
    private Date updateTime;
    private Order order;

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

    public double getCollect() {
        return collect;
    }

    public void setCollect(double collect) {
        this.collect = collect;
    }

    public double getLoan() {
        return loan;
    }

    public void setLoan(double loan) {
        this.loan = loan;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
