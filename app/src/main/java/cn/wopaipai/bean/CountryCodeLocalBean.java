package cn.wopaipai.bean;

import java.io.Serializable;
import java.util.List;


/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 17:48
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.bean
+--------------+---------------------------------
+ description  +  实体类：获取本地地区号码资源
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/
public class CountryCodeLocalBean implements Serializable {
    public List<CountryCode> data;

    public class CountryCode implements Serializable {
        private String countryName;//城市名字
        private String countryPinyin;//城市名字的拼音
        private String phoneCode;
        private String countryCode;

        private String countryPhone;//城市区号
        private String countryAbbr;//国家的地址

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryPinyin() {
            return countryPinyin;
        }

        public void setCountryPinyin(String countryPinyin) {
            this.countryPinyin = countryPinyin;
        }

        public String getPhoneCode() {
            return phoneCode;
        }

        public void setPhoneCode(String phoneCode) {
            this.phoneCode = phoneCode;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }


        @Override
        public String toString() {
            return "CountryCode{" +
                    "countryName='" + countryName + '\'' +
                    ", countryPinyin='" + countryPinyin + '\'' +
                    ", phoneCode='" + phoneCode + '\'' +
                    ", countryCode='" + countryCode + '\'' +
                    '}';
        }
    }

    public List<CountryCode> getData() {
        return data;
    }

    public void setData(List<CountryCode> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CountryCodeLocalBean{" +
                "data=" + data +
                '}';
    }
}
