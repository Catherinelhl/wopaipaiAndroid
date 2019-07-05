package cn.wopaipai.ui.contract

import cn.wopaipai.base.BaseContract
import cn.wopaipai.bean.BannerBean
import cn.wopaipai.bean.ProductListBean
import cn.wopaipai.bean.ProductsBean

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-19 01:40
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.ui.contract
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface MainContract {

    interface View : BaseContract.View {
        fun getMainBannerSuccess(bannerBeans: List<BannerBean>)
        fun getMainBannerFailure(msg: String)


        fun getProductsSuccess(productListBean: ProductListBean?)
        fun getProductsFailure(msg: String?)

    }

    interface Presenter : BaseContract.Presenter {
        fun getMainBanner()
        fun getProducts(pageSize: Int, pageIndex: Int)
    }
}