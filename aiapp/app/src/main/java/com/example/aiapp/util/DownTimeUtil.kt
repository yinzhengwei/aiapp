package com.example.aiapp.util

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author yzw
 * @date 2022/4/7
 * @describe 计时帮助类
 */
class DownTimeUtil {

    private var countdownDisposable: Disposable? = null
    private var counterDispose: Disposable? = null

    /**
     * 开始倒计时
     *
     * @param totalTime 要倒计时的总时长（x秒）
     * @param callBack  回调对象
     */

    fun start(totalTime: Int, currentCallback: (Int) -> Unit, finishCallback: () -> Unit) {
        //interval四个参数分别为：延时0开始、到xx秒结束，单位时间（NANOSECONDS,MICROSECONDS,MILLISECONDS,SECONDS,MINUTES,HOURS,DAYS）。
        countdownDisposable = Observable.interval(0, 1, TimeUnit.SECONDS).take(totalTime.toLong())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { index: Long -> currentCallback((totalTime - index).toString().toInt()) }
            .doOnComplete { finishCallback() } //倒计时完毕事件处理
            .subscribe()
    }

    /**
     * 计时器
     * 每隔1秒中输出一次
     * @param callBack 返回值
     */
    fun startCounter(currentCallback: (Int) -> Unit) {
        counterDispose = Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { current: Long -> currentCallback(current.toInt()) }
    }

    /**
     * 停止
     */
    fun release() {
        if (countdownDisposable != null && !countdownDisposable!!.isDisposed) {
            countdownDisposable!!.dispose()
        }
    }

    fun releaseCounter() {
        if (counterDispose != null && !counterDispose!!.isDisposed) {
            counterDispose!!.dispose()
        }
    }
}