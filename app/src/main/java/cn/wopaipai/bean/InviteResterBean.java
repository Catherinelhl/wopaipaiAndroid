package cn.wopaipai.bean;

import java.io.Serializable;

public class InviteResterBean implements Serializable {

    /**
     * pCode : 4I9NMX
     * qrRegisterUrl : http://www.myiauction.com/register?pcode=4I9NMX
     * remark : 扫描上面二维码，注册后下载使用
     */

    private String pCode;
    private String qrRegisterUrl;
    private String remark;

    public String getPCode() {
        return pCode;
    }

    public void setPCode(String pCode) {
        this.pCode = pCode;
    }

    public String getQrRegisterUrl() {
        return qrRegisterUrl;
    }

    public void setQrRegisterUrl(String qrRegisterUrl) {
        this.qrRegisterUrl = qrRegisterUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "InviteResterBean{" +
                "pCode='" + pCode + '\'' +
                ", qrRegisterUrl='" + qrRegisterUrl + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
