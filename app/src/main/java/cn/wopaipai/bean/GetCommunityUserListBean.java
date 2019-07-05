package cn.wopaipai.bean;

import java.io.Serializable;
import java.util.List;

public class GetCommunityUserListBean implements Serializable {

    /**
     * total : 34
     * directPushUserList : [{"phoneNumber":"13724144831","levelName":"普通用户"},{"phoneNumber":"69356934","levelName":"普通用户"},{"phoneNumber":"18059229199","levelName":"普通用户"}]
     */

    private int total;
    private List<DirectPushUserListBean> directPushUserList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DirectPushUserListBean> getDirectPushUserList() {
        return directPushUserList;
    }

    public void setDirectPushUserList(List<DirectPushUserListBean> directPushUserList) {
        this.directPushUserList = directPushUserList;
    }

    public static class DirectPushUserListBean implements Serializable{
        /**
         * phoneNumber : 13724144831
         * levelName : 普通用户
         */

        private String phoneNumber;
        private String levelName;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        @Override
        public String toString() {
            return "DirectPushUserListBean{" +
                    "phoneNumber='" + phoneNumber + '\'' +
                    ", levelName='" + levelName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetCommunityUserListBean{" +
                "total=" + total +
                ", directPushUserList=" + directPushUserList +
                '}';
    }
}
