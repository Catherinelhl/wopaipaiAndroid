package cn.wopaipai.bean;

import java.io.Serializable;

public class ContactDetailsBean implements Serializable {


    /**
     * id : 0
     * passportId : 0
     * name : string
     * address : string
     * coinCode : string
     */

    private int id;
    private int passportId;
    private String name;
    private String address;
    private String coinCode;

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

    @Override
    public String toString() {
        return "ContactDetailsBean{" +
                "id=" + id +
                ", passportId=" + passportId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", coinCode='" + coinCode + '\'' +
                '}';
    }
}
