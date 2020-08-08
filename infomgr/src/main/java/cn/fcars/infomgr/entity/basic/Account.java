package cn.fcars.infomgr.entity.basic;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by fanyp on 2018/11/25.
 */
public class Account {
    private Integer accountID;
    @NotNull(message = "不能为空")
    @Size(max=30,message = "长度不超过30")
    private String accountName;
    private String accountType;
    @NotNull(message = "不能为空")
    @Size(max=30,message = "长度不超过30")
    private String accountBank;
    @NotNull(message = "不能为空")
    @Size(max=20,message ="长度不超过20")
    private String accountNumber;
    private String isDelete;

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
