package cn.wopaipai.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.catherine.token.http.retrofit.RetrofitFactory
import cn.catherine.token.tool.json.GsonTool
import cn.wopaipai.R
import cn.wopaipai.adapter.GetCommunityUserAdapter
import cn.wopaipai.base.*
import cn.wopaipai.bean.GetCommunityUser
import cn.wopaipai.bean.GetCommunityUserListBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.gson.ResponseJson
import cn.wopaipai.http.HttpApi
import cn.wopaipai.requester.AccountRequester
import cn.wopaipai.tool.LogTool
import cn.wopaipai.utils.MD5
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.ResourceObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.aty_my_community.*
import kotlinx.android.synthetic.main.aty_my_community.srl_data
import kotlinx.android.synthetic.main.aty_order_manager.*
import kotlinx.android.synthetic.main.include_header.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-25 15:44
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  我的社群
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class MyCommunityAty : BaseAty(), BaseContract.View {

    private var communityUserList: MutableList<GetCommunityUser> = arrayListOf()

    override fun getLayoutRes(): Int = R.layout.aty_my_community

    override fun initViews() {
        ib_back.visibility = View.VISIBLE
        tv_title.text = resources.getText(R.string.title_my_community)

        // 设置加载按钮的形态
        srl_data.setColorSchemeResources(
            R.color.button_color,
            R.color.button_color
        )
        srl_data.setSize(SwipeRefreshLayout.DEFAULT)

    }

    override fun initData() {
        refreshView()
    }

    override fun initListener() {
        srl_data.setOnRefreshListener {
            srl_data.isRefreshing = false
            refreshView()
        }
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

    override fun getArgs(bundle: Bundle?) {
    }

    @SuppressLint("CheckResult")
    private fun refreshView() {
        showLoading()
        val getCommunityUserAdapter =
            GetCommunityUserAdapter(R.layout.recycle_my_community, communityUserList)
        rcl_recommend.layoutManager = LinearLayoutManager(this)
        rcl_recommend.adapter = getCommunityUserAdapter
        // 获取社群用户
        var passportId = BaseApplication.getPassportId()
        var sign = MD5.Md5Sign(passportId.toString())
        AccountRequester.getCommunity(passportId, sign)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    communityUserList.clear()
                    val code = it.statusCode
                    if (code == MessageConstants.CODE_200) {
                        val communityUserListBean = it.data as GetCommunityUserListBean?
                        if (communityUserListBean != null) {
                            LogTool.d(tag, communityUserListBean)
                            val total = communityUserListBean.total
                            tv_total_person.text = total.toString()
                            for (i in 0 until communityUserListBean.directPushUserList.size) {
                                val communityUser = GetCommunityUser()
                                communityUser.phoneNumber =
                                    communityUserListBean.directPushUserList[i].phoneNumber
                                communityUser.levelName =
                                    communityUserListBean.directPushUserList[i].levelName
                                communityUserList.add(communityUser)
                            }
                            tv_recommend_number.text =
                               getResString(R.string.recommendation_person) + "(" + communityUserList.size + ")"
                            getCommunityUserAdapter.notifyDataSetChanged()
                        } else {
                            showToast(getResString(R.string.no_more_data))
                        }

                    } else {
                        showToast(BasePresenterImp().getExceptionInfoByCode(code))
                    }
                } else {
                    showToast(getResString(R.string.no_more_data))
                }

            }, {
                BaseException.print(it)
                hideLoading()
            }, {
                hideLoading()
            })
    }
}