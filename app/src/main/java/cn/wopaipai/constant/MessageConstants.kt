package cn.wopaipai.constant

import cn.wopaipai.utils.MD5

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 11:46
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.Constant
+--------------+---------------------------------
+ description  +   常数类：定义一些服务器接口code对应的message以及需要print的message字段
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object MessageConstants {

    //服務器類型
    object ServerType {
        const val TEST = "TEST"//測試環境
        const val PRD = "PRD"//正式環境

    }

    const val PASSWORD_LENGTH: Int = 6
    const val LOADING_MORE: String = "loading more"
    const val OFFER_PRICE: Double = 10.0
    const val AUCTION_TYPE: String = "ZBB"//竞拍默认币种
    const val NO_ENOUGH_BALANCE = "-1"
    const val NO_ENOUGH_TIME = " 00:00:00"
    const val AMOUNT_EXCEPTION_CODE = "-1"
    const val CONNECT_EXCEPTION: String = "CONNECT EXCEPTION"
    const val SCREEN_WIDTH: String = "screen width:"
    const val SCREEN_HEIGHT: String = "screen height:"
    const val DEVICE_INFO: String = "Devices info:"
    const val Empty: String = ""
    const val Space: String = " "

    const val REGISTER_RECOMMEND_CODE = "D09NMX"//临时的注册推荐码

    const val CODE_0 = 0
    const val CODE_200 = 200 // Success
    const val CODE_400 = 400 // Failure
    const val CODE_404 = 404 // Failure


    /// 签名验证不通过
    const val C1000 = 1000
    /// 用户名或国家代码错误
    const val C1001 = 1001
    ///登录密码不正确
    const val C1002 = 1002
    ///交易密码不正确
    const val C1003 = 1003
    ///登录密码格式错误
    const val C1004 = 1004
    /// 短信验证码无效
    const val C1006 = 1006
    /// 邀请码或推广码无效
    const val C1007 = 1007
    /// 邮箱格式不正确
    const val C1008 = 1008
    /// 手机号码格式不正确
    const val C1009 = 1009
    /// 手机号码已存在
    const val C1010 = 1010
    /// 国家地区暂不支持
    const val C1011 = 1011
    /// 钱包创建失败
    const val C1012 = 1012
    /// 手机号码不存在
    const val C1018 = 1018
    /// 不存在此产品
    const val C1020 = 1020
    ///加价不能小于或等于0
    const val C1021 = 1021
    ///  起拍时间不能小于当前时间
    const val C1022 = 1022
    ///EndSeconds不能小于或等于0
    const val C1023 = 1023
    /// 页索引不能小于等于0
    const val C1024 = 1024
    ///  页大小不能小于等于0
    const val C1025 = 1025
    /// 竞拍不存在，请检查AuctionId
    const val C1030 = 1030
    /// 钱包余额小于入场保证金
    const val C1031 = 1031
    /// 当前竞品不可拍
    const val C1032 = 1032
    /// 余额不足
    const val C1033 = 1033
    /// 出价低于最低加价
    const val C1034 = 1034
    /// 拍卖时间已结束
    const val C1035 = 1035
    ///  系统
    const val C9999 = 9999

    /// 押金必须大于等于起拍价
    const val C1026 = 1026

    /// 输入的币种不支持
    const val C1040 = 1040

    /// 输出的币种不支持
    const val C1041 = 1041

    /// 兑换失败
    const val C1045 = 1045

    ///邮箱已存在
    const val C1019 = 1019

    ///外部转出失败
    const val C1042 = 1042

    /// 内部转出失败
    const val C1043 = 1043


    ///钱包参数错误
    const val C1046 = 1046


    //    /// <summary>
//    /// 默认
//    /// </summary>
//    [Description("默认")] Default = 0,
//    /// <summary>
//    ///     转入
//    /// </summary>
//    [Description("转入")] Transfer = 1,
//
//    /// <summary>
//    ///     转出
//    /// </summary>
//    [Description("转出")] TransferOut = 2,
//
//    /// <summary>
//    ///     兑换
//    /// </summary>
//    [Description("兑换")] Exchange = 3,
//
//    /// <summary>
//    ///     竞拍加价
//    /// </summary>
//    [Description("竞拍加价")] Auction = 4,
//
//    /// <summary>
//    ///     竞拍红包
//    /// </summary>
//    [Description("竞拍红包")] AuctionReward = 5,
//
//    /// <summary>
//    ///     购买
//    /// </summary>
//    [Description("购买")] Buy = 6,
//
//    /// <summary>
//    ///     托管收益
//    /// </summary>
//    [Description("托管收益")] Host = 7
    const val CHARSET_FORMAT = "UTF-8"   //字节码格式
    const val HTTP_CONTENT_ENCODING = "Content-Encoding"
    const val NULL = "(null)"

    /* 设备相关信息*/
    object Device {
        const val CPU_INFO = "CPU info:"
        const val CHECK_SIM_STATUS_IS_TV = "checkSIMStatusIsTv"
        const val TV_DEVICE = "TV DEVICE"
        const val NON_TV_DEVICE = "NON TV DEVICE"
        const val MANUFACTURER = "manufacturer:"
        const val BRAND = "brand:"
        const val BOARD = "board:"
        const val NAME = "device:"
        const val MODEL = "model:"
        const val DISPLAY = "display:"
        const val PRODUCT = "product:"
        const val FINGER_PRINT = "fingerPrint:"
    }


    /*日志信息*/
    object LogInfo {
        const val LOGOUT_TAG = "[LogOut] \r"
        const val REQUEST_JSON = "【RequestJson】"
        const val RESPONSE_JSON = "【ResponseJson】"

    }
}