package cn.wopaipai.requester

import cn.catherine.token.http.retrofit.RetrofitFactory
import cn.wopaipai.bean.UserResultBean
import cn.wopaipai.gson.ResponseAnyJson
import cn.wopaipai.gson.ResponseJson
import cn.wopaipai.http.HttpApi
import io.reactivex.Observable
import okhttp3.RequestBody

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-16 18:29
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.requester
+--------------+---------------------------------
+ description  +  登录+®注册
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object LoginRequester {

    /**
     * 注册
     *
     *  @param body
     */
    fun register(body: RequestBody): Observable<ResponseAnyJson<UserResultBean>?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java).register(body)
    }

    /**
     * 注册发送验证码
     *
     *  @param body
     */
    fun sendRegisterVerifyCode(body: RequestBody): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java).registerValidCode(body)
    }


    /**
     * 登录
     *
     * @param body
     */
    fun login(body: RequestBody): Observable<ResponseAnyJson<UserResultBean>?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java).login(body)
    }

    /**
     * 重设密码
     *
     * @param body
     */
    fun resetPassword(body: RequestBody): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java).resetPassword(body)
    }


    /**
     * 重设密码发送验证码
     *
     * @param body
     */
    fun resetPasswordVerifyCode(body: RequestBody): Observable<ResponseJson?> {
        return RetrofitFactory.getAPIInstance().create(HttpApi::class.java)
            .resetPasswordVerifyCode(body)
    }

}