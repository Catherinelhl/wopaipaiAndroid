package cn.wopaipai.bean.request;

import java.io.Serializable;

public class ExchangeZBBRequestBean implements Serializable {

    private int passportId;	//

    private String inputCoinCode;	// 输入币种

    private double inputAmount;	// 输入数量

    private String outputCoinCode;	// 输出币种【目前只能换ZBB】

    private String tradePassword;	// 交易密码【传过来加个后缀比如：123123.x2019 其中.x2019是后缀】

    private String sign;	// string参数签名Sign=Md5Sign(xx参数)

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public String getInputCoinCode() {
        return inputCoinCode;
    }

    public void setInputCoinCode(String inputCoinCode) {
        this.inputCoinCode = inputCoinCode;
    }

    public double getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(double inputAmount) {
        this.inputAmount = inputAmount;
    }

    public String getOutputCoinCode() {
        return outputCoinCode;
    }

    public void setOutputCoinCode(String outputCoinCode) {
        this.outputCoinCode = outputCoinCode;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "ExchangeZBBRequestBean{" +
                "passportId=" + passportId +
                ", inputCoinCode='" + inputCoinCode + '\'' +
                ", inputAmount=" + inputAmount +
                ", outputCoinCode='" + outputCoinCode + '\'' +
                ", tradePassword='" + tradePassword + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
