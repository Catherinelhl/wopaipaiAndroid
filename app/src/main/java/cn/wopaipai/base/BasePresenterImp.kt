package cn.wopaipai.base

import android.content.Context
import android.content.res.Resources
import cn.wopaipai.R
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.tool.StringTool
import io.reactivex.disposables.Disposable

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-19 11:53
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.base
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

open class BasePresenterImp : BaseContract.Presenter {
    private val TAG = BasePresenterImp::class.java.simpleName
    val context: Context = BaseApplication.context
    val string: Resources
        get() = context.resources

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun disposeDisposable(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    /**
     * 根据传入的code返回相对应的异常信息
     */
    fun getExceptionInfoByCode(code: Int): String {
        return getExceptionInfoByCode(code, MessageConstants.Empty)
    }

    fun getExceptionInfoByCode(code: Int, type: String): String {
        return when (code) {
            MessageConstants.C1000 -> {
                //签名验证不通过
                getString(R.string.request_data_failure)
            }
            MessageConstants.C1001 -> {
                //用户名或密码错误
                getString(R.string.wrong_username_or_region)
            }
            MessageConstants.C1002 -> {
                //登录密码不正确
                getString(R.string.wrong_login_password)
            }
            MessageConstants.C1003 -> {
                //登录密码不正确
                getString(R.string.wrong_trade_password)
            }
            MessageConstants.C1004 ->
                //{message='登录密码格式错误', statusCode=1004, data=null}
                getString(R.string.login_password_format_error)
            MessageConstants.C1006 -> {
                //短信验证码无效
                getString(R.string.invalid_message_verify_code)
            }
            MessageConstants.C1007 -> {
                //邀请码或推广码无效
                getString(R.string.invalid_invitation_or_promotion_code)
            }
            MessageConstants.C1008 -> {
                //邮箱格式不正确
                getString(R.string.wrong_email_format)
            }
            MessageConstants.C1009 -> {
                //手机号码格式不正确
                getString(R.string.invalid_phone_format)
            }
            MessageConstants.C1010 -> {
                //手机号码已存在
                getString(R.string.phone_number_already_exist)
            }
            MessageConstants.C1011 -> {
                //国家地区暂不支持
                getString(R.string.national_area_does_not_support)
            }
            MessageConstants.C1012 -> {
                //钱包创建失败
                getString(R.string.fail_to_create_wallet)
            }
            MessageConstants.C1020 -> {
                //不存在此产品
                getString(R.string.the_product_does_not_exist)
            }
            MessageConstants.C1021 -> {
                //加价不能小于或等于0
                getString(R.string.the_makeup_not_less_than_or_equal_zero)
            }
            MessageConstants.C1022 -> {
                //起拍时间不能小于当前时间
                getString(R.string.start_time_cannot_less_than_current_time)
            }
            MessageConstants.C1023 -> {
                //EndSeconds不能小于或等于0
                getString(R.string.end_second_cannot_less_than_or_equal_zero)
            }
            MessageConstants.C1024 -> {
                //页索引不能小于等于0
                getString(R.string.page_index_not_less_than_or_equal_zero)
            }
            MessageConstants.C1025 -> {
                //页大小不能小于等于0
                getString(R.string.page_size_not_less_than_or_equal_zero)
            }
            MessageConstants.C1030 -> {
                //竞拍不存在，请检查AuctionId
                getString(R.string.auction_does_not_exist)
            }
            MessageConstants.C1031 -> {
                //钱包余额小于入场保证金
                getString(R.string.balance_not_less_than_admission_deposit)
            }
            MessageConstants.C1032 -> {
                //当前竞品不可拍
                getString(R.string.current_commodity_not_available)
            }
            MessageConstants.C1033 -> {
                //余额不足
                getString(R.string.no_enough_balance)
            }
            MessageConstants.C1034 -> {
                //出价低于最低加价
                getString(R.string.bid_below_the_minimum_markup)
            }
            MessageConstants.C1035 -> {
                //拍卖时间已结束
                getString(R.string.auction_is_over)
            }
            MessageConstants.C1018 -> {
                //手机号码不存在
                getString(R.string.mobile_phone_not_exist)
            }
            MessageConstants.C1026 -> {
                //押金必须大于等于起拍价
                getString(R.string.security_must_more_than_lowest_price)
            }
            MessageConstants.C1040 -> {
                //输入的币种不支持
                getString(R.string.security_must_more_than_lowest_price)
            }
            MessageConstants.C1045 -> {
                //兑换失败
                getString(R.string.failed_to_exchange)
            }
            MessageConstants.C1041 -> {
                //输出的币种不支持
                getString(R.string.not_support_currency)
            }

            MessageConstants.C1019 -> {
                //邮箱已存在
                getString(R.string.email_has_exist)
            }
            MessageConstants.C1042 -> {
                //外部转出失败
                getString(R.string.external_roll_out_failed)
            }
            MessageConstants.C1043 -> {
                //内部转出失败
                getString(R.string.internal_roll_out_failed)
            }
            MessageConstants.C1046 -> {
                //钱包参数错误
                getString(R.string.wrong_wallet_params)
            }

            MessageConstants.C9999 -> {
                //系统
                if (StringTool.equals(type, Constants.KeyMaps.REGISTER_CODE)) {
                    // 判断当前是否是验证码失败
                    getString(R.string.message_send_failed)
                } else {
                    getString(R.string.system_data_exception)
                }
            }
            else -> {
                getString(R.string.get_data_failure)
            }
        }
    }
}