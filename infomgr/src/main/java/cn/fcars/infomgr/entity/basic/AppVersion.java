package cn.fcars.infomgr.entity.basic;

import java.util.Date;

/**
 * app版本管理
 * @author zp
 *
 */
public class AppVersion {
    private String id;

    private String appId;

    private String appVersion;

    private String appName;

    private String appDownloadUrl;

    private Date createTime;

    private Date updateTime;

    private String isDelete;

    private String remark;

    private String appVersionImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion == null ? null : appVersion.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppDownloadUrl() {
        return appDownloadUrl;
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl == null ? null : appDownloadUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createdTime) {
        this.createTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }
   
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
    public String getAppVersionImage() {
        return appVersionImage;
    }

    public void setAppVersionImage(String appVersionImage) {
        this.appVersionImage = appVersionImage == null ? null : appVersionImage.trim();
    }
}