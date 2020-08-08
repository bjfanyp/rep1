package cn.fcars.infomgr.entity.basic;

/**
 * Created by fanyp on 2018/12/13.
 */
public class RolePerm {
    private Integer id;
    private Role role;
    private Perm perm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Perm getPerm() {
        return perm;
    }

    public void setPerm(Perm perm) {
        this.perm = perm;
    }
}
