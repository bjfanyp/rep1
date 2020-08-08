package cn.fcars.infomgr.entity.basic;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by fanyp on 2018/11/25.
 */
public class Pic {
    private Integer picID;
    @NotNull(message = "不为空")
    @Size(max = 20,message = "长度不超过20")
    private String picName;
    private String picType;
    @NotNull(message = "不为空")
    @Size(max = 50,message = "长度不超过50")
    private String picUrl;
    @NotNull(message = "不为空")
    @Size(max = 50,message = "长度不超过50")
    private String picThumbUrl;

    public Integer getPicID() {
        return picID;
    }

    public void setPicID(Integer picID) {
        this.picID = picID;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicThumbUrl() {
        return picThumbUrl;
    }

    public void setPicThumbUrl(String picThumbUrl) {
        this.picThumbUrl = picThumbUrl;
    }
}
