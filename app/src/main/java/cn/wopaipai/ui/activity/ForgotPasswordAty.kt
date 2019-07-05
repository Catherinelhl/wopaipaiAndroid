package cn.wopaipai.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.wopaipai.R
import cn.wopaipai.base.BaseAty
import cn.wopaipai.base.BaseException
import cn.wopaipai.bean.CountryCodeBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.manager.hideSoftKeyBoardByTouchView
import cn.wopaipai.manager.returnResult
import cn.wopaipai.tool.ActivityTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.StringTool
import cn.wopaipai.tool.time.ObservableTimerTool
import cn.wopaipai.ui.contract.ResetPasswordContract
import cn.wopaipai.ui.presenter.ResetPasswordPresenterImp
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.aty_forgot_password.*
import kotlinx.android.synthetic.main.aty_forgot_password.tv_get_verify_code
import kotlinx.android.synthetic.main.include_header.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-18 01:56
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  忘记密码
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class ForgotPasswordAty : BaseAty(), ResetPasswordContract.View {
    //用于倒计时的订阅
    private var disposableCountDownTimer: Disposable? = null
    private val presenter by lazy { ResetPasswordPresenterImp(this) }
    private lateinit var phoneCode: String

    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_forgot_password

    override fun initViews() {
        ActivityTool.addActivity(this)
        tv_title.text = getResString(R.string.forgot_password)
        ib_back.visibility = View.VISIBLE
        phoneCode = et_lmr_account.getLeftContent()
        et_lmr_confirm_reset_password.setHint(getResString(R.string.confirm_reset_password))
    }

    override fun initData() {
    }

    @SuppressLint("CheckResult")
    override fun initListener() {
        sv_forgot_password.hideSoftKeyBoardByTouchView(this)
        et_lmr_account.onItemSelectListener = baseOnItemSelectListener
        RxView.clicks(ib_back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(o: Any) {
                    returnResult(true)
                }

                override fun onError(e: Throwable) {
                    LogTool.e(tag, e.toString())

                }

                override fun onComplete() {

                }
            })
        RxView.clicks(btn_finish).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(o: Any) {
                    //1:判断手机号是否输入
                    var phoneNumber = et_lmr_account.getMiddleContent()
                    if (StringTool.isEmpty(phoneNumber)) {
                        showToast(getResString(R.string.please_input_phone_number))
                        return
                    }
                    //2：判断验证码是否输入
                    var verifyCode = et_verify_code.text.toString()
                    if (StringTool.isEmpty(verifyCode)) {
                        showToast(getResString(R.string.please_input_verify_code))
                        return
                    }
                    //3：判断登录密码是否输入
                    var resetPassword = et_lmr_reset_password.getMiddleContent()
                    if (StringTool.isEmpty(resetPassword)) {
                        showToast(getResString(R.string.please_input_login_password))
                        return
                    }

                    //4：判断确认密码是否输入
                    var confirmResetPassword = et_lmr_confirm_reset_password.getMiddleContent()
                    if (StringTool.isEmpty(confirmResetPassword)) {
                        showToast(getResString(R.string.please_confirm_login_password))
                        return
                    }

                    //5：判断登录密码以及确认密码是否一致
                    if (!StringTool.equals(resetPassword, confirmResetPassword)) {
                        showToast(getResString(R.string.login_password_input_not_match))
                        return
                    }
                    presenter.resetPassword(
                        phoneNumber!!,
                        phoneCode.toInt(),
                        resetPassword!!,
                        verifyCode
                    )
                }

                override fun onError(e: Throwable) {
                    BaseException.print(e)
                    LogTool.e(tag, e.toString())
                }

                override fun onComplete() {

                }
            })

        RxView.clicks(tv_switch_method_reset_password)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(o: Any) {
                    showToast(getResString(R.string.this_feature_not_open_yet))
//                    val text = tv_switch_method_reset_password.text.toString()
//                    if (StringTool.equals(
//                            text,
//                            context.resources.getResString(R.string.switch_phone_to_reset_password)
//                        )
//                    ) {
//                        //代表当前是邮箱登录，那么需要切换到手机号登录
//
//                        et_lmr_account.switchToPhone()
//                        et_lmr_reset_password.leftInVisible()
//                        et_lmr_confirm_reset_password.leftInVisible()
//                        tv_switch_method_reset_password.text =
//                            context.resources.getResString(R.string.switch_email_to_reset_password)
//                        et_lmr_account.setMiddleContent(MessageConstants.Empty)
//                        et_lmr_reset_password.setMiddleContent(MessageConstants.Empty)
//                        et_lmr_confirm_reset_password.setMiddleContent(MessageConstants.Empty)
//
//
//                    } else {
//                        // 否则当前是手机号登录，需要切换到邮箱的登录
//
//                        et_lmr_account.switchToEmail()
//                        //修改文字为"用邮箱登录"
//                        et_lmr_reset_password.leftGone()
//                        et_lmr_confirm_reset_password.leftGone()
//                        tv_switch_method_reset_password.text =
//                            context.resources.getResString(R.string.switch_phone_to_reset_password)
//                        et_lmr_account.setMiddleContent(MessageConstants.Empty)
//                        et_lmr_reset_password.setMiddleContent(MessageConstants.Empty)
//                        et_lmr_confirm_reset_password.setMiddleContent(MessageConstants.Empty)
//
//                    }
                }

                override fun onError(e: Throwable) {
                    LogTool.e(tag, e.toString())

                }

                override fun onComplete() {

                }
            })

        RxView.clicks(tv_get_verify_code)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                //1：判断当前是否是倒计时状态
                var state = tv_get_verify_code.text.toString()
                if (!StringTool.equals(state, getResString(R.string.get_verify_code))) {
                    return@subscribe
                }
                //1:判断是否输入了手机号
                var phoneNumber = et_lmr_account.getMiddleContent()
                if (StringTool.isEmpty(phoneNumber)) {
                    showToast(getResString(R.string.please_input_phone_number))
                    return@subscribe
                }

                presenter.sendResetPasswordVerifyCode(phoneNumber!!, phoneCode.toInt(), "")
                // 获取验证码，点击之后开始倒计时
                startCountDownInterval()
            }

    }

    /**
     * 开始倒计时
     * 开始60s倒计时
     */
    private fun startCountDownInterval() {
        ObservableTimerTool.countDownTimer(Constants.Time.sleep60)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
            }.subscribe(object : Observer<Int> {
                @SuppressLint("SetTextI18n")
                override fun onNext(integer: Int) {
                    tv_get_verify_code!!.text = "$integer s"
                }

                override fun onSubscribe(d: Disposable) {
                    disposableCountDownTimer = d

                }

                override fun onError(e: Throwable) {
                    disposeRequest(disposableCountDownTimer)
                }

                override fun onComplete() {
                    disposeRequest(disposableCountDownTimer)
                }
            })
    }

    private fun disposeRequest(disposable: Disposable?) {
        tv_get_verify_code!!.text = getResString(R.string.get_verify_code)
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    override fun getResetPasswordVerifyCodeSuccess(msg: String?) {

    }

    override fun getResetPasswordVerifyCodeFailure(msg: String?) {
        showToast(msg!!)
        disposeRequest(disposableCountDownTimer)
    }

    override fun resetPasswordSuccess(msg: String?) {
        showShortToast(msg!!)
        finish()
    }

    override fun resetPasswordFailure(msg: String?) {
        showToast(msg!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Constants.RequestCode.COUNTRY_CODE_REQUEST_CODE -> {
                    //获取当前选择的区号
                    data?.let {
                        val isBack = it.getBooleanExtra(Constants.KeyMaps.From, false)
                        if (!isBack) {
                            val countryCodeLocal: CountryCodeBean.CountryCode? =
                                it.getSerializableExtra(Constants.KeyMaps.SELECT_COUNTRY_CODE)
                                        as CountryCodeBean.CountryCode
                            countryCodeLocal?.let { countryCode ->
                                phoneCode = StringTool.splitCountryCode(countryCode.countryPhone)
                                et_lmr_account.setLeftContent(
                                    phoneCode
                                )
                            }
                        }

                    }

                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        returnResult(true)

    }

}