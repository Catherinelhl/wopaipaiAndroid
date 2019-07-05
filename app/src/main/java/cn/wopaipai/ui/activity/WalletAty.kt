package cn.wopaipai.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import cn.wopaipai.R
import cn.wopaipai.adapter.WalletAssetsAdapter
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseAty
import cn.wopaipai.base.BaseException
import cn.wopaipai.bean.CoinsBean
import cn.wopaipai.bean.WalletResponsesBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.StringTool
import cn.wopaipai.ui.contract.GetUserContract
import cn.wopaipai.ui.my.WalletListActivity
import cn.wopaipai.ui.presenter.GetUserWalletPresenterImp
import cn.wopaipai.utils.DecimalFormatUtils
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.aty_wallet.*
import kotlinx.android.synthetic.main.title_transparent.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-28 09:05
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.activity
+--------------+---------------------------------
+ description  +  钱包资产
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class WalletAty : BaseAty(), GetUserContract.View {

    private val presenter by lazy { GetUserWalletPresenterImp(this) }

    private var walletName: String? = null
    private var logoUrl: String? = null
    private var balance: Double = 0.toDouble()
    private var exUsdRate: Double = 0.toDouble()

    private var coinsBeans: MutableList<CoinsBean> = arrayListOf()
    private val walletAssetsAdapter: WalletAssetsAdapter by lazy {
        WalletAssetsAdapter(coinsBeans)
    }

    override fun getArgs(bundle: Bundle?) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_wallet

    override fun initViews() {
        // 设置加载按钮的形态
        srl_data.setColorSchemeResources(
            R.color.button_color,
            R.color.button_color
        )
        srl_data.setSize(SwipeRefreshLayout.DEFAULT)
        set_title.text = getResString(R.string.title_my_wallet_money)
        rcl_wallet.layoutManager = LinearLayoutManager(this)
        rcl_wallet.adapter = walletAssetsAdapter

    }

    override fun initData() {
        presenter.getUserWallet()
    }

    @SuppressLint("CheckResult")
    override fun initListener() {
        srl_data.setOnRefreshListener {
            srl_data.isRefreshing = false
            initData()
        }
        walletAssetsAdapter.setOnItemClickListener { adapter, view, position ->
            //                showToast(""+position);
            walletName = coinsBeans[position].coinCode
            logoUrl = coinsBeans[position].logoUrl
            balance = coinsBeans[position].balance
            exUsdRate = coinsBeans[position].exUsdRate
            val bundle = Bundle()
            bundle.putString("wallet_name", walletName)
            bundle.putString("logUrl", logoUrl)
            bundle.putDouble("balance", balance)
            bundle.putDouble("exUsdRate", exUsdRate)
            var intent = Intent()
            intent.putExtras(bundle)
            intent.setClass(this, WalletListActivity::class.java)
            startActivityForResult(intent, Constants.RequestCode.WALLET_ASSET_DETAIL_REQUEST_CODE)
        }
        RxView.clicks(ib_back).throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                finish()
            }, { e -> BaseException.print(e) })
    }

    @SuppressLint("SetTextI18n")
    override fun getUserWalletSuccess(walletResponsesBean: WalletResponsesBean) {
        coinsBeans.clear()
        tv_total_coin.text = "≈$" + DecimalFormatUtils.getDecimalFormaTwo(walletResponsesBean.total)
        coinsBeans.addAll(walletResponsesBean.coins)
        //如果当前的币种情况不为空，那么存储当前的余额
        if (ListTool.noEmpty(coinsBeans)) {
            for (i in coinsBeans.indices) {
                val coinsBean = coinsBeans[i]
                val name = coinsBean.coinCode
                if (StringTool.equals(name, MessageConstants.AUCTION_TYPE)) {
                    val balance = coinsBean.balance
                    BaseApplication.balance = balance
                }
            }
        }
        walletAssetsAdapter.notifyDataSetChanged()
    }

    override fun getUserWalletFailure(msg: String) {
        showToast(msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.RequestCode.WALLET_ASSET_DETAIL_REQUEST_CODE -> {
                //更新当前界面的数据
                presenter.getUserWallet()
            }

        }
    }
}