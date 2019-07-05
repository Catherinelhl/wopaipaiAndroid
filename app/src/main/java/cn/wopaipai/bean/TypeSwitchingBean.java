package cn.wopaipai.bean;


import java.io.Serializable;

/**
 * @author catherine.brainwilliam
 * @since 2018/8/16
 * <p>
 * 「語言切換」、「TV版幣種切換」數據類
 */
public class TypeSwitchingBean implements Serializable {
    private String language;
    private String type;
    private boolean isChoose;

    //供幣種選擇調用
    public TypeSwitchingBean(String language, boolean isChoose) {
        super();
        this.isChoose = isChoose;
        this.type = language;
        this.language = language;
    }

    public TypeSwitchingBean(String language, String type, boolean isChoose) {
        super();
        this.isChoose = isChoose;
        this.type = type;
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "TypeSwitchingBean{" +
                "type='" + type + '\'' +
                ", isChoose=" + isChoose +
                ", language='" + language + '\'' +
                '}';
    }
}
