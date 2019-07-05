package cn.wopaipai.bean;

import java.io.Serializable;
import java.util.List;

public class WalletExchangeBean implements Serializable {

    /**
     * coins : [{"coinCode":"ZBB","balance":1663,"exUsdRate":9.99,"logoUrl":null},{"coinCode":"BTC","balance":0,"exUsdRate":9.99,"logoUrl":null}]
     * exchangeRate : 9.99
     */

    private double exchangeRate;
    private List<CoinsBean> coins;

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public List<CoinsBean> getCoins() {
        return coins;
    }

    public void setCoins(List<CoinsBean> coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "WalletExchangeBean{" +
                "exchangeRate=" + exchangeRate +
                ", coins=" + coins +
                '}';
    }
}
