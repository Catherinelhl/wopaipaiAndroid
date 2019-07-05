package cn.wopaipai.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cn.wopaipai.R
import cn.wopaipai.base.BaseApplication
import cn.wopaipai.base.BaseException
import cn.wopaipai.bean.AuctionInfoBean
import cn.wopaipai.constant.Constants
import cn.wopaipai.constant.MessageConstants
import cn.wopaipai.listener.OnItemSelectListener
import cn.wopaipai.listener.UpdateListener
import cn.wopaipai.tool.DateFormatTool
import cn.wopaipai.tool.ListTool
import cn.wopaipai.tool.LogTool
import cn.wopaipai.tool.time.ObservableTimerTool
import cn.wopaipai.utils.GlideImgManager
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-14 10:54
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.adapter
+--------------+---------------------------------
+ description  +   适配器：正在竞拍
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class AuctionInProgressAdapter(
    private val context: Context?,
    private var auctionInfoBeans: List<AuctionInfoBean>?, var updateListener: UpdateListener?
) : RecyclerView.Adapter<AuctionInProgressAdapter.viewHolder>() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val day by lazy { context!!.resources.getString(R.string.day) }
    private val TAG = AuctionInProgressAdapter::class.java.simpleName
    private var updateTime: Long = 0//记住更新时候的设备当前时间
    var width: Int = 0//得到当前图片应该的宽度
    var onItemSelectListener: OnItemSelectListener? = null

    init {
        context?.let {
            var itemWidth =
                BaseApplication.screenWidth - (it.resources.getDimensionPixelOffset(R.dimen.d12) * 2)
            //设置图片的大小 130：221；
            width = itemWidth / 350 * 141// - it.resources.getDimensionPixelOffset(R.dimen.d10)
        }

    }

    /**
     * 更新当前时间显示
     */
    fun updateTime(updateTime: Long) {
        this.updateTime = updateTime
        notifyDataSetChanged()
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: viewHolder, position: Int) {
        auctionInfoBeans?.let { it ->
            var countTime = 0L//当前需要倒数计时的总时间
            val auctionInProgressBean = it[position]


            viewHolder.tvName.text = auctionInProgressBean.productName
            viewHolder.tvAuctionRule.text =
                context!!.resources.getString(R.string.auction_admin) + MessageConstants.Space + auctionInProgressBean.startingPrice + MessageConstants.Space + MessageConstants.AUCTION_TYPE + MessageConstants.Space +
                        context.getString(
                            R.string.start_str
                        )


            viewHolder.tvCurrentPrice.text = auctionInProgressBean.currentPrice.toString()
            viewHolder.tvJoinedPerson.text =
                context!!.resources.getString(R.string.number_of_participants) + MessageConstants.Space + auctionInProgressBean.roomCount
            // 复用Item的时候，用当前的系统时间减去更新的时间=已过去的时间段，然后用于显示
            var currentTime = System.currentTimeMillis()
            var updatePeriod = (currentTime - updateTime) / 1000

            countTime = DateFormatTool.getCountDownTime(
                auctionInProgressBean.endTime
                , auctionInProgressBean.currentTime

            )
            //用服务器返回的时间得到倒计时，然后减去时间差得到应该显示的倒计时
            countTime -= updatePeriod
            //关闭当前的timer
            if (viewHolder.disposableCountDownTimer != null && !viewHolder.disposableCountDownTimer!!.isDisposed) {
                viewHolder.disposableCountDownTimer!!.dispose()
                viewHolder.disposableCountDownTimer = null
            }
            var displayCountDownTime = MessageConstants.NO_ENOUGH_TIME
            if (countTime > 0L) {
                LogTool.d(TAG, "countTime::$countTime")
                displayCountDownTime = DateFormatTool.getDisplayCountDownTime(day, countTime)
                LogTool.d(TAG, "displayCountDownTime::$displayCountDownTime")
                // 如果当前有需要倒计时的时间则启动倒计时
                // 倒数计时竞价结束时间
                ObservableTimerTool.countDownTimer(countTime)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Long> {
                        @SuppressLint("SetTextI18n")
                        override fun onNext(integer: Long) {
                            viewHolder.tvAuctionTime.text =
                                context.resources.getString(R.string.according_to_the_end) + MessageConstants.Space + DateFormatTool.getDisplayCountDownTime(
                                    day,
                                    integer
                                )
                        }

                        override fun onSubscribe(d: Disposable) {
                            viewHolder.disposableCountDownTimer = d
                            compositeDisposable.add(d)
                        }

                        override fun onError(e: Throwable) {
                            BaseException.print(e)
                            viewHolder.tvAuctionTime.text =
                                context!!.resources.getString(R.string.according_to_the_end) + MessageConstants.Space + MessageConstants.NO_ENOUGH_TIME
                            disposeRequest(viewHolder.disposableCountDownTimer)
                        }

                        override fun onComplete() {
                            //如果当前有item显示的时间为0了，就执行重新刷新；
                            updateListener?.let { listener ->
                                listener.onUpdate(false)
                            }
                            viewHolder.tvAuctionTime.text =
                                context!!.resources.getString(R.string.according_to_the_end) + MessageConstants.Space+ MessageConstants.NO_ENOUGH_TIME
                            disposeRequest(viewHolder.disposableCountDownTimer)
                        }
                    })
            } else {
                viewHolder.tvAuctionTime.text =
                    context!!.resources.getString(R.string.according_to_the_end) + MessageConstants.Space + displayCountDownTime
            }
            //设置图片的大小 130：112
            var layoutParams = viewHolder.ivCover.layoutParams
            layoutParams.width = width
            layoutParams.height = width //* 112 / 130
            viewHolder.ivCover.layoutParams = layoutParams
            GlideImgManager.displayImage(
                context,
                auctionInProgressBean.imageUrl,
                viewHolder.ivCover
            )
            RxView.clicks(viewHolder.itemView)
                .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
                .subscribe({
                    onItemSelectListener?.let {
                        it.onItemSelect(
                            auctionInProgressBean,
                            MessageConstants.Empty
                        )
                    }
                },
                    { e -> BaseException.print(e) })
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): viewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_auction_in_progress, viewGroup, false)
        return viewHolder(view)
    }


    override fun getItemCount(): Int =
        if (ListTool.isEmpty(auctionInfoBeans)) 0 else auctionInfoBeans!!.size

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.findViewById(R.id.tv_auction_commodity_name)
        var tvCurrentPrice: TextView =
            view.findViewById(R.id.tv_middle_content)
        var tvJoinedPerson: TextView = view.findViewById(R.id.tv_auction_joined_person_number)
        var tvAuctionRule: TextView = view.findViewById(R.id.tv_auction_commodity_rule)
        var tvAuctionHammer: TextView = view.findViewById(R.id.tv_auction_enter)
        var rlAuctionItem: RelativeLayout = view.findViewById(R.id.rl_auction_item)
        var tvAuctionTime: TextView = view.findViewById(R.id.tv_auction_end_time)
        var ivCover: ImageView = view.findViewById(R.id.iv_auction_commodity_cover)
        var disposableCountDownTimer: Disposable? = null

    }

    private fun disposeRequest(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    fun disposeAllTimer() {
        if (compositeDisposable != null && compositeDisposable.size() > 0) {
            //一次性取消所有的订阅
//            compositeDisposable.dispose()
        }
    }
}