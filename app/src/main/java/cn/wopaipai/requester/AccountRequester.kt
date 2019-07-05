package cn.wopaipai.requester

import cn.catherine.token.http.retrofit.RetrofitFactory
import cn.wopaipai.bean.GetCommunityUserListBean
import cn.wopaipai.bean.ProfileBean
import cn.wopaipai.bean.WalletResponsesBean
import cn.wopaipai.gson.ResponseAnyJson
import cn.wopaipai.gson.ResponseJson
import cn.wopaipai.http.HttpApi
import io.reactivex.Observable

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-23 17:51
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.requester
+--------------+---------------------------------
+ description  +  我的
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object AccountRequester {
    /**
     *获取个人信息
     */
    fun getProfile(passportID: Int, sign: String): Observable<ResponseAnyJson<ProfileBean>?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java).getProfile(passportID,sign)
    }

    /**
     *我的社群
     */
    fun getCommunity(passportID: Int, sign: String): Observable<ResponseAnyJson<GetCommunityUserListBean>?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java).getCommunity(passportID,sign)
    }


    /**
     *获取钱包信息
     */
    fun getUserWallet(passportID: Int, sign: String): Observable<ResponseAnyJson<WalletResponsesBean>?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java).getWallet(passportID,sign)
    }

}