package cn.wopaipai.bean;

public class CoinsBean {
    /**
     * coinCode : ZBB
     * balance : 8900
     * exUsdRate : 1
     * logoUrl : http://image.myiauction.com/logo/ZBB.png
     */
    private String coinCode;
    private double balance;
    private double exUsdRate;
    private String logoUrl;

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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return "CoinsBean{" +
                "coinCode='" + coinCode + '\'' +
                ", balance=" + balance +
                ", exUsdRate=" + exUsdRate +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}
