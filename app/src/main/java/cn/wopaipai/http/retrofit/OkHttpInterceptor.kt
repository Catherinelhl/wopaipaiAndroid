package cn.catherine.token.http.retrofit

import cn.wopaipai.base.BaseException
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.NetWorkTool
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset

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
+ description  +   Http： 设置网络请求拦截器，用於獲取查看發送之前以及獲取到Response的原始信息
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class OkHttpInterceptor : Interceptor {

    private val tag = OkHttpInterceptor::class.java.simpleName

    private val charset = Charset.forName(MessageConstants.CHARSET_FORMAT)
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val requestBody = request.body()

        var body: String? = null

        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            var charset: Charset? = charset
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(this.charset)
            }
            body = buffer.readString(charset!!)
        }
        // 获得Connection，内部有route、socket、handshake、protocol方法
        val connection = chain.connection()
        // 如果Connection为null，返回HTTP_1_1，否则返回connection.protocol()
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1
        // 比如: --> POST http://121.40.227.8:8088/api http/1.1
        val requestStartMessage = request.method() + ' '.toString() + request.url() + ' '.toString() + protocol

        //"\nheaders:" + request.headers() +
        LogTool.d(tag, "$requestStartMessage\nhttp request:$body")

        // 打印 Response
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            if (NetWorkTool.connectTimeOut(e)) {
                //切换服务器
                LogTool.d(tag, request.url().toString() + ":\n" + MessageConstants.CONNECT_EXCEPTION)
            } else {
                LogTool.d(tag, request.url().toString() + ":\n" + e.message)
            }
            throw e
        }

        val responseBody = response.body()
        var contentLength: Long = 0
        if (responseBody != null) {
            contentLength = responseBody.contentLength()
        }
        if (bodyEncoded(response.headers())) {

        } else {
            val source = responseBody!!.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            val charset = charset
            if (contentLength != 0L) {
                // 获取Response的body的字符串 并打印
                LogTool.d(tag, " http response " + request.url() + "\n" + buffer.clone().readString(charset))
            }
        }
        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get(MessageConstants.HTTP_CONTENT_ENCODING)
        return contentEncoding != null && !contentEncoding.equals(Constants.ENCODE_INGORE_CASE, ignoreCase = true)
    }
}