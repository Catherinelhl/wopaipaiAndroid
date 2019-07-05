package cn.wopaipai.ui.activity

import android.os.Bundle
import android.view.View
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseAty
import cn.wopaipai.constant.Constants
import cn.wopaipai.manager.returnResult
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.aty_myself_profile.*
import kotlinx.android.synthetic.main.include_header.*
import kotlinx.android.synthetic.main.include_header.ib_back
import kotlinx.android.synthetic.main.title.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-23 19:29
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class MySelfProfileActivity : BaseAty() {
    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_myself_profile
    override fun initViews() {
        set_title.text = getString(R.string.title_personal_data)
        ib_back.visibility = View.VISIBLE
        val profileBean = BaseApplication.profileBean
        profileBean?.let {
            tv_name.text = it.nickName
            tv_phone_number.text = it.phoneNumber
            tv_email.text = it.email
        }

    }

    override fun initData() {
    }

    override fun initListener() {
        RxView.clicks(ib_back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(o: Any) {
                    activity.returnResult(true)

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }
}