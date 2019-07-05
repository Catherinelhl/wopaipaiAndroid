package cn.wopaipai.constant

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 11:49
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.Constant
+--------------+---------------------------------
+ description  +  常数类：定義网路请求對應Config常數(API)
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object HostURLConstants {

    const val SERVER_TYPE = MessageConstants.ServerType.PRD//服務器環境，false為測試，true為正式

    //API domain
    const val API_HOST = "http://api.myiauction.com/"
    //API TEST domain
    const val API_TEST_HOST = "http://testapi.myiauction.com/"

    //用户平台协议
    const val PLATFORM_PROTOCAL = "http://www.myiauction.com/Register/Agreement"
}