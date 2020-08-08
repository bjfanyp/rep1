package cn.fcars.infomgr.entity.basic;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Enumerator {

    private String gnlb;
    private String dmlb;
    private String dmz;
    private String dmsx;
    private Integer sxh;
    private String zt;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date gxsj;


    public String getDmlb() {
        return dmlb;
    }

    public void setDmlb(String dmlb) {
        this.dmlb = dmlb;
    }

    public String getDmz() {
        return dmz;
    }

    public void setDmz(String dmz) {
        this.dmz = dmz;
    }

    public String getGnlb() {
        return gnlb;
    }

    public void setGnlb(String gnlb) {
        this.gnlb = gnlb;
    }

    public String getDmsx() {
        return dmsx;
    }

    public void setDmsx(String dmsx) {
        this.dmsx = dmsx;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }
}
