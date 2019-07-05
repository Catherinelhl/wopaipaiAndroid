package cn.wopaipai.tool.time

import cn.wopaipai.base.BaseException
import cn.wopaipai.constant.Constants
import cn.wopaipai.listener.ObservableTimerListener
import cn.wopaipai.tool.LogTool
import io.reactivex.Observable
import io.reactivex.Observable.timer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019-06-11 15:55
+--------------+---------------------------------
+ projectName  +   wopaipaiAndroid
+--------------+---------------------------------
+ packageName  +   cn.wopaipai.tool.time
+--------------+---------------------------------
+ description  +   工具類：时间倒计时，定时管理工具
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/
object ObservableTimerTool {
    private val TAG = ObservableTimerTool::class.java.simpleName
    /*倒计时观察者*/
    private var countDownDisposable: Disposable? = null
    /*定时发送心跳观察者*/
    private var getLastRecordByIntervalDisposable: Disposable? = null
    /*定时刷新*/
    private var countDownRefreshViewDisposable: Disposable? = null
    /*定时查询当前的内存信息*/
    private var logDisposable: Disposable? = null

    fun countDownTimerBySetTime(
        time: Long,
        timeUnit: TimeUnit,
        observableTimerListener: ObservableTimerListener?
    ) {
        val subscribe = timer(time, timeUnit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                },
                {
                    //error
                    LogTool.e(TAG, it.toString())
                },
                {
                    //onComplete
                    observableTimerListener?.timeUp(Constants.TimerType.COUNT_DOWN_TIME)
                    //close countDown
                    countDownDisposable?.let { it.dispose() }
                },
                {
                    //onSubscribe
                    this.countDownDisposable = it
                }
            )
    }

    fun countDownTimerBySetTime(
        time: Long,
        observableTimerListener: ObservableTimerListener?
    ) {
//        val subscribe = timer(time, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    observableTimerListener?.timeUp(Constants.TimerType.COUNT_DOWN_ONE_TIME)
//                },
//                {
//                    //error
//                    LogTool.e(TAG, it.toString())
//                },
//                {
//                    //onComplete
//                    //close countDown
//                    countDownDisposable?.let { it.dispose() }
//                },
//                {
//                    //onSubscribe
//                    this.countDownDisposable = it
//                }
//            )

//        var time = time
//        if (time < 0) {
//            time = 0
//        }
//        val countTime = time
//        return Observable.interval(0, 1, TimeUnit.SECONDS)
//            .map { increaseTime -> countTime - increaseTime.toInt() }.take((countTime + 1).toLong())
    }

    /**
     * 倒计时
     */
    fun countDownTimer(time: Int): Observable<Int> {
        var time = time
        if (time < 0) {
            time = 0
        }
        val countTime = time
        return Observable.interval(0, 1, TimeUnit.SECONDS)
            .map { increaseTime -> countTime - increaseTime.toInt() }.take((countTime + 1).toLong())
    }

    /**
     * 倒计时
     */
    fun startToTime(): Observable<Int> {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
            .map { increaseTime ->  increaseTime.toInt() }
    }

    /**
     * 倒计时
     */
    fun countDownTimer(time: Long): Observable<Long> {
        println("countDownTimer")
        var time = time
        if (time < 0) {
            time = 0
        }
        val countTime = time
        return Observable.interval(0, 1, TimeUnit.SECONDS)
            .map { increaseTime -> countTime - increaseTime.toInt() }.take((countTime + 1))
    }

    /**
     * 开始与SAN心跳30秒定时发送
     *
     * @param observableTimerListener
     */
    fun getLastRecordByIntervalTimer(observableTimerListener: ObservableTimerListener?) {
        Observable.interval(0, Constants.Time.GET_LATEST_RECORD_INTERVAL_TIME, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onNext(t: Long) {
                    observableTimerListener?.timeUp(Constants.TimerType.GET_LATEST_RECORD)

                }

                override fun onSubscribe(d: Disposable) {
                    getLastRecordByIntervalDisposable = d
                }

                override fun onError(e: Throwable) {
                    BaseException.print(e)
                    LogTool.e(TAG,e.toString())

                }

                override fun onComplete() {
                    closeGetLatestRecordByIntervalTimer()
                }
            })
    }


    /**
     * 关闭定时发送器
     */
    fun closeGetLatestRecordByIntervalTimer() {
        getLastRecordByIntervalDisposable?.let {
            it.dispose() }
    }

}