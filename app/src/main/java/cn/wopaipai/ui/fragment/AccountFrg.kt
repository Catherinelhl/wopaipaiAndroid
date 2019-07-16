package cn.wopaipai.ui.fragment

import android.Manifest
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import cn.wopaipai.BaseDialog
import cn.wopaipai.BuildConfig
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseFrg
import cn.wopaipai.bean.ProfileBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.manager.intentToActivity
import cn.wopaipai.manager.showToast
import cn.wopaipai.tool.StringTool
import cn.wopaipai.tool.VersionTool
import cn.wopaipai.ui.activity.*
import cn.wopaipai.ui.contract.AccountContract
import cn.wopaipai.ui.my.RevenueRewardActivity
import cn.wopaipai.ui.presenter.AccountPresenterImp
import kotlinx.android.synthetic.main.frg_account.*


/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-12 20:39
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.fragment
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class AccountFrg : BaseFrg(), AccountContract.View {

    private val TAG = AccountFrg::class.java.simpleName

    private val presenter by lazy { AccountPresenterImp(this) }
    private var customer: String? = null//当前的客服电话
    override fun getLayoutRes(): Int = R.layout.frg_account

    override fun initViews(view: View) {
        getProfile()
        // 如果当前是debug版本，显示当前的版本信息
        val sb = StringBuffer("Version:" + VersionTool.getVersionName(context!!))
        if (BuildConfig.Version) {
            sb.append(
                "(" + VersionTool.getVersionCode(
                    context!!
                ) + ")"
            )
        }
        tv_version.text = sb

    }

    private fun getProfile() {
        presenter.getProfile()
    }

    override fun getArgs(bundle: Bundle?) {
    }

    override fun initListener() {
        ly_user_msg.setOnClickListener {
            //个人资料
            activity.intentToActivity(MySelfProfileActivity::class.java)
        }
        ly_my_wallet.setOnClickListener {
            // 钱包账户
            activity.intentToActivity(WalletAty::class.java)
        }
        ly_my_tg.setOnClickListener {
            // 智能托管
            activity.intentToActivity(
                SmartHostingAty::class.java
            )
        }
        ly_my_invite.setOnClickListener {
            // 邀请注册
            activity.intentToActivity(InviteRegisterAty::class.java)
        }
        ly_my_order.setOnClickListener {
            // 订单管理
            activity.intentToActivity(OrderManagerAty::class.java)
        }
        ly_my_communte.setOnClickListener {
            // 我的社群
            activity.intentToActivity(MyCommunityAty::class.java)
        }
        ly_my_comm_money.setOnClickListener {
            // 社区奖励
            activity.intentToActivity(RevenueRewardActivity::class.java)
        }
        ly_my_set.setOnClickListener {
            // 设置中心
            activity.intentToActivity(SettingAty::class.java)
        }
        ly_my_conster.setOnClickListener {
            activity ?: return@setOnClickListener
            // 联系客服,改为弹框式
            customer?.let {
                BaseDialog().showDialog(
                    activity!!,
                    getString(R.string.contact_customer_service),
                    getResString(R.string.cancel),
                    getString(R.string.copy),
                    it,
                    object : BaseDialog.ConfirmClickListener {
                        override fun sure() {
                            val clipboardManager =
                                activity!!.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
                            clipboardManager!!.text = customer.toString()
                            activity.showToast(getResString(R.string.copy_success))
                        }


                        override fun cancel() {
                        }
                    })
            }

            //TODO 检查手机有没有开启电话权限
//           checkPhonePermission()
        }
    }

    override fun getProfileSuccess(profileBean: ProfileBean?) {
        profileBean?.let {
            tv_name.text = profileBean.nickName
            tv_phone_number.text = profileBean.phoneNumber
            val isVip = profileBean.isVip
            iv_vip.visibility = if (isVip) View.VISIBLE else View.GONE
            //判断用户的级别
            when (profileBean.level) {
                Constants.Level.NORMAL -> {
                    tv_level.visibility = View.GONE
                }
                Constants.Level.MANAGER -> {
                    tv_level.visibility = View.VISIBLE
                    tv_level.setBackgroundResource(R.drawable.vip_blue_bg)
                    tv_level.text = getResString(R.string.manager)

                }
                Constants.Level.DIRECTOR -> {
                    tv_level.visibility = View.VISIBLE
                    tv_level.text = getResString(R.string.director)

                }
                else -> {
                    tv_level.visibility = View.GONE

                }
            }
            customer = it.customer
//判读当前的客户电话是否为空，如果为空，隐藏掉客服电话
            if (StringTool.isEmpty(customer)) {
                ly_my_conster.visibility = View.GONE
            } else {
                ly_my_conster.visibility = View.VISIBLE
            }
        }
    }

    override fun getProfileFailure(msg: String) {
        activity.showToast(msg)
    }

    /**
     * 检测当前的通话权限
     */
    private fun checkPhonePermission() {

        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var checkCallPhonePermission = ContextCompat.checkSelfPermission(
                BaseApplication.context,
                Manifest.permission.CALL_PHONE
            );

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity!!, arrayOf(Manifest.permission.CALL_PHONE),
                    Constants.RequestCode.PERMISSIONS_REQUEST_CALL_PHONE_CODE
                )
                return
            } else {
                callPhone()
            }
        } else {
            callPhone()
            // 检查是否获得了权限（Android6.0运行时权限）
            if (ContextCompat.checkSelfPermission(
                    BaseApplication.context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 没有获得授权，申请授权
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity!!,
                        Manifest.permission.CALL_PHONE
                    )
                ) {
                    // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                    // 弹窗需要解释为何需要该权限，再次请求授权
                    activity.showToast(getResString(R.string.please_grant_phone_access))
                    // 帮跳转到该应用的设置界面，让用户手动授权
                    var intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    var uri = Uri.fromParts("package", activity!!.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } else {
                    // 不需要解释为何需要该权限，直接请求授权
                    ActivityCompat.requestPermissions(
                        activity!!,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        Constants.RequestCode.PERMISSIONS_REQUEST_CALL_PHONE_CODE
                    )
                }
            } else {
                // 已经获得授权，可以打电话
                callPhone()
            }
        }
    }

    private fun callPhone() {
        if (StringTool.isEmpty(customer)) {
            activity.showToast(getResString(R.string.phone_call_failed))
        } else {
            val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$customer"))//直接拨打电话
            startActivity(dialIntent)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.RequestCode.PERMISSIONS_REQUEST_CALL_PHONE_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // 授权成功，继续打电话
                    callPhone()
                } else {
                    // 授权失败！
                    activity.showToast(getResString(R.string.authorization_failure))
                }
            }
        }
    }
}