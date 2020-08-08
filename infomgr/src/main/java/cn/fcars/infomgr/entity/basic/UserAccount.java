package cn.fcars.infomgr.entity.basic;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by fanyp on 2018/11/25.
 */
public class UserAccount {
    private String id;
    private User user;
    private Account account;
    private String isDelete;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
