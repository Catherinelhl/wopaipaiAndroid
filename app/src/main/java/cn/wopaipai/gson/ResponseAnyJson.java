package cn.wopaipai.gson;

import java.io.Serializable;

public class ResponseAnyJson<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    // 信息
    private String message;
    // 狀態碼
    private int statusCode;
    // data 数据
    private T data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseAnyJson{" +
                "message='" + message + '\'' +
                ", statusCode=" + statusCode +
                ", data=" + data +
                '}';
    }
}
