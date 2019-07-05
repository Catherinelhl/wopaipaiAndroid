package cn.wopaipai.bean.request;

import java.io.Serializable;
import java.util.List;

import cn.wopaipai.bean.GetWalletListBean;

public class GetWalletListRequestBean implements Serializable {

    /**
     * total : 0
     * data : [{"typeName":"string","createTime":"2019-06-22T06:59:52.320Z","amount":0}]
     * attachValue : {}
     */

    private int total;
    private List<GetWalletListBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GetWalletListBean>  getData() {
        return data;
    }

    public void setData(List<GetWalletListBean> data) {
        this.data = data;
    }

    public static class AttachValueBean implements Serializable{
    }


    @Override
    public String toString() {
        return "GetWalletListRequestBean{" +
                "total=" + total +
                ", data=" + data +
                '}';
    }
}
