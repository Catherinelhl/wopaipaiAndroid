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
+ description  +  实体类：获取地区号码资源
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/
public class CountryCodeBean implements Serializable {
    public List<CountryCode> data;

    public class CountryCode implements Serializable {
        private String countryName;//城市名字
        private String countryPhone;//城市区号
        private String countryAbbr;//国家的地址

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryPhone() {
            return countryPhone;
        }

        public void setCountryPhone(String countryPhone) {
            this.countryPhone = countryPhone;
        }

        public String getCountryAbbr() {
            return countryAbbr;
        }

        public void setCountryAbbr(String countryAbbr) {
            this.countryAbbr = countryAbbr;
        }

        @Override
        public String toString() {
            return "CountryCode{" +
                    "countryName='" + countryName + '\'' +
                    ", countryPhone='" + countryPhone + '\'' +
                    ", countryAbbr='" + countryAbbr + '\'' +
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
