package cn.wopaipai.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import cn.wopaipai.BuildConfig
import cn.wopaipai.R
import cn.wopaipai.base.BaseAty
import cn.wopaipai.bean.CountryCodeBean
import cn.wopaipai.bean.UserResultBean
import cn.wopaipai.bean.request.RegisterRequestBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.listener.AmountEditTextFilter
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.manager.hideSoftKeyBoardByTouchView
import cn.wopaipai.manager.intentToActivity
import cn.wopaipai.manager.returnResult
import cn.wopaipai.tool.ActivityTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.StringTool
import cn.wopaipai.tool.time.ObservableTimerTool
import cn.wopaipai.ui.contract.RegisterContract
import cn.wopaipai.ui.presenter.RegisterPresenterImp
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.aty_login.*
import kotlinx.android.synthetic.main.aty_register.*
import kotlinx.android.synthetic.main.include_header.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 10:40
+--------------+---------------------------------
+ projectName  +   wopaipai
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +   activity:注册页面
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class RegisterAty : BaseAty(), RegisterContract.View {


    //用于倒计时的订阅
    private var disposableCountDownTimer: Disposable? = null
    private val presenter by lazy { RegisterPresenterImp(this) }
    private lateinit var phoneCode: String

    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_register

    override fun initViews() {
        ActivityTool.addActivity(this)
        tv_title.text = getResString(R.string.register)
        ib_back.visibility = View.VISIBLE
        phoneCode = et_lmr_phone_number.getLeftContent()
    }

    override fun initData() {
        //TODO 临时写上推荐码
        if (BuildConfig.LazyMode) {
            et_recommend_code.setText(MessageConstants.REGISTER_RECOMMEND_CODE)
        }

    }

    @SuppressLint("CheckResult")
    override fun initListener() {
        sv_register.hideSoftKeyBoardByTouchView(this)
        et_lmr_phone_number.onItemSelectListener = baseOnItemSelectListener
        switchPasswordState(cb_transaction_password, et_transaction_password)
        switchPasswordState(cb_confirm_transaction_password, et_confirm_transaction_password)


        cb_right_login_pwd!!.setOnCheckedChangeListener { buttonView, isChecked ->
            val text = et_register_pwd!!.text.toString()
            if (StringTool.isEmpty(text)) {
                return@setOnCheckedChangeListener
            }
            et_register_pwd!!.inputType = if (isChecked)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD//设置当前私钥显示不可见
            et_register_pwd!!.setSelection(text.length)

        }
        cb_right_confirm_login_pwd!!.setOnCheckedChangeListener { buttonView, isChecked ->
            val text = et_register_confirm_pwd!!.text.toString()
            if (StringTool.isEmpty(text)) {
                return@setOnCheckedChangeListener
            }
            et_register_confirm_pwd!!.inputType = if (isChecked)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD//设置当前私钥显示不可见
            et_register_confirm_pwd!!.setSelection(text.length)

        }
        cb_transaction_password!!.setOnCheckedChangeListener { buttonView, isChecked ->
            val text = et_transaction_password!!.text.toString()
            if (StringTool.isEmpty(text)) {
                return@setOnCheckedChangeListener
            }
            et_transaction_password!!.inputType = if (isChecked)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD//设置当前私钥显示不可见
            et_transaction_password!!.setSelection(text.length)

        }
        cb_confirm_transaction_password!!.setOnCheckedChangeListener { buttonView, isChecked ->
            val text = et_confirm_transaction_password!!.text.toString()
            if (StringTool.isEmpty(text)) {
                return@setOnCheckedChangeListener
            }
            et_confirm_transaction_password!!.inputType = if (isChecked)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD//设置当前私钥显示不可见
            et_confirm_transaction_password!!.setSelection(text.length)

        }
        RxView.clicks(ib_back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                activity.returnResult(true)
            }
        RxView.clicks(btn_register).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                var registerResultBean = RegisterRequestBean()
                //1：判断邮箱是否输入
                var emailMsg = et_email.text.trim().toString()
//                when {
                //TODO 邮箱第一期暂时不做
//                    StringTool.isEmpty(emailMsg) -> {
//                        showToast(getResString(R.string.please_input_email_address))
//                        return@subscribe
//                    }
//                    !RegexTool.isRightEmail(emailMsg!!) -> {
//                        showToast(getResString(R.string.please_input_right_email_address))
//                        return@subscribe
//
//                    }
//                    else -> registerResultBean.email = emailMsg
//
//                }
                registerResultBean.email = emailMsg
                //2：判断手机号是否输入
                var phoneNumber = et_lmr_phone_number.getMiddleContent()
                if (StringTool.isEmpty(phoneNumber)) {
                    showToast(getResString(R.string.please_input_phone_number))
                    return@subscribe
                } else {
                    registerResultBean.phoneNumber = phoneNumber
                }
                //3：判断验证码是否输入
                var verifyCode = et_verify_code.text.toString()
                if (StringTool.isEmpty(verifyCode)) {
                    showToast(getResString(R.string.please_input_verify_code))
                    return@subscribe
                } else {
                    registerResultBean.validCode = verifyCode
                }
                //4：判断登录密码是否输入
                var loginPassword = et_register_pwd.text.toString()
                if (StringTool.isEmpty(loginPassword)) {
                    showToast(getResString(R.string.please_input_login_password))
                    return@subscribe
                }
                //5：判断确认登录密码是否输入
                var confirmLoginPassword = et_register_confirm_pwd.text.toString()
                if (StringTool.isEmpty(confirmLoginPassword)) {
                    showToast(getResString(R.string.please_confirm_login_password))
                    return@subscribe
                }
                //判断确认登录密码与登录密码是否一致
                if (StringTool.equals(loginPassword, confirmLoginPassword)) {
                    registerResultBean.password = confirmLoginPassword

                } else {
                    showToast(getResString(R.string.login_password_input_not_match))
                    return@subscribe
                }
                //6：判断交易密码是否输入
                var tradePassword = et_transaction_password.text.toString()
                if (StringTool.isEmpty(tradePassword)) {
                    showToast(getResString(R.string.please_input_trade_password))
                    return@subscribe
                }
                // 判断交易密码的位数
                if (tradePassword.length != MessageConstants.PASSWORD_LENGTH) {
                    showToast(getResString(R.string.please_input_six_length_trade_password))
                    return@subscribe
                }
                //7：判断确认交易密码是否输入
                var confirmTradePassword =
                    et_confirm_transaction_password.text.toString()
                if (StringTool.isEmpty(confirmTradePassword)) {
                    showToast(getResString(R.string.please_confirm_trade_password))
                    return@subscribe
                }
                //判断两次输入的交易密码是否一致
                if (StringTool.equals(tradePassword, confirmTradePassword)) {
                    registerResultBean.tradePassword = confirmTradePassword

                } else {
                    showToast(getResString(R.string.trade_password_input_not_match))
                    return@subscribe
                }
                //8：判断推荐码是否输入
                var recommendCode = et_recommend_code.text.toString()
                if (StringTool.isEmpty(recommendCode)) {
                    showToast(getResString(R.string.please_input_recommend_code))
                    return@subscribe
                } else {
                    registerResultBean.sourceCode = recommendCode
                }
                registerResultBean.countryPhone = phoneCode.toInt()
                //判读当前服务器平台协议是否同意
                if (!tv_register_tips.checkIsAgree()) {
                    showToast(getResString(R.string.please_agree_item_of_service))
                    return@subscribe
                }
                presenter.register(registerResultBean, phoneNumber!!)

            }
        RxView.clicks(tv_get_verify_code)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                //1：判断当前是否是倒计时状态
                var state = tv_get_verify_code.text.toString()
                if (!StringTool.equals(state, getResString(R.string.get_verify_code))) {
                    return@subscribe
                }
                //1:判断是否输入了手机号
                var phoneNumber = et_lmr_phone_number.getMiddleContent()
                if (StringTool.isEmpty(phoneNumber)) {
                    showToast(getResString(R.string.please_input_phone_number))
                    return@subscribe
                }

                presenter.sendRegisterVerifyCode(phoneNumber!!, phoneCode.toInt())
                //获取验证码，点击之后开始倒计时
                startCountDownInterval()
            }

        tv_register_tips.setOnItemSelectListener(object : OnItemSelectListener {
            override fun <T : Any?> onItemSelect(type: T, from: String?) {
                intentToActivity(PlatformProtocolAty::class.java)
            }
        }, Constants.ActionFrom.REGISTER)


    }


    private fun switchPasswordState(checkBox: CheckBox, view: EditText) {

        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            val text = view.text.toString()
            if (StringTool.isEmpty(text)) {
                return@setOnCheckedChangeListener
            }
            view.inputType = if (isChecked)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_NUMBER_VARIATION_PASSWORD//设置当前私钥显示不可见  checkBox
            view.setSelection(text.length)

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
                //                                            LogTool.d(TAG, "计时开始");
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

    override fun registerSuccess(userResultBean: UserResultBean) {
        //携带参数然后跳转登录直接登录进入MainAty
        var bundle = Bundle()
        bundle.putSerializable(Constants.KeyMaps.USER_INFO, userResultBean)
        bundle.putString(Constants.KeyMaps.From, Constants.ValueMaps.REGISTER)
        intentToActivity(bundle, MainAty::class.java, true)
    }

    override fun registerFailure(msg: String) {
        showToast(msg)
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
                                et_lmr_phone_number.setLeftContent(
                                    phoneCode
                                )
                            }
                        }

                    }

                }
            }
        }
    }

    override fun sendRegisterVerifyCodeFailure(msg: String) {
        showToast(msg)
        disposeRequest(disposableCountDownTimer)
    }

    override fun sendRegisterVerifyCodeSuccess(msg: String) {
        //停止倒计时
        LogTool.d(tag, msg)
        showToast(getResString(R.string.success_to_send_register_verify_code))
    }

}