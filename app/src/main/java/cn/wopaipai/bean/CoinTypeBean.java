package cn.wopaipai.bean;

import java.io.Serializable;

// 可用币种
public class CoinTypeBean implements Serializable {
    /**
     * id : 1
     * coinCode : ZBB
     * coinName : ZBB
     */
    private int id;
    private String coinCode;
    private String coinName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    @Override
    public String toString() {
        return "CoinTypeBean{" +
                "id=" + id +
                ", coinCode='" + coinCode + '\'' +
                ", coinName='" + coinName + '\'' +
                '}';
    }
}
