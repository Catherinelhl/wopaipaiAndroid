package cn.wopaipai.ui.presenter

import android.annotation.SuppressLint
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.R
import cn.wopaipai.base.BaseException
import cn.wopaipai.base.BasePresenterImp
import cn.wopaipai.bean.CountryCodeBean
import cn.wopaipai.bean.CountryCodeLocalBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.gson.ResponseAnyJson
import cn.wopaipai.requester.GetCountryCodeRequester
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.StringTool
import cn.wopaipai.tool.file.FilePathTool
import cn.wopaipai.tool.file.ResourceTool
import cn.wopaipai.ui.contract.CountryCodeContract
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-12 09:30
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.presenter
+--------------+---------------------------------
+ description  +  获取城市区号
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class CountryCodePresenterImp(val view: CountryCodeContract.View) : CountryCodeContract.Presenter,
    BasePresenterImp() {
    private val TAG = CountryCodePresenterImp::class.java.simpleName

    /**
     *获取城市区号列表
     */
    override fun getCountryCode(language: String) {
//        getCountryCodeFromLocal(language)
        getCountryCodeFromNet(language)
    }

    @SuppressLint("CheckResult")
    fun getCountryCodeFromNet(language: String) {
        view.showLoading()
        GetCountryCodeRequester.getCountryCode()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                LogTool.d(TAG, it)
                if (it != null) {
                    when (val code = it.statusCode) {
                        MessageConstants.CODE_200 -> {
                            val countryCodesTemp: List<CountryCodeBean.CountryCode>? =it.data as List<CountryCodeBean.CountryCode>
                            if (ListTool.noEmpty(countryCodesTemp)) {
                                val countryCodes = ArrayList<CountryCodeBean.CountryCode>()
                                for (countryCode in countryCodesTemp!!) {
                                    val name = countryCode.countryName
                                    val code = countryCode.countryPhone
                                    if (StringTool.isEmpty(name)
                                        || StringTool.isEmpty(code)
                                        || StringTool.equals(code, MessageConstants.NULL)
                                        || StringTool.equals(name, MessageConstants.NULL)
                                    ) {
                                        continue
                                    }
                                    countryCodes.add(countryCode)
                                }
                                view.getCountryCodeSuccess(countryCodes)

                            } else {
                                view.getCountryCodeFailure(getString(R.string.no_more_data))

                            }
                        }
                        else -> {
                            view.getCountryCodeFailure(getExceptionInfoByCode(code))
                        }

                    }
                } else {
                    view.getCountryCodeFailure(getString(R.string.get_data_failure))

                }


            },
                { e ->
                    BaseException.print(e)
                    view.hideLoading()
                },
                {
                    view.hideLoading()
                })

    }

    fun getCountryCodeFromLocal(language: String) {
        val json = ResourceTool.getJsonFromAssets(FilePathTool.getCountryCodeFilePath(language))
        if (StringTool.notEmpty(json)) {
            val countryCodeBean = GsonTool.convert(json, CountryCodeLocalBean::class.java)
            if (countryCodeBean != null) {
                val countryCodesTemp = countryCodeBean!!.getData()
                if (ListTool.noEmpty(countryCodesTemp)) {
                    val countryCodes = ArrayList<CountryCodeLocalBean.CountryCode>()
                    for (countryCode in countryCodesTemp) {
                        val name = countryCode.countryName
                        val code = countryCode.phoneCode
                        if (StringTool.isEmpty(name)
                            || StringTool.isEmpty(code)
                            || StringTool.equals(code, MessageConstants.NULL)
                            || StringTool.equals(name, MessageConstants.NULL)
                        ) {
                            continue
                        }
                        countryCodes.add(countryCode)
                    }
                    view.getCountryCodeLocalSuccess(countryCodes)
                } else {
                    view.getCountryCodeFailure(getString(R.string.no_more_data))
                }

            } else {
                view.getCountryCodeFailure(getString(R.string.no_more_data))

            }

        }
    }
}