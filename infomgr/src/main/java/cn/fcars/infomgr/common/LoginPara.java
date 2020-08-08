package cn.fcars.infomgr.common;

import org.hibernate.validator.constraints.NotBlank;

public class LoginPara{

    @NotBlank(message = "不为空")
    private String userName;
    @NotBlank(message = "不为空")
    private String userPassword;
    @NotBlank(message = "不为空")
    private String yzm;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }
}
