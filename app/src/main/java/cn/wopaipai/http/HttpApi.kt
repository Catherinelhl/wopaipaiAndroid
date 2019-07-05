package cn.wopaipai.http

import cn.wopaipai.bean.*
import cn.wopaipai.bean.request.GetWalletListRequestBean
import cn.wopaipai.constant.APIURLConstants.API_AUCTION_CREATE
import cn.wopaipai.constant.APIURLConstants.API_CONFIRM_TRADE_PWD
import cn.wopaipai.constant.APIURLConstants.API_CREATE_CONTACT
import cn.wopaipai.constant.APIURLConstants.API_CREATE_PRODUCT
import cn.wopaipai.constant.APIURLConstants.API_DELETE_CONTACT
import cn.wopaipai.constant.APIURLConstants.API_GET_ADDRESS
import cn.wopaipai.constant.APIURLConstants.API_GET_COMMUNITY
import cn.wopaipai.constant.APIURLConstants.API_GET_CONTACT
import cn.wopaipai.constant.APIURLConstants.API_GET_WALLET_LIST
import cn.wopaipai.constant.APIURLConstants.API_EXCHANGE_ZBB
import cn.wopaipai.constant.APIURLConstants.API_GET_AUCTION_DETAIL
import cn.wopaipai.constant.APIURLConstants.API_GET_AUCTION_LIST
import cn.wopaipai.constant.APIURLConstants.API_GET_AUCTION_OBTAIN
import cn.wopaipai.constant.APIURLConstants.API_GET_AUCTION_OFFER_PRICE
import cn.wopaipai.constant.APIURLConstants.API_GET_AUCTION_RECORD
import cn.wopaipai.constant.APIURLConstants.API_GET_COIN_TYPE
import cn.wopaipai.constant.APIURLConstants.API_GET_COUNTRY_CODE
import cn.wopaipai.constant.APIURLConstants.API_GET_INDEX_BANNERS
import cn.wopaipai.constant.APIURLConstants.API_GET_LAST_AUCTION_RECORDS
import cn.wopaipai.constant.APIURLConstants.API_GET_PRODUCTS
import cn.wopaipai.constant.APIURLConstants.API_GET_PROFILE
import cn.wopaipai.constant.APIURLConstants.API_GET_UPLOAD_BANNER
import cn.wopaipai.constant.APIURLConstants.API_GET_USER_WALLET
import cn.wopaipai.constant.APIURLConstants.API_INVITE_REGISTER
import cn.wopaipai.constant.APIURLConstants.API_LOGIN
import cn.wopaipai.constant.APIURLConstants.API_REGISTER
import cn.wopaipai.constant.APIURLConstants.API_RESET_PASSWORD
import cn.wopaipai.constant.APIURLConstants.API_RESET_PASSWORD_VERIFY_CODE
import cn.wopaipai.constant.APIURLConstants.API_SEND_REGISTER_VALID_CODE
import cn.wopaipai.constant.APIURLConstants.API_TURN_OUT
import cn.wopaipai.constant.APIURLConstants.API_UNFREEZE
import cn.wopaipai.constant.APIURLConstants.API_UPDATE_CONTACT
import cn.wopaipai.constant.APIURLConstants.API_WALLET_EXCHANGE
import cn.wopaipai.gson.ResponseAnyJson
import cn.wopaipai.gson.ResponseJson
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 15:37
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.http
+--------------+---------------------------------
+ description  +   Http API请求接口提供
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface HttpApi {

    // 获取城市区号列表  @FormUrlEncoded
    @GET(API_GET_COUNTRY_CODE)
    fun getCountryCode(): Observable<ResponseAnyJson<List<CountryCodeBean.CountryCode>>?>

    // 注册  @FormUrlEncoded
    @POST(API_REGISTER)
    fun register(@Body requestBody: RequestBody): Observable<ResponseAnyJson<UserResultBean>?>

    // 发送账号验证码  @FormUrlEncoded
    @POST(API_SEND_REGISTER_VALID_CODE)
    fun registerValidCode(@Body requestBody: RequestBody): Observable<ResponseJson?>

    // 登入  @FormUrlEncoded
    @POST(API_LOGIN)
    fun login(@Body requestBody: RequestBody): Observable<ResponseAnyJson<UserResultBean>?>


    // 重设密码  @FormUrlEncoded
    @POST(API_RESET_PASSWORD)
    fun resetPassword(@Body requestBody: RequestBody): Observable<ResponseJson?>

    // 重设密码发送验证码  @FormUrlEncoded
    @POST(API_RESET_PASSWORD_VERIFY_CODE)
    fun resetPasswordVerifyCode(@Body requestBody: RequestBody): Observable<ResponseJson?>


    // 获取首页banner
    @GET(API_GET_INDEX_BANNERS)
    fun getIndexBanners(): Observable<ResponseJson?>

    // 获取个人信息
    @GET(API_GET_PROFILE)
    fun getProfile(
        @Query("PassportId") PassportId: Int,
        @Query("Sign") Sign: String
    ): Observable<ResponseAnyJson<ProfileBean>?>


    // 上传Banner  @FormUrlEncoded
    @POST(API_GET_UPLOAD_BANNER)
    fun uploadBanner(@Body requestBody: RequestBody): Observable<ResponseJson?>


    // 获取产品列表 @FormUrlEncoded
    @GET(API_GET_PRODUCTS)
    fun getProducts(
        @Query("PageSize") PageSize: Int,
        @Query("PageIndex") PageIndex: Int,
        @Query("Sign") Sign: String
    ): Observable<ResponseJson?>


    // 创建产品  @FormUrlEncoded
    @POST(API_CREATE_PRODUCT)
    fun createProduct(@Body requestBody: RequestBody): Observable<ResponseJson?>

    /****************************************竞拍 Auction START****************************************/


    // 获取竞拍产品列表
    @GET(API_GET_AUCTION_LIST)
    fun getAuctionList(
        @Query("MinPrice") MinPrice: Double,
        @Query("MaxPrice") MaxPrice: Double,
        @Query("Type") Type: Int,
        @Query("PageSize") PageSize: Int,
        @Query("PageIndex") PageIndex: Int,
        @Query("Sign") Sign: String
    ): Observable<ResponseJson?>

    // 确认交易密码
    @POST(API_CONFIRM_TRADE_PWD)
    fun confirmTradePwd(@Body requestBody: RequestBody): Observable<ResponseJson?>

    // 获取竞拍产品详情
    @GET(API_GET_AUCTION_DETAIL)
    fun getAuctionDetail(
        @Query("PassportId") PassportId: Int,
        @Query("AuctionId") AuctionId: Int,
        @Query("Sign") Sign: String
    ): Observable<ResponseJson?>

    // 获取竞拍记录数据，包含出价记录和分成记录
    @GET(API_GET_AUCTION_RECORD)
    fun getAuctionRecord(
        @Query("PassportId") PassportId: Int,
        @Query("AuctionId") AuctionId: Int,
        @Query("PageSize") PageSize: Int,
        @Query("PageIndex") PageIndex: Int,
        @Query("Sign") Sign: String
    ): Observable<ResponseJson?>

    // 获取最新竞拍记录数据，包含出价记录和分成记录，并自动更新在线时间
    @GET(API_GET_LAST_AUCTION_RECORDS)
    fun getLatestAuctionRecords(
        @Query("PassportId") PassportId: Int,
        @Query("AuctionId") AuctionId: Int,
        @Query("OfferRecordId") OfferRecordId: Int,
        @Query("DivideRecordId") DivideRecordId: Int,
        @Query("Sign") Sign: String
    ): Observable<ResponseJson?>

    //出价竞拍
    @POST(API_GET_AUCTION_OFFER_PRICE)
    fun offerPrice(@Body requestBody: RequestBody): Observable<ResponseJson?>


    //创建一个竞拍产品，成功返回竞拍Id
    @POST(API_AUCTION_CREATE)
    fun auctionCreate(@Body requestBody: RequestBody): Observable<ResponseJson?>

    //竞拍获得接口
    @GET(API_GET_AUCTION_OBTAIN)
    fun getAuctionObtain(
        @Query("PassportId") PassportId: Int,
        @Query("PageSize") PageSize: Int,
        @Query("PageIndex") PageIndex: Int,
        @Query("sign") sign: String
    ): Observable<ResponseJson?>


    /****************************************竞拍 Auction START****************************************/


    /****************************************钱包 Wallet START****************************************/

    // 获取钱包 @FormUrlEncoded
    @GET(API_GET_USER_WALLET)
    fun getWallet(@Query("PassportId") PassportId: Int, @Query("Sign") Sign: String): Observable<ResponseAnyJson<WalletResponsesBean>?>


    //解冻押金
    @POST(API_UNFREEZE)
    fun unFreeze(@Body requestBody: RequestBody): Observable<ResponseJson?>


    /****************************************钱包 Wallet END****************************************/

    // 获取可用币种
    @GET(API_GET_COIN_TYPE)
    fun getCoinType(): Observable<ResponseJson?>

    // 获取联系人列表
    @GET(API_GET_CONTACT)
    fun getContact(@Query("PassportId") PassportId: Int, @Query("Sign") Sign: String): Observable<ResponseJson?>

    // 创建联系人
    @POST(API_CREATE_CONTACT)
    fun createContact(@Body requestBody: RequestBody): Observable<ResponseJson?>

    // 修改联系人
    @POST(API_UPDATE_CONTACT)
    fun updateContact(@Body requestBody: RequestBody): Observable<ResponseJson?>

    // 删除联系人
    @POST(API_DELETE_CONTACT)
    fun deleteContact(@Body requestBody: RequestBody): Observable<ResponseJson?>

    // 获取社群用户
    @GET(API_GET_COMMUNITY)
    fun getCommunity(@Query("PassportId") PassportId: Int, @Query("Sign") Sign: String): Observable<ResponseAnyJson<GetCommunityUserListBean>?>

    // 转入
    @GET(API_GET_ADDRESS)
    fun getAddress(
        @Query("CoinCode") CoinCode: String, @Query("PassportId") PassportId: Int, @Query(
            "Sign"
        ) Sign: String
    ): Observable<ResponseJson?>

    // 获取钱包的交易列表  FlowType：1(转入) 2(转出) 3(兑换) 4(竞拍) 5(红包) 6(购买) 7(托管)
    @GET(API_GET_WALLET_LIST)
    fun getWalletList(
        @Query("FlowType") FlowType: Int, @Query("PassportId") PassportId: Int, @Query(
            "CoinCode"
        ) CoinCode: String, @Query("PageSize") PageSize: Int, @Query("PageIndex") PageIndex: Int, @Query(
            "Sign"
        ) Sign: String
    ): Observable<ResponseAnyJson<GetWalletListRequestBean>?>

    // 邀请注册
    @GET(API_INVITE_REGISTER)
    fun inviteRegister(@Query("PassportId") PassportId: Int, @Query("Sign") Sign: String): Observable<ResponseJson?>

    // 获取兑换信息
    @GET(API_WALLET_EXCHANGE)
    fun getWalletExchange(
        @Query("CoinCode") CoinCode: String, @Query("PassportId") PassportId: Int, @Query(
            "Sign"
        ) Sign: String
    ): Observable<ResponseJson?>

    // 兑换ZBB
    @POST(API_EXCHANGE_ZBB)
    fun exchangeZBB(@Body requestBody: RequestBody): Observable<ResponseJson?>

    // 转出
    @POST(API_TURN_OUT)
    fun turnOut(@Body requestBody: RequestBody): Observable<ResponseJson?>


}