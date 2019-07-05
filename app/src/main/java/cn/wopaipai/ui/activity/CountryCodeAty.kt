package cn.wopaipai.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import cn.catherine.token.tool.language.LanguageTool
import cn.wopaipai.R
import cn.wopaipai.adapter.CountryCodeAdapter
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseAty
import cn.wopaipai.bean.CountryCodeBean
import cn.wopaipai.bean.CountryCodeLocalBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.listener.FilterListener
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.manager.hideSoftKeyBoardByTouchView
import cn.wopaipai.manager.hideSoftKeyboard
import cn.wopaipai.manager.returnResult
import cn.wopaipai.tool.LogTool
import cn.wopaipai.ui.contract.CountryCodeContract
import cn.wopaipai.ui.presenter.CountryCodePresenterImp
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.aty_country_code.*
import kotlinx.android.synthetic.main.include_header.*
import java.util.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 20:57
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  Activity:城市区号
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class CountryCodeAty : BaseAty(), CountryCodeContract.View {
    override fun getCountryCodeLocalSuccess(countryCodesLocal: ArrayList<CountryCodeLocalBean.CountryCode>) {
    }


    private lateinit var adapter: CountryCodeAdapter
    private var countryCodeList: MutableList<CountryCodeBean.CountryCode> = arrayListOf()
    private val presenter: CountryCodeContract.Presenter by lazy { CountryCodePresenterImp(this) }

    override fun getArgs(bundle: Bundle?) {

    }

    override fun getLayoutRes(): Int = R.layout.aty_country_code

    override fun initViews() {
        v_title_line.visibility = View.INVISIBLE
        //获取本地城市区号配置信息
        presenter.getCountryCode(LanguageTool.getCurrentLanguageString(BaseApplication.context))
        ib_back.visibility = View.VISIBLE
        tv_title.text = getResString(R.string.country_code_title)
    }

    override fun initData() {
    }

    override fun initListener() {
        ll_list.hideSoftKeyBoardByTouchView(this)
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

        /**
         * 对编辑框添加文本改变监听，搜索的具体功能在这里实现
         * 很简单，文本该变的时候进行搜索。关键方法是重写的onTextChanged（）方法。
         */
        et_select_content.addTextChangedListener(object : TextWatcher {

            /**
             *
             * 编辑框内容改变的时候会执行该方法
             */
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                // 如果adapter不为空的话就根据编辑框中的内容来过滤数据
                adapter.filter.filter(s)
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun initAdapter() {
        adapter = CountryCodeAdapter(context, countryCodeList)
        adapter.setOnItemSelectListener(onItemSelectListenerCountryCode)
        adapter.setFilterListener(filterListener)
        rv_list.adapter = adapter
        rv_list.setHasFixedSize(true)
        rv_list.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private val onItemSelectListenerCountryCode = object : OnItemSelectListener {
        override fun <T> onItemSelect(type: T?, from: String) {
            if (type != null) {
                if (type is CountryCodeBean.CountryCode) {
                    setResult(type as CountryCodeBean.CountryCode?)
                }
            }
        }
    }

    private val filterListener = FilterListener { }
    fun setResult(countryCodeLocal: CountryCodeBean.CountryCode?) {
        hideSoftKeyboard()
        val intent = Intent()
        val bundle = Bundle()
        bundle.putSerializable(Constants.KeyMaps.SELECT_COUNTRY_CODE, countryCodeLocal)
        intent.putExtras(bundle)
        this.setResult(RESULT_OK, intent)
        this.finish()
    }

    override fun getCountryCodeSuccess(countryCodesBean: ArrayList<CountryCodeBean.CountryCode>) {
        this.countryCodeList.clear()
        this.countryCodeList.addAll(countryCodesBean)
        initAdapter()
    }


    override fun getCountryCodeFailure(msg: String) {
        showToast(msg)
    }
}