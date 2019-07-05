package cn.wopaipai.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.catherine.token.tool.language.LanguageTool
import cn.wopaipai.R
import cn.wopaipai.adapter.TypeSwitchingAdapter
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseAty
import cn.wopaipai.bean.TypeSwitchingBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.listener.OnLanguageItemSelectListener
import cn.wopaipai.manager.intentToActivity
import cn.wopaipai.manager.returnResult
import cn.wopaipai.tool.ActivityTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.PreferenceTool
import cn.wopaipai.tool.StringTool
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.aty_language_switch.*
import kotlinx.android.synthetic.main.include_header.*
import java.util.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-12 16:25
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +   语言切换
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class LanguageSwitchAty : BaseAty() {

    private lateinit var typeSwitchingAdapter: TypeSwitchingAdapter

    private var fromLogin = false// 是否是从login页面进入
    override fun getArgs(bundle: Bundle?) {
        bundle ?: return
        fromLogin = bundle.getBoolean(Constants.KeyMaps.FROM_LOGIN, false)

    }

    override fun getLayoutRes(): Int = R.layout.aty_language_switch
    override fun initViews() {
        ActivityTool.addActivity(this)
        tv_title.text = getResString(R.string.select_Language)
        ib_back.visibility = View.VISIBLE
        setAdapter()
    }

    override fun initData() {
    }

    override fun initListener() {
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
    }

    private fun setAdapter() {
        val currentLanguage = LanguageTool.getCurrentLanguageString(BaseApplication.context)
        val typeSwitchingBeans = ArrayList<TypeSwitchingBean>()
        val typeSwitchingBeanCN = TypeSwitchingBean(
            getResString(R.string.language_chinese_simplified),
            Constants.Language.CN,
            StringTool.equals(currentLanguage, Constants.Language.CN)
        )
        val typeSwitchingBeanEN = TypeSwitchingBean(
            getResString(R.string.language_english),
            Constants.Language.EN,
            StringTool.equals(currentLanguage, Constants.Language.EN)
        )
        typeSwitchingBeans.add(typeSwitchingBeanCN)
        typeSwitchingBeans.add(typeSwitchingBeanEN)
        typeSwitchingAdapter = TypeSwitchingAdapter(this, typeSwitchingBeans)
        typeSwitchingAdapter.setSettingItemSelectListener(onItemSelectListenerLanguageSwitch)
        rv_setting.setHasFixedSize(true)
        rv_setting.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        rv_setting.adapter = typeSwitchingAdapter

    }

    private val onItemSelectListenerLanguageSwitch = object : OnLanguageItemSelectListener {
        override fun <T> onItemSelect(type: T?, from: String) {
            if (type == null) {
                return
            }
            LogTool.d(tag, " switch.....$type")
            if (type is TypeSwitchingBean) {
                val typeSwitchingBean = type as TypeSwitchingBean? ?: return
                LogTool.d(tag, " switch.....$typeSwitchingBean")
                val languageType = typeSwitchingBean.type
                PreferenceTool.getInstance()
                    .saveString(Constants.Preference.LANGUAGE_TYPE, languageType)
                //更新當前的語言環境
                LanguageTool.setApplicationLanguage(
                    BaseApplication.context,
                    LanguageTool.getLanguageLocal(BaseApplication.context)
                )
                //如果不重启当前界面，是不会立马修改的
                ActivityTool.removeAllActivity()
                val bundle = Bundle()
                bundle.putString(Constants.KeyMaps.From, Constants.ValueMaps.FROM_LANGUAGE_SWITCH)
                intentToActivity(
                    bundle,
                    if (fromLogin) LoginAty::class.java else MainAty::class.java,
                    true,
                    isClearTask = true
                )
            }

        }

        override fun changeItem(isChange: Boolean) {}
    }

    override fun onBackPressed() {
        returnResult(true)

    }

}