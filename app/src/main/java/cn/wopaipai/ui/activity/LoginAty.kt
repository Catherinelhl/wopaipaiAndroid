package cn.wopaipai.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.BuildConfig.LazyMode
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseAty
import cn.wopaipai.base.BaseException
import cn.wopaipai.bean.CountryCodeBean
import cn.wopaipai.bean.UserResultBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.listener.OnTextChangeListener
import cn.wopaipai.manager.hideSoftKeyBoardByTouchView
import cn.wopaipai.manager.intentActivityForResult
import cn.wopaipai.manager.intentToActivity
import cn.wopaipai.tool.ActivityTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.PreferenceTool
import cn.wopaipai.tool.StringTool
import cn.wopaipai.ui.contract.LoginContract
import cn.wopaipai.ui.presenter.LoginPresenterImp
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.aty_login.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 10:39
+--------------+---------------------------------
+ projectName  +   wopaipai
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +   Activity:登录页面
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class LoginAty : BaseAty(), LoginContract.View {


    private val presenter by lazy { LoginPresenterImp(this) }
    private lateinit var phoneCode: String
    private var fromRegister = false//是否来自与注册
    private var userResultBean: UserResultBean? = null
    override fun getArgs(bundle: Bundle?) {
        bundle ?: return
        var from = bundle.getString(Constants.KeyMaps.From)
        fromRegister = StringTool.equals(from, Constants.ValueMaps.REGISTER)
        if (fromRegister) {
            //获取到传过来的用户见信息
            userResultBean = bundle.getSerializable(Constants.KeyMaps.USER_INFO) as UserResultBean
        }

    }

    override fun getLayoutRes(): Int = R.layout.aty_login

    override fun initViews() {
        //检测当前的存储是否有注册的信息，如果有，直接进入首页
        val userResult = PreferenceTool.getInstance().getString(Constants.Preference.USER_INFO)
        if (StringTool.notEmpty(userResult)) {
            //存储在BaseApplication里面
            BaseApplication.userInfoBean =
                GsonTool.convert(userResult!!, UserResultBean::class.java)

            intentToActivity(MainAty::class.java, true)
        } else {
            ActivityTool.addActivity(this)
            //如果当前是从注册过来的，那么直接进行登录进入
            if (fromRegister) {
            } else {
                phoneCode = lmr_et_account.getLeftContent()
            }

            if (LazyMode) {
                lmr_et_account.setMiddleContent("18580269120")
                lmr_et_password.setMiddleContent("111111q")
            }
        }

    }

    override fun initData() {
    }

    @SuppressLint("CheckResult")
    override fun initListener() {
        rl_login.hideSoftKeyBoardByTouchView(this)
        lmr_et_account.onTextChangeListener = object : OnTextChangeListener {
            override fun onComplete(content: String) {
            }

            override fun onTextChange(content: String?) {
                //如果当前有内容，那么判断密码框是否有内容
                if (StringTool.notEmpty(content)) {
                    var msg = lmr_et_password.getMiddleContent()
                    if (StringTool.notEmpty(msg)) {
                        //按钮即为可点击状态
                        btn_login.alpha = 1f
                    } else {
                        btn_login.alpha = 0.6f
                    }
                } else {
                    btn_login.alpha = 0.6f

                }
            }
        }
        lmr_et_password.onTextChangeListener = object : OnTextChangeListener {
            override fun onComplete(content: String) {
            }

            override fun onTextChange(content: String?) {
                //如果当前密码框有内容，判断账户框是否有内容
                if (StringTool.notEmpty(content)) {
                    var msg = lmr_et_account.getMiddleContent()
                    if (StringTool.notEmpty(msg)) {
                        //按钮即为可点击状态
                        btn_login.alpha = 1f
                    } else {
                        btn_login.alpha = 0.6f

                    }
                } else {
                    btn_login.alpha = 0.6f

                }
            }
        }
        lmr_et_account.onItemSelectListener = baseOnItemSelectListener
        tv_switch_language.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, LanguageSwitchAty::class.java)
            val bundle = Bundle()
            bundle.putBoolean(Constants.KeyMaps.FROM_LOGIN, true)
            intent.putExtras(bundle)
            this.startActivityForResult(intent, Constants.RequestCode.LANGUAGE_SWITCH_REQUEST_CODE)
        }
        tv_register_account.setOnClickListener {
            this.intentActivityForResult(
                RegisterAty::class.java,
                Constants.RequestCode.INTENT_LOGIN_REQUEST_CODE, null
            )
        }
        tv_forgot_password.setOnClickListener {
            this.intentActivityForResult(
                ForgotPasswordAty::class.java,
                Constants.RequestCode.FORGOT_PASSWORD_REQUEST_CODE, null
            )
        }
        tv_switch_login.setOnClickListener {
            showToast(getResString(R.string.this_feature_not_open_yet))
            //TODO
//            val text = tv_switch_login.text.toString()
//            if (StringTool.equals(text, getResString(R.string.login_by_phone))) {
//                //代表当前是邮箱登录，那么需要切换到手机号登录
//
//                lmr_et_account.switchToPhone()
//                lmr_et_password.leftInVisible()
//                tv_switch_login.text = getResString(R.string.login_by_email)
//                lmr_et_account.setMiddleContent(MessageConstants.Empty)
//                lmr_et_password.setMiddleContent(MessageConstants.Empty)
//
//
//            } else {
//                // 否则当前是手机号登录，需要切换到邮箱的登录
//
//                lmr_et_account.switchToEmail()
//                //修改文字为"用邮箱登录"
//                lmr_et_password.leftGone()
//                tv_switch_login.text = cgetResString(R.string.login_by_phone)
//                lmr_et_account.setMiddleContent(MessageConstants.Empty)
//                lmr_et_password.setMiddleContent(MessageConstants.Empty)
//
//            }

        }
        RxView.clicks(btn_login).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                //:判断当前是否输入手机号
                val phoneNumber = lmr_et_account.getMiddleContent()
                if (StringTool.isEmpty(phoneNumber)) {
                    showToast(getResString(R.string.please_input_phone_number))
                    return@subscribe
                }
                //2:判断当前是否输入密码
                val password = lmr_et_password.getMiddleContent()
                if (StringTool.isEmpty(password)) {
                    showToast(getResString(R.string.please_input_password))
                    return@subscribe
                }
                presenter.login(phoneNumber!!, phoneCode.toInt(), password!!)

            },
                { e -> BaseException.print(e) })
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
                                lmr_et_account.setLeftContent(phoneCode)
                            }
                        }

                    }

                }
                Constants.RequestCode.LANGUAGE_SWITCH_REQUEST_CODE -> {

                }
                Constants.RequestCode.FORGOT_PASSWORD_REQUEST_CODE -> {
                }
            }
        }
    }

    override fun loginSuccess(userResultBean: UserResultBean?) {
        intentToActivity(MainAty::class.java, true)
    }

    override fun loginFailure(msg: String) {
        showToast(msg)
    }


}