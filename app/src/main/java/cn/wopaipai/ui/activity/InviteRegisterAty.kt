package cn.wopaipai.ui.activity

import android.os.Bundle
import android.view.View
import cn.catherine.token.http.retrofit.RetrofitFactory
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseAty
import cn.wopaipai.base.BaseException
import cn.wopaipai.base.BasePresenterImp
import cn.wopaipai.bean.InviteResterBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.gson.ResponseJson
import cn.wopaipai.http.HttpApi
import cn.wopaipai.tool.LogTool
import cn.wopaipai.utils.MD5
import cn.wopaipai.utils.ZxingUtils
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.ResourceObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.aty_inviate_rester.*
import kotlinx.android.synthetic.main.include_header.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-25 15:26
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  邀请注册
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class InviteRegisterAty : BaseAty() {
    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_inviate_rester

    override fun initViews() {
        ib_back.visibility = View.VISIBLE
        tv_title.text = getResString(R.string.invite_to_register)

    }

    override fun initData() {
        val passportId = BaseApplication.getPassportId()
        val sign = MD5.Md5Sign(passportId.toString() + MessageConstants.Empty)
        val wallet = RetrofitFactory.getAPIInstance().create(HttpApi::class.java)
            .inviteRegister(passportId, sign)
        wallet.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ResourceObserver<ResponseJson>() {
                override fun onNext(responseJson: ResponseJson) {
                    val code = responseJson.statusCode
                    if (code == MessageConstants.CODE_200) {
                        val registerBeans = GsonTool.convert(
                            GsonTool.string(responseJson.data),
                            InviteResterBean::class.java
                        )
                        LogTool.d(tag, registerBeans)
                        tv_recommend_code.text =
                            getResString(R.string.title_recommendation_code) + registerBeans.pCode
                        val bitmap =
                            ZxingUtils.createBitmap(
                                registerBeans.qrRegisterUrl + MessageConstants.Empty,
                                200,
                                200
                            )
                        iv_invite.setImageBitmap(bitmap)
                        tv_invite_msg.text =getResString(R.string.scan_code_and_register_download)
                    } else {
                        showToast(BasePresenterImp().getExceptionInfoByCode(code))
                    }
                }

                override fun onError(e: Throwable) {
                    showToast(e.toString())
                }

                override fun onComplete() {

                }
            })
    }

    override fun initListener() {
        RxView.clicks(ib_back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(o: Any) {
                    finish()
                }

                override fun onError(e: Throwable) {
                    BaseException.print(e)
                }

                override fun onComplete() {

                }
            })
    }
}