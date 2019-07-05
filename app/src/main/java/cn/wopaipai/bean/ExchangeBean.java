package cn.wopaipai.bean;

public class ExchangeBean {
    /**
     * coinCode : ZBB
     * balance : 1663
     * exUsdRate : 9.99
     * logoUrl : null
     */

    private String coinCode;
    private double balance;
    private double exUsdRate;
    private Object logoUrl;

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getExUsdRate() {
        return exUsdRate;
    }

    public void setExUsdRate(double exUsdRate) {
        this.exUsdRate = exUsdRate;
    }

    public Object getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(Object logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return "ExchangeBean{" +
                "coinCode='" + coinCode + '\'' +
                ", balance=" + balance +
                ", exUsdRate=" + exUsdRate +
                ", logoUrl=" + logoUrl +
                '}';
    }
}
