package cn.wopaipai.bean;

import java.io.Serializable;

public class GetCommunityUser implements Serializable {

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
}
