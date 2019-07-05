package cn.wopaipai.bean;

import java.io.Serializable;
import java.util.List;

public class WalletResponsesBean implements Serializable {

    /**
     * total : 58740
     * coins : [{"coinCode":"ZBB","balance":8900,"exUsdRate":1,"logoUrl":"http://image.myiauction.com/logo/ZBB.png"},{"coinCode":"BTC","balance":0,"exUsdRate":1,"logoUrl":"http://image.myiauction.com/logo/BTC.png"}]
     */

    private double total;
    private List<CoinsBean> coins;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<CoinsBean> getCoins() {
        return coins;
    }

    public void setCoins(List<CoinsBean> coins) {
        this.coins = coins;
    }
    @Override
    public String toString() {
        return "WalletResponsesBean{" +
                "total=" + total +
                ", coins=" + coins +
                '}';
    }
}
