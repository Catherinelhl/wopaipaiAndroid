package cn.wopaipai.ui.presenter

import android.annotation.SuppressLint
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.R
import cn.wopaipai.base.BaseException
import cn.wopaipai.base.BasePresenterImp
import cn.wopaipai.bean.BannerBean
import cn.wopaipai.bean.ProductListBean
import cn.wopaipai.bean.ProductsBean
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.requester.GetCountryCodeRequester
import cn.wopaipai.requester.MainRequester
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.ui.contract.MainContract
import cn.wopaipai.utils.MD5
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-19 01:41
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.presenter
+--------------+---------------------------------
+ description  +  首页
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class MainPresenterImp(val view: MainContract.View) : MainContract.Presenter, BasePresenterImp() {

    private val TAG = MainPresenterImp::class.java.simpleName


    @SuppressLint("CheckResult")
    override fun getMainBanner() {
        MainRequester.getMainBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    var code = it.statusCode
                    val mainBannerBean =
                        GsonTool.convert<List<BannerBean>>(
                            GsonTool.string(it.data),
                            object : TypeToken<List<BannerBean>>() {}.type
                        )
                    when (code) {
                        MessageConstants.CODE_200 -> {
                            if (ListTool.noEmpty(mainBannerBean)) {
                                view.getMainBannerSuccess(mainBannerBean!!)
                            }
                        }
                        else -> {
                            view.getMainBannerFailure(getExceptionInfoByCode(code))
                        }
                    }
                }

            }, { e ->
                LogTool.e(TAG, e.toString())
                BaseException.print(e)

            }, {
            })
    }

    @SuppressLint("CheckResult")
    override fun getProducts(pageSize: Int, pageIndex: Int) {
        view.showLoading()
        val sign = MD5.Md5Sign("$pageIndex")
        MainRequester.getProducts(pageSize, pageIndex, sign)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    GsonTool.logInfo(
                        TAG,
                        MessageConstants.LogInfo.RESPONSE_JSON,
                        "getProducts:",
                        it
                    )
                    var code = it.statusCode
                    when (code) {
                        MessageConstants.CODE_200 -> {
                            val productListBean =
                                GsonTool.convert(
                                    GsonTool.string(it.data),
                                    ProductListBean::class.java
                                )
                            view.getProductsSuccess(productListBean)
                        }
                        else -> {
                            view.getProductsFailure(getExceptionInfoByCode(code))
                        }
                    }
                } else {
                    view.getProductsFailure(getString(R.string.get_data_failure))

                }

            }, { e ->
                LogTool.e(TAG, e.toString())
                BaseException.print(e)
                view.hideLoading()

            }, {
                view.hideLoading()
            })
    }

}