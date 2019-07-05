package cn.catherine.token.http.retrofit

import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

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
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class StringConverterFactory : Factory() {
    private val MEDIA_TYPE = MediaType.parse("text/plain")


    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        return if (String::class.java == type)
            Converter<ResponseBody, String> { value -> value.string() }
        else
            null
    }

    override fun stringConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<*, String>? {
        return super.stringConverter(type, annotations, retrofit)
    }

    override fun requestBodyConverter(
        type: Type?,
        parameterAnnotations: Array<Annotation>?,
        methodAnnotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<*, RequestBody>? {
        return if (String::class.java == type)
            Converter<String, RequestBody> { value -> RequestBody.create(MEDIA_TYPE, value) }
        else
            null
    }
}