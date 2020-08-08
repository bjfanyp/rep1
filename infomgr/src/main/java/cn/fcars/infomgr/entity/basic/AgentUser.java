package cn.fcars.infomgr.entity.basic;

import java.util.Date;

public class AgentUser {
    private String id;

    private String agentUserName;//手机号id

    private String agentPassword;//密码

    private String agentRealName;//用户姓名

    private String agentSex;//性别

    private String agentAge;//年龄

    private Integer regionId;//地区id

    private String agentPhoto;//头像

    private String agentStatus;//审核状态

    private String agentRemark;//审核结果明细

    private Date createdTime;//添加时间

    private String referralCode;//一级经纪人id
    
    private String deviceId;//设备id
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgentUserName() {
        return agentUserName;
    }

    public void setAgentUserName(String agentUserName) {
        this.agentUserName = agentUserName == null ? null : agentUserName.trim();
    }

    public String getAgentPassword() {
        return agentPassword;
    }

    public void setAgentPassword(String agentPassword) {
        this.agentPassword = agentPassword == null ? null : agentPassword.trim();
    }

    public String getAgentRealName() {
        return agentRealName;
    }

    public void setAgentRealName(String agentRealName) {
        this.agentRealName = agentRealName == null ? null : agentRealName.trim();
    }

    public String getAgentSex() {
        return agentSex;
    }

    public void setAgentSex(String agentSex) {
        this.agentSex = agentSex == null ? null : agentSex.trim();
    }

    public String getAgentAge() {
        return agentAge;
    }

    public void setAgentAge(String agentAge) {
        this.agentAge = agentAge == null ? null : agentAge.trim();
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getAgentPhoto() {
        return agentPhoto;
    }

    public void setAgentPhoto(String agentPhoto) {
        this.agentPhoto = agentPhoto == null ? null : agentPhoto.trim();
    }

    public String getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(String agentStatus) {
        this.agentStatus = agentStatus == null ? null : agentStatus.trim();
    }

    public String getAgentRemark() {
        return agentRemark;
    }

    public void setAgentRemark(String agentRemark) {
        this.agentRemark = agentRemark == null ? null : agentRemark.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode == null ? null : referralCode.trim();
    }

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}