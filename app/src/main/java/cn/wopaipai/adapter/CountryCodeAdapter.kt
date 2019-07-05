package cn.wopaipai.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.RelativeLayout
import android.widget.TextView
import cn.wopaipai.R
import cn.wopaipai.bean.CountryCodeBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.listener.FilterListener
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.StringTool
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.item_country_code.view.*
import java.util.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 21:23
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.adapter
+--------------+---------------------------------
+ description  +  數據適配器：「城市区号」：用於顯示已經存在的所有地址數據填充在PopWindow裡的適配器
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class CountryCodeAdapter(
    private val context: Context,
    private var countryCodesBeans: List<CountryCodeBean.CountryCode>?
) : RecyclerView.Adapter<CountryCodeAdapter.viewHolder>(), Filterable {

    private val TAG = CountryCodeAdapter::class.java.simpleName
    private var onItemSelectListener: OnItemSelectListener? = null
    private var filterListener: FilterListener? = null

    fun setOnItemSelectListener(onItemSelectListener: OnItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener
    }

    fun setFilterListener(filterListener: FilterListener) {
        this.filterListener = filterListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): viewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_country_code, viewGroup, false)
        return viewHolder(view)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(viewHolder: viewHolder, i: Int) {
        countryCodesBeans ?: return
        val countryCode = countryCodesBeans!![i]
        val name = countryCode.countryName
        val code = countryCode.countryPhone
        if (StringTool.isEmpty(name) || StringTool.isEmpty(code)) {
            return
        }
        viewHolder.tvContent.text =
            String.format(
                context.getString(R.string.double_s),
                context.resources.getString(R.string.plus_sign),
                StringTool.splitCountryCode(code)
            )
        viewHolder.tvName.text = name
//        viewHolder.tvName.setOnClickListener { v ->
//            onItemSelectListener!!.onItemSelect(
//                countryCode,
//                MessageConstants.Empty
//            )
//        }
        viewHolder.rlList.setOnClickListener { view ->
            onItemSelectListener!!.onItemSelect(
                countryCode,
                MessageConstants.Empty
            )
        }
        RxView.clicks(viewHolder.itemView)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe({
                onItemSelectListener!!.onItemSelect(
                    countryCode,
                    MessageConstants.Empty
                )
            },
                { e -> LogTool.e(TAG, e.toString()) })

    }

    override fun getItemCount(): Int {
        return if (ListTool.isEmpty<CountryCodeBean.CountryCode>(countryCodesBeans)) 0 else countryCodesBeans!!.size
    }

    override fun getFilter(): Filter {
        return SearchFilter(countryCodesBeans)
    }

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.tv_name
        val tvContent: TextView = view.tv_content
        val rlList: RelativeLayout = view.rl_list

    }

    internal inner class SearchFilter(// 创建集合保存原始数据
        private val original: List<CountryCodeBean.CountryCode>?
    ) : Filter() {

        /**
         * 该方法返回搜索过滤后的数据
         */
        override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
            // 创建FilterResults对象
            val results = Filter.FilterResults()
            original?.let {
                /**
                 * 没有搜索内容的话就还是给results赋值原始数据的值和大小
                 * 执行了搜索的话，根据搜索的规则过滤即可，最后把过滤后的数据的值和大小赋值给results
                 *
                 */
                if (StringTool.isEmpty(constraint.toString())) {
                    results.values = original
                    results.count = original.size
                } else {
                    // 创建集合保存过滤后的数据
                    val listNew = ArrayList<CountryCodeBean.CountryCode>()
                    // 遍历原始数据集合，根据搜索的规则过滤数据
                    for (countryCode in original) {
                        if (countryCode == null) {
                            results.values = original
                            results.count = original.size
                        } else {
                            //取得当前得城市名字
                            val countryName = countryCode.countryName
                            // 取得当前得城市名字拼音「中文环境可用」
//                            val countryNamePinyin = countryCode.countryPinyin
                            val countryNamePinyin = countryCode.countryAbbr
                            //取得当前返回得数据的小写信息·
                            val constraintLowerCase =
                                constraint.toString().trim { it <= ' ' }.toLowerCase()
                            if (countryName.trim { it <= ' ' }.toLowerCase().contains(
                                    constraintLowerCase
                                )
                            ) {
                                // 规则匹配的话就往集合中添加该数据
                                listNew.add(countryCode)
                            } else if (StringTool.notEmpty(countryNamePinyin)) {
                                //  比较拼音
                                if (countryNamePinyin.trim { it <= ' ' }.toLowerCase().contains(
                                        constraintLowerCase
                                    )
                                ) {
                                    listNew.add(countryCode)
                                }
                            }
                        }

                    }
                    results.values = listNew
                    results.count = listNew.size
                }

            }


            // 返回FilterResults对象
            return results
        }

        /**
         * 该方法用来刷新用户界面，根据过滤后的数据重新展示列表
         */
        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            // 获取过滤后的数据
            countryCodesBeans = results.values as List<CountryCodeBean.CountryCode>
            // 如果接口对象不为空，那么调用接口中的方法获取过滤后的数据，具体的实现在new这个接口的时候重写的方法里执行
            if (filterListener != null) {
                filterListener!!.getFilterData(countryCodesBeans)
            }
            // 刷新数据源显示
            notifyDataSetChanged()
        }

    }
}