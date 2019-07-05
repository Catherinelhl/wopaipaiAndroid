package cn.wopaipai.bean.request;

import java.io.Serializable;

public class GetAddressRequestBean implements Serializable {

    /**
     * data : 174zhi9ce1861uApvWw1czDvsCVyyBVVeb
     * statusCode : 200
     * message : null
     */

    private String data;
    private int statusCode;
    private Object message;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
