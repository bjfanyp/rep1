package cn.fcars.infomgr.entity.basic;

/**
 * Created by fanyp on 2018/12/13.
 */
public class UserRole {
    private Integer id;
    private User User;
    private Role Role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public cn.fcars.infomgr.entity.basic.User getUser() {
        return User;
    }

    public void setUser(cn.fcars.infomgr.entity.basic.User user) {
        User = user;
    }

    public cn.fcars.infomgr.entity.basic.Role getRole() {
        return Role;
    }

    public void setRole(cn.fcars.infomgr.entity.basic.Role role) {
        Role = role;
    }
}
