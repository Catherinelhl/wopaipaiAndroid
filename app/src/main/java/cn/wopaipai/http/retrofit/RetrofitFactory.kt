package cn.catherine.token.http.retrofit

import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.HostURLConstants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.http.retrofit.LenientGsonConverterFactory
import cn.wopaipai.tool.StringTool
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 10:42
+--------------+---------------------------------
+ projectName  +   wopaipai
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.base
+--------------+---------------------------------
+ description  +   Http：Retrofit封裝网络请求
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/
object RetrofitFactory {

    private var APIInstance: Retrofit? = null//访问正常訪問的网络
    private var client: OkHttpClient? = null

    private fun initClient() {
        if (client == null) {
            client = OkHttpClient.Builder()
                .connectTimeout(Constants.Time.LONG_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.Time.LONG_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.Time.LONG_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(OkHttpInterceptor())
                .build()
        }
    }

    fun getAPIInstance(): Retrofit {
        //根據當前的環境得到相對應的API
        return getAPIInstance(
            if (StringTool.equals(
                    HostURLConstants.SERVER_TYPE,
                    MessageConstants.ServerType.TEST
                )
            ) HostURLConstants.API_TEST_HOST else HostURLConstants.API_HOST
        )
    }


    /**
     * api
     *
     * @param baseUrl
     * @return
     */
    fun getAPIInstance(baseUrl: String): Retrofit {
        initClient()
        if (APIInstance == null) {
            APIInstance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client!!)
                .addConverterFactory(LenientGsonConverterFactory.create())
                .addConverterFactory(StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Observable，暂时没用
                .build()
        }
        return APIInstance!!
    }

    //清空API请求
    fun cleanAPI() {
        APIInstance = null
    }


}