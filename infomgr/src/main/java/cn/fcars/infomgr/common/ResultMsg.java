package cn.fcars.infomgr.common;

/**
 * Created by fanyp on 2018/11/29.
 */
public class ResultMsg {
    public boolean code;
    public String msg;

    public ResultMsg(boolean code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
