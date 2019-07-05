package cn.wopaipai.ui.fragment

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import cn.wopaipai.R
import cn.wopaipai.adapter.HotCommoditiesAdapter
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseFrg
import cn.wopaipai.bean.BannerBean
import cn.wopaipai.bean.ProductListBean
import cn.wopaipai.bean.ProductsBean
import cn.wopaipai.listener.GridViewLoadListener
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.manager.showToast
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.ui.contract.MainContract
import cn.wopaipai.ui.presenter.MainPresenterImp
import cn.wopaipai.utils.GlideImgManager
import cn.wopaipai.view.viewpager.IconPagerFragmentAdapter
import kotlinx.android.synthetic.main.frg_main.*
import kotlinx.android.synthetic.main.view_main_banner.*
import kotlinx.android.synthetic.main.view_main_banner.view.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-12 18:09
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.fragment
+--------------+---------------------------------
+ description  +  Fragment:首页
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class MainFrg : BaseFrg(), MainContract.View {

    private val TAG = MainFrg::class.java.simpleName
    private val presenter by lazy { MainPresenterImp(this) }

    private var bannerBeans: ArrayList<BannerBean> = arrayListOf()
    private var viewList: MutableList<View> = arrayListOf()
    private val viewFragmentAdapter: IconPagerFragmentAdapter by lazy {
        IconPagerFragmentAdapter(
            viewList
        )
    }//图片页码的viewpager适配
    private var productsBeans: ArrayList<ProductsBean> = arrayListOf()//热门商品 的更新数据
    //    private var productsBeansAll: ArrayList<ProductsBean> = arrayListOf()//热门商品 的所有数据
    private var fromLoading: Boolean = false
    private var canLoadingMore: Boolean = false// 是否能够加载更多
    private val hotCommoditiesAdapter: HotCommoditiesAdapter by lazy {
        HotCommoditiesAdapter(
            context
        )
    }
    private val gridViewLoadListener by lazy { GridViewLoadListener(autoLoadCallBack) }
    private var pageSize: Int = 20//页大小
    private var pageIndex: Int = 1//页索引

    override fun getLayoutRes(): Int = R.layout.frg_main
    override fun initViews(view: View) {
        // 设置加载按钮的形态
        srl_data.setColorSchemeResources(
            R.color.button_color,
            R.color.button_color

        )
        srl_data.setSize(SwipeRefreshLayout.DEFAULT)
        //为了避免重加载，所以需要清空刷新数据
        if (viewList.size > 0) {
            viewList.clear()
            viewFragmentAdapter.notifyDataSetChanged()
        }
        gv_hot_commodities.setOnScrollListener(gridViewLoadListener)
        initGridViewData()
        refreshView()

    }

    private fun initMainTopBanner() {
        viewList.clear()
        for (i in bannerBeans.indices) {
            var view = LayoutInflater.from(activity).inflate(R.layout.view_main_banner, null)
            GlideImgManager.displayImage(
                activity,
                bannerBeans[i].imageUrl,
                view.iv_broadcast_banner
            )
            viewList.add(view)
        }
        vp_main_banner.offscreenPageLimit = viewList.size
        viewFragmentAdapter.notifyDataSetChanged()
        vp_main_banner.adapter = viewFragmentAdapter
        vp_main_banner.currentItem = 0
        icon_pager_indicator.setViewPager(vp_main_banner)
        icon_pager_indicator.setCurrentItem(0)
        icon_pager_indicator.invalidate()

    }

    private fun initGridViewData() {
        if (productsBeans.size > 0) {
            productsBeans.clear()
            hotCommoditiesAdapter.notifyDataSetChanged()
        }
        gv_hot_commodities.adapter = hotCommoditiesAdapter
        hotCommoditiesAdapter.setOnItemSelectListener(onItemSelectListener)
    }

    override fun getArgs(bundle: Bundle?) {
    }

    override fun initListener() {

        srl_data.setOnRefreshListener {
            srl_data.isRefreshing = false
            refreshView()
        }
        icon_pager_indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    private val onItemSelectListener = object : OnItemSelectListener {
        override fun <T : Any?> onItemSelect(type: T, from: String?) {
            if (type is ProductsBean) {
                activity?.let {
                    it.showToast(getResString(R.string.this_feature_not_open_yet))
                }
//                val hotCommoditiesBean: HotCommoditiesBean? = type
//                hotCommoditiesBean ?: return
//                //跳转到应用详情页面
//                val bundle = Bundle()
//                bundle.putSerializable(Constants.KeyMaps.HotCommodities, hotCommoditiesBean)
//                activity.intentToActivity(bundle, CommodityDetailsAty::class.java)
            }
        }

    }

    override fun getMainBannerSuccess(bannerBeans: List<BannerBean>) {
        this.bannerBeans.clear()
        this.bannerBeans.addAll(bannerBeans)
        initMainTopBanner()
    }

    override fun getMainBannerFailure(msg: String) {
        activity?.showToast(msg)
    }


    override fun getProductsSuccess(productListBean: ProductListBean?) {
        if (productListBean == null) {
            rl_no_data.visibility = View.VISIBLE
            srl_data.visibility = View.GONE
        } else {
            val productsBean = productListBean.data
            if (ListTool.isEmpty(productsBean)) {
                canLoadingMore = false
            } else {
                if (fromLoading) {
                    for (i in productsBean.indices) {
                        this.productsBeans.add(productsBean[i])
                    }
                } else {
                    this.productsBeans.clear()
                    this.productsBeans.addAll(productsBean!!)
                }
                hotCommoditiesAdapter.addList((productsBeans))
                //加载数据
                canLoadingMore = this.productsBeans.size < productListBean.total

            }

        }
    }


    override fun getProductsFailure(msg: String?) {
        msg?.let {
            activity.showToast(it)

        }
    }


    private fun refreshView() {
        pageIndex = 1
        //加载首页banner信息
        refreshView(false)
    }

    private fun refreshView(fromLoading: Boolean) {
        this.fromLoading = fromLoading
        if (fromLoading) {
            if (canLoadingMore) {
                //代表还需要加载
                pageIndex += 1
                presenter.getProducts(pageSize, pageIndex)

            } else {
                activity.showToast(getResString(R.string.no_more_data))
            }
        } else {
            //加载首页banner信息
            presenter.getMainBanner()
            presenter.getProducts(pageSize, pageIndex)
        }
    }

    private val autoLoadCallBack = GridViewLoadListener.AutoLoadCallBack { refreshView(true) }
}