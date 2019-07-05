package cn.wopaipai.constant

import cn.wopaipai.tool.StringTool

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 11:44
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.Constant
+--------------+---------------------------------
+ description  +   常数类：定义一些数据传输，表明type的key or values
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object Constants {

    /**
     * 用户等级【0 普通用户，1 Vip，8 经理，9 董事
     * */
    object Level {
        const val NORMAL = 0
        const val VIP = 1
        const val MANAGER = 8
        const val DIRECTOR = 9

    }


    const val LOCAL_DEFAULT_IP = "0.0.0.0"
    const val SYMBOL_DOT = "\\."
    const val PACKAGE = "cn.wopaipai"
    const val NAME = "/wopai/"
    const val APPNAME = "Auction.apk"
    const val BlackDot = '●'

    /*相关的Key常数*/
    object KeyMaps {
        const val REGISTER_CODE: String = "registerCode"//注册
        const val FROM_LOGIN: String = "fromLogin"//是否是来自登录
        const val USER_INFO: String = "userInfo"//用户信息
        const val AUCTION_ACQUISITION: String = "auctionAcquisition"//竞拍的物品信息
        const val HotCommodities: String = "HotCommodities"
        const val From: String = "from"
        const val SELECT_COUNTRY_CODE = "selectCountryCode"//选择的城市号码
        const val AUCTION_COMMODITY_INFO = "auctionCommodityInfo"//竞拍物品信息

    }

    object TabLayout {
        const val SLIDING_TAB_INDICATOR = "slidingTabIndicator"
    }

    /*相关的Value常数*/
    object ValueMaps {
        const val MORNING = "上午"
        const val AFTERROON = "下午"
        const val AM = "AM"
        const val PM = "PM"
        const val REGISTER: String = "register"
        const val PASSWORD_MIN_LENGTH: Int = 8//密码的最小长度
        const val ZH_CN = "zh-cn"//中文（简体）
        const val ZH_TW = "zh-tw"//繁中
        const val EN_US = "zh-cn"//英文

        const val FROM_LANGUAGE_SWITCH = "languageSwitch"

    }

    const val ENCODE_INGORE_CASE = "identity"//http設置encode忽略

    /*sp存储字段信息*/
    object Preference {
        const val USER_INFO: String = "userInfo"
        //用户信息

        fun SPNAME(): String {
            return if (StringTool.equals(
                    HostURLConstants.SERVER_TYPE,
                    MessageConstants.ServerType.PRD
                )
            ) "wopaipaiAndroid" else "testWopaipaiAndroid"
        }

        const val LANGUAGE_TYPE: String = "languageType" //當前的語言環境

    }

    object Time {
        const val sleep60: Int = 60
        const val sleep500: Long = 500
        const val sleep400: Long = 400
        const val sleep200: Long = 200
        const val sleep100: Long = 100
        const val sleep1000: Long = 1000
        const val sleep10000: Long = 10000
        const val sleep800: Long = 800
        const val sleep2000: Long = 2000

        const val LONG_TIME_OUT: Long = 30//设置超时时间
        const val GET_LATEST_RECORD_INTERVAL_TIME: Long = 6//倒数计时
        const val TOAST_LONG: Long = 3
        const val TOAST_SHORT: Long = 0
    }

    /*定时、倒计时管理*/
    object TimerType {
        const val COUNT_DOWN_TIME = "countDownTime"//倒数计时
        const val COUNT_DOWN_ONE_TIME = "countDownOneTime"//倒数计时
        const val GET_LATEST_RECORD = "getLatestRecord"//获取最新的竞价记录
    }

    /*页面跳转的请求code*/
    object RequestCode {

        const val INTENT_LOGIN_REQUEST_CODE = 0x001//跳转到注册页面
        const val COUNTRY_CODE_REQUEST_CODE = 0x002//跳转到城市区号页面
        const val AUCTION_DETAIL_REQUEST_CODE = 0x003//跳转到竞拍详情页面
        const val PERMISSIONS_REQUEST_CALL_PHONE_CODE: Int = 0x004//跳转到打电话的界面
        const val ADD_CONTACT_REQUEST_CODE = 0x005// 跳转到添加联系人
        const val LANGUAGE_SWITCH_REQUEST_CODE: Int = 0x006// 跳转到语言切换的页面
        const val FORGOT_PASSWORD_REQUEST_CODE: Int = 0x007// 跳转到忘记密码的页面
        const val WALLET_ASSET_DETAIL_REQUEST_CODE: Int = 0x007// 跳转到钱包资产明细界面

    }

    /* 國際化語言*/
    object Language {
        const val CN = "CN"//中文簡體
        const val TW = "TW"// 台灣繁體
        const val EN = "EN"//英文
        const val HK = "HK"// 香港繁體
    }

    /*页面的来自*/
    object From {
        const val COUNTRY_CODE = "countryCode"//城市区号
    }

    /*存储路径名字 */
    object FilePath {
        const val COUNTRY_CODE = "country_code"
        const val ZH_CN_COUNTRY_CODE = "zh-cn_CountryCode"
        const val ZH_TW_COUNTRY_CODE = "zh-tw_CountryCode"
        const val EN_US_COUNTRY_CODE = "en-us_CountryCode"

    }

    /*表示动作来向 */
    object ActionFrom {
        const val APPLY_AUCTION: String = "applyAuction"//持宝竞拍
        const val REPLACEMENT_COMMODITY: String = "replacementCommodity"//置换商品
        const val RECEIVE_COMMODITY: String = "receiveCommodity"//领取商品
        const val REGISTER = "register"//注册
        const val SELECT_COUNTRY_CODE = "selectCountryCode"//选择城市区号
    }

}