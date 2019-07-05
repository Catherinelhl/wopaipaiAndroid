package cn.wopaipai.bean.request;

import java.io.Serializable;

public class ContactResponseBean implements Serializable {

    private int id;	//联系人Id

    private int passportId;	//integer($int32) 创建者的PassportId

    private String name;	//string maxLength: 50 姓名

    private String address;	//stringmaxLength: 100minLength: 0 钱包地址

    private String coinCode;	//string maxLength: 20  币种代码

    private String sign;	//string参数签名Sign=Md5Sign(xx参数)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "ContactResponseBean{" +
                "id=" + id +
                ", passportId=" + passportId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", coinCode='" + coinCode + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
