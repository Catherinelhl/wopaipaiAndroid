package cn.wopaipai.view.edittext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import cn.wopaipai.R
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.listener.OnTextChangeListener
import cn.wopaipai.manager.intentActivityForResult
import cn.wopaipai.tool.StringTool
import cn.wopaipai.ui.activity.CountryCodeAty
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.aty_login.*
import kotlinx.android.synthetic.main.layout_edittext_left_middle_right.view.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 19:52
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.view
+--------------+---------------------------------
+ description  +  自定义View：左中右结构的，主要是登录界面
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class LeftMiddleRightEditText(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {

    var isPwd: Boolean = false

    var onItemSelectListener: OnItemSelectListener? = null
    var onTextChangeListener: OnTextChangeListener? = null

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_edittext_left_middle_right, this, true)
        //获取自定义属性的值
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.editViewLeftMiddleRight)
        if (typedArray != null) {
            /*声明需要显示的标题以及hint*/

            val hint = typedArray.getString(R.styleable.editViewLeftMiddleRight_hint)
            //是否是密码框是隐藏的，如果是密码框，那么需要显示出来
            isPwd =
                typedArray.getBoolean(R.styleable.editViewLeftMiddleRight_isPwd, false)
            val hintColor = typedArray.getInt(
                R.styleable.editViewLeftMiddleRight_hintColor,
                context.resources.getColor(R.color.black30_222222)
            )
            val textColor = typedArray.getInt(
                R.styleable.editViewLeftMiddleRight_textColor,
                context.resources.getColor(R.color.black_222222)
            )
            val textSize = typedArray.getFloat(R.styleable.editViewLeftMiddleRight_textSize, 14f)
            val rightTextVisible =
                typedArray.getInt(R.styleable.editViewLeftMiddleRight_rightTextVisible, 0)
            val leftTextVisible =
                typedArray.getInt(R.styleable.editViewLeftMiddleRight_leftTextVisible, 0)
            val behaviour = typedArray.getInt(R.styleable.editViewLeftMiddleRight_behaviour, 0)
            hint?.let {
                //如果当前的hint不为空，那么对输入框进行赋值
                et_middle_content.hint = hint
            }

            //显示密码框布局
            rl_pwd.visibility = if (isPwd) View.VISIBLE else View.GONE
            et_middle_content.visibility = if (isPwd) View.GONE else View.VISIBLE
            et_middle_content.inputType = InputType.TYPE_CLASS_NUMBER
            when (leftTextVisible) {
                0 -> {
                    //visible
                    tv_left.visibility = View.VISIBLE
                }
                1 -> {
                    //gone
                    tv_left.visibility = View.GONE

                }
                2 -> {
                    //invisible
                    tv_left.visibility = View.INVISIBLE

                }

            }
            when (rightTextVisible) {
                0 -> {
                    //visible
                    cb_right_pwd.visibility = View.VISIBLE
                }
                1 -> {
                    //gone
                    cb_right_pwd.visibility = View.GONE

                }
                2 -> {
                    //invisible
                    cb_right_pwd.visibility = View.INVISIBLE

                }
            }

        }
        initListener()
    }


    @SuppressLint("CheckResult")
    fun initListener() {
        cb_right_pwd!!.setOnCheckedChangeListener { buttonView, isChecked ->
            val text = et_middle_password!!.text.toString()
            if (StringTool.isEmpty(text)) {
                return@setOnCheckedChangeListener
            }
            et_middle_password!!.inputType = if (isChecked)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD//设置当前私钥显示不可见
            et_middle_password!!.setSelection(text.length)

        }

        RxView.clicks(tv_left).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                //跳转到城市code选择
                onItemSelectListener?.let {
                    it.onItemSelect(
                        MessageConstants.Empty,
                        Constants.ActionFrom.SELECT_COUNTRY_CODE
                    )
                }
            }, {})

        et_middle_password!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    val content = s.toString()
                    onTextChangeListener?.let {
                        it.onTextChange(content)
                        if (StringTool.notEmpty(content)) {
                            et_middle_password!!.setSelection(content.length)
                        }
                    }

                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        et_middle_password!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    val content = s.toString()
                    onTextChangeListener?.let {
                        it.onTextChange(content)
                        if (content.length >= Constants.ValueMaps.PASSWORD_MIN_LENGTH) {
                            onTextChangeListener!!.onComplete(content)
                        }
                    }


                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }


    @SuppressLint("SetTextI18n")
    fun setLeftContent(countryCode: String?) {
        tv_left?.let {
            tv_left.text = context.resources.getString(R.string.plus_sign) + countryCode
        }
    }

    fun setMiddleContent(content: String?) {
        et_middle_password?.let {
            it.setText(content)
        }
        et_middle_content?.let {
            it.setText(content)
        }
    }
    fun setHint(content: String?) {
        et_middle_password?.let {
            it.hint = content
        }
        et_middle_content?.let {
            it.hint = content
        }
    }


    /**
     * 得到左边文本内容
     */
    fun getLeftContent(): String {
        var message = tv_left.text.toString()
        //将+号分离出来
        if (StringTool.notEmpty(message)) {
            message = message.substring(1)
        }
        return message
    }

    /**
     * 得到右边文本内容
     */
    fun getRightContent(): String {
        return MessageConstants.Empty
    }

    /**
     * 得到中间文本内容
     */
    fun getMiddleContent(): String? {
        var message = MessageConstants.Empty
        if (isPwd) {
            et_middle_password?.let {
                message = it.text.toString()
            }
        } else {
            et_middle_content?.let {
                message = it.text.toString()
            }
        }
        return message
    }

    /**
     * 切换成邮箱登录
     */
    fun switchToEmail() {
        leftGone()
        //键盘切换成正常模式
        et_middle_content.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

    }


    /**
     * 切换成手机登录
     */
    fun switchToPhone() {
        tv_left.visibility = View.VISIBLE
        et_middle_content.hint = context.resources.getString(R.string.phone_account)
        // 键盘切换成数字输入
        et_middle_content.inputType = InputType.TYPE_CLASS_PHONE

    }

    fun leftGone() {
        tv_left.visibility = View.GONE
        et_middle_content.hint = context.resources.getString(R.string.email_account)


    }

    fun leftInVisible() {
        tv_left.visibility = View.INVISIBLE
        et_middle_content.hint = context.resources.getString(R.string.phone_account)

    }

    fun setInputType(inoutType: Int) {
        // 键盘切换成数字输入
        et_middle_password.inputType=inoutType
    }

}

