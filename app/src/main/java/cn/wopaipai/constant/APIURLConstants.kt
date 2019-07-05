package cn.wopaipai.constant

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 11:47
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.Constant
+--------------+---------------------------------
+ description  +   常数类：定義API Router
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object APIURLConstants {


    /*用户注册*/
    const val API_REGISTER = "api/User/Register"

    /*发送注册手机验证码*/
    const val API_SEND_REGISTER_VALID_CODE = "api/User/SendRegistValidCode"

    /*用户登录接口*/
    const val API_LOGIN = "api/User/Login"

    /*重设密码*/
    const val API_RESET_PASSWORD = "api/User/ResetPassword"

    /*获取个人信息*/
    const val API_GET_PROFILE = "api/User/GetProfile"


    /*发送重设密码验证码*/
    const val API_RESET_PASSWORD_VERIFY_CODE = "api/User/SendResetPwdValidCode"


    /*获取可用国家列表*/
    const val API_GET_COUNTRY_CODE = "api/Country/GetCountries"


    /*获取首页banner*/
    const val API_GET_INDEX_BANNERS = "api/Banner/GetIndexBanners"


    /*上传Banner*/
    const val API_GET_UPLOAD_BANNER = "api/File/UploadBanner"


    /*获取产品列表*/
    const val API_GET_PRODUCTS = "api/Product/GetProducts"

    /*创建产品*/
    const val API_CREATE_PRODUCT = "api/Product/Create"

    /****************************************竞拍 Auction START****************************************/
    /*获取竞拍产品列表*/
    const val API_GET_AUCTION_LIST = "api/Auction/GetAuctionList"

    /*确认交易密码*/
    const val API_CONFIRM_TRADE_PWD = "api/User/ConfirmTradePwd"

    /*获取竞拍产品详情*/
    const val API_GET_AUCTION_DETAIL = "api/Auction/GetAuctionDetail"

    /*获取竞拍记录数据，包含出价记录和分成记录*/
    const val API_GET_AUCTION_RECORD = "api/Auction/GetAuctionRecord"


    /*获取最新竞拍记录数据，包含出价记录和分成记录，并自动更新在线时间*/
    const val API_GET_LAST_AUCTION_RECORDS = "api/Auction/GetLatestAuctionRecords"

    /*出价竞拍*/
    const val API_GET_AUCTION_OFFER_PRICE = "api/Auction/OfferPrice"

    /*创建一个竞拍产品，成功返回竞拍Id*/
    const val API_AUCTION_CREATE = "api/Auction/Create"

    /*竞拍获得接口*/
    const val API_GET_AUCTION_OBTAIN = "api/Auction/GetAuctionObtain"

    /****************************************竞拍 Auction END****************************************/


    /****************************************钱包 Wallet START****************************************/


    /*获取钱包列表*/
    const val API_GET_USER_WALLET = "api/Wallet/GetUserWallet"

    /*解冻押金*/
    const val API_UNFREEZE = "api/Wallet/Unfreeze"

    /****************************************钱包 Wallet End****************************************/


    /*获取可用币种*/
    const val API_GET_COIN_TYPE = "api/Currency/GetCurrencies"


    /*获取联系人列表*/
    const val API_GET_CONTACT = "api/Contact/GetContact"

    /*创建联系人*/
    const val API_CREATE_CONTACT = "api/Contact/Create"

    /*修改联系人*/
    const val API_UPDATE_CONTACT = "api/Contact/Update"

    /*删除联系人*/
    const val API_DELETE_CONTACT = "api/Contact/Delete"

    /*获取社群用户*/
    const val API_GET_COMMUNITY = "api/User/GetCommunityUsers"

    /*转入*/
    const val API_GET_ADDRESS = "api/Wallet/GetAddress"

    /*钱包交易记录*/
    const val API_GET_WALLET_LIST = "api/Wallet/GetTradeFlow"

    // 邀请注册
    const val API_INVITE_REGISTER = "api/User/GetPromoteInfo"

    // 获取兑换信息
    const val API_WALLET_EXCHANGE = "api/Wallet/GetExchangeWallet"

    // 兑换ZBB
    const val API_EXCHANGE_ZBB = "api/Wallet/ExchangeZbb"

    // 转出
    const val API_TURN_OUT="api/Wallet/TransferOut"

}