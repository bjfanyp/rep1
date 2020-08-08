package cn.fcars.infomgr.common;
/**
 * Created by fanyp on 2018/12/3.
 */
public class ResultData<T> {
    private boolean code;
    private T data;
    private String msg;

    public ResultData() {
    }

    public ResultData(boolean code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
