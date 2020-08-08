package cn.fcars.infomgr.common;

/**
 * Created by fanyp on 2018/12/2.
 */
public class BaseQuery {
    private String userName;
    private Integer depID;
    private Integer pageNum;

    private String depName;
    private String depType;

    private String accountName;
    private String accountType;

    private String menuName;
    private String menuParentID;

    private String permName;
    private String menuID;

    private String roleName;

    private String pinPai;

    private String cheXing;

    private String pinPaiID;

    private String cheXingID;

    private String cheKuanID;

    private String colorCode;

    private String orderZt;
    private String appID;
    private int userID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getOrderZt() {
        return orderZt;
    }

    public void setOrderZt(String orderZt) {
        this.orderZt = orderZt;
    }

    public String getPinPai() {
        return pinPai;
    }

    public void setPinPai(String pinPai) {
        this.pinPai = pinPai;
    }

    public String getCheXing() {
        return cheXing;
    }

    public void setCheXing(String cheXing) {
        this.cheXing = cheXing;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getDepID() {
        return depID;
    }

    public void setDepID(Integer depID) {
        this.depID = depID;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepType() {
        return depType;
    }

    public void setDepType(String depType) {
        this.depType = depType;
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuParentID() {
        return menuParentID;
    }

    public void setMenuParentID(String menuParentID) {
        this.menuParentID = menuParentID;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPinPaiID() {
        return pinPaiID;
    }

    public void setPinPaiID(String pinPaiID) {
        this.pinPaiID = pinPaiID;
    }

    public String getCheXingID() {
        return cheXingID;
    }

    public void setCheXingID(String cheXingID) {
        this.cheXingID = cheXingID;
    }

    public String getCheKuanID() {
        return cheKuanID;
    }

    public void setCheKuanID(String cheKuanID) {
        this.cheKuanID = cheKuanID;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }
}
