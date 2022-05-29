package com.example.aiapp.util

import android.app.Activity
import android.content.Context
import com.bumptech.glide.Glide
import android.widget.ImageView
import jp.wasabeef.glide.transformations.RoundedCornersTransformation.CornerType
import jp.wasabeef.glide.transformations.CropTransformation.CropType
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.example.aiapp.R
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.engine.DiskCacheStrategy
import jp.wasabeef.glide.transformations.*

/**
 * @author yzw
 * @date 2022/5/24
 *
 *
 * 图片加载类
 */

fun ImageView.loadImage(obj: String) {
    Glide.with(context).load(obj).error(errorImage)
        .fallback(errorImage).placeholder(placeholder).into(this)
}

/**
 * @param obj       这里obj 只加载 url bitmap  drawable
 * @param imageView 需要加载的图片
 *
 * 加载正常图片
 * 这里并没有加载错图，在有错图的时候设置 error()
 */
fun ImageView.loadImage(obj: Any?) {
    Glide.with(context).load(obj).apply(initOptions())
        .skipMemoryCache(isSkipMemoryCache).error(errorImage)
        .fallback(errorImage).placeholder(placeholder).into(this)
}

/**
 * @param context   当前Activity的上下文对象
 * @param obj       obj
 * @param imageView imageView
 * 与没有context的方法相比 不易导致 内存泄漏问题，原因 activity销毁的时候 imageView 的上下文对象自然不存在
 */
fun ImageView.loadImage(context: Context, obj: Any?) {
    Glide.with(context).load(obj).apply(initOptions())
        .skipMemoryCache(isSkipMemoryCache).error(errorImage)
        .fallback(errorImage).placeholder(placeholder).into(this)
}

/**
 * @param context   当前Activity的上下文对象
 * @param obj       obj
 * @param imageView imageView
 * 与没有context的方法相比 不易导致 内存泄漏问题，原因 activity销毁的时候 imageView 的上下文对象自然不存在
 */
fun ImageView.loadImage(context: Activity, obj: Any?) {
    Glide.with(context).load(obj).apply(initOptions())
        .skipMemoryCache(isSkipMemoryCache).error(errorImage)
        .fallback(errorImage).placeholder(placeholder).into(this)
}

/**
 * @param fragment   当前Fragment的上下文对象
 * @param obj       obj
 * @param imageView imageView
 * 与没有context的方法相比 不易导致 内存泄漏问题，原因 activity销毁的时候 imageView 的上下文对象自然不存在
 */
fun ImageView.loadImage(fragment: Fragment?, obj: Any?) {
    Glide.with(fragment!!).load(obj).apply(initOptions())
        .skipMemoryCache(isSkipMemoryCache).error(errorImage)
        .fallback(errorImage).placeholder(placeholder).into(this)
}

/**
 * @param context   当前Activity的上下文对象
 * @param imageView imageView
 * 加载圆形图
 */
fun ImageView.loadCircleImage(context: Context, url: String?) {
    Glide.with(context).load(url).apply(initOptions())
        .skipMemoryCache(isSkipMemoryCache).error(errorImage).placeholder(placeholder)
        .fallback(errorImage).circleCrop().into(this)
}

/**
 * @param context   当前Activity的上下文对象
 * @param imageView imageView
 * 加载正方形图片
 */
fun ImageView.loadSquareImage(context: Context, url: String?) {
    Glide.with(context).load(url).apply(initOptions(CropSquareTransformation()))
        .skipMemoryCache(isSkipMemoryCache).error(errorImage).placeholder(placeholder)
        .fallback(errorImage).circleCrop().into(this)
}

/**
 * @param context   当前Activity的上下文对象
 * @param imageView imageView
 * 加载黑白图片
 */
fun ImageView.loadGrayscaleImage(context: Context, url: String?) {
    Glide.with(context).load(url).apply(initOptions(GrayscaleTransformation()))
        .skipMemoryCache(isSkipMemoryCache).error(errorImage).placeholder(placeholder)
        .fallback(errorImage).circleCrop().into(this)
}

/**
 * @param context   当前Activity的上下文对象
 * @param imageView imageView
 * @param radius    圆角
 * 加载圆角图片  默认所有圆角
 */
fun ImageView.loadGrayscaleImage(context: Context, url: String?, radius: Int) {
    Glide.with(context).load(url)
        .apply(initOptions(RoundedCornersTransformation(radius, 0, CornerType.ALL)))
        .error(errorImage).placeholder(placeholder)
        .fallback(errorImage).into(this)
}

/**
 * @param context    当前Activity的上下文对象
 * @param imageView  imageView
 * @param radius     圆角
 * @param cornerType 圆角类型
 * 加载圆角图片
 */
fun ImageView.loadGrayscaleImage(
    context: Context,
    url: String?,
    radius: Int,
    cornerType: CornerType?
) {
    Glide.with(context).load(url)
        .apply(initOptions(RoundedCornersTransformation(radius, 0, cornerType)))
        .skipMemoryCache(isSkipMemoryCache).error(errorImage).placeholder(placeholder)
        .fallback(errorImage).circleCrop().into(this)
}

/**
 * @param context      当前Activity的上下文对象
 * @param imageView    imageView
 * @param width,height 圆角宽高
 * @param cropType     裁剪位置
 * 自定义裁剪
 */
fun ImageView.loadCropTransformationImage(
    context: Context,
    url: String?,
    width: Int,
    height: Int,
    cropType: CropType?
) {
    Glide.with(context).load(url)
        .apply(initOptions(CropTransformation(width, height, cropType)))
        .skipMemoryCache(isSkipMemoryCache).error(errorImage).placeholder(placeholder)
        .fallback(errorImage).circleCrop().into(this)
}

/**
 * @param context      当前Activity的上下文对象
 * @param imageView    imageView
 * @param width,height 圆角宽高
 * 自定义裁剪 默认居中裁剪
 */
fun ImageView.loadCropTransformationImage(
    context: Context,
    url: String?,
    width: Int,
    height: Int
) {
    Glide.with(context).load(url)
        .apply(initOptions(CropTransformation(width, height, CropType.CENTER)))
        .skipMemoryCache(isSkipMemoryCache).error(errorImage).placeholder(placeholder)
        .fallback(errorImage).circleCrop().into(this)
}

/**
 * @param context
 * @param url
 * @param imageView imageView
 * 加载动图gif
 */
fun ImageView.loadGifImage(context: Context, url: String?) {
    Glide.with(context).asGif().apply(initOptions())
        .skipMemoryCache(isSkipMemoryCache).load(url).error(errorImage).placeholder(placeholder)
        .fallback(errorImage).circleCrop().into(this)
}

/**
 * @param url
 * @param imageView imageView
 * 加载动图gif
 */
fun ImageView.loadGifImage(url: String?) {
    Glide.with(context).asGif().apply(initOptions())
        .skipMemoryCache(isSkipMemoryCache).load(url).error(errorImage)
        .placeholder(placeholder).fallback(errorImage).circleCrop().into(this)
}

/**
 * @param ambiguity 模糊度  eg ：80
 * 加载高斯模糊大图
 */
fun ImageView.loadTransformImage(url: String?, ambiguity: Int) {
    Glide.with(context).load(url).skipMemoryCache(isSkipMemoryCache)
        .fallback(errorImage).placeholder(placeholder).error(errorImage)
        .apply(initOptions(BlurTransformation(ambiguity)))
        .into(this)
}

/**
 * @param sizeMultiplier 如设置0.2f缩略
 * 加载缩略图
 */
fun ImageView.loadThumbnailImage(url: String?, sizeMultiplier: Float) {
    Glide.with(context).load(url)
        .skipMemoryCache(isSkipMemoryCache) //缩略的参数
        .thumbnail(sizeMultiplier)
        .apply(initOptions())
        .into(this)
}

/**
 * @param context   当前Activity的上下文对象
 * @param imageView imageView
 * 设置滤镜 （陈旧）
 */
fun ImageView.loadSepiaFilterTransformationImage(context: Context, url: String?) {
    Glide.with(context).load(url).apply(initOptions(SepiaFilterTransformation(1.0f)))
        .skipMemoryCache(isSkipMemoryCache).error(errorImage).placeholder(placeholder)
        .fallback(errorImage).circleCrop().into(this)
}

/**
 * @param context   当前Activity的上下文对象
 * @param imageView imageView
 * 设置滤镜 （亮度）
 */
fun ImageView.loadBrightnessFilterTransformationImage(
    context: Context,
    url: String?
) {
    Glide.with(context).load(url).apply(initOptions(BrightnessFilterTransformation(0.5f)))
        .skipMemoryCache(isSkipMemoryCache).error(errorImage).placeholder(placeholder)
        .fallback(errorImage).circleCrop().into(this)
}

/**
 * @param context   当前Activity的上下文对象
 * @param imageView imageView
 * 设置滤镜 （马赛克）
 */
fun ImageView.loadPixelationFilterTransformationImage(
    context: Context,
    url: String?
) {
    Glide.with(context).load(url).apply(initOptions(PixelationFilterTransformation(20f)))
        .skipMemoryCache(isSkipMemoryCache).error(errorImage).placeholder(placeholder)
        .fallback(errorImage).circleCrop().into(this)
}

/**
 * @param context   当前Activity的上下文对象
 * @param imageView imageView
 * 设置滤镜 （素描画）
 */
fun ImageView.loadSketchFilterTransformationImage(
    context: Context,
    url: String?
) {
    Glide.with(context).load(url).apply(initOptions(SketchFilterTransformation()))
        .skipMemoryCache(isSkipMemoryCache).error(errorImage).placeholder(placeholder)
        .fallback(errorImage).circleCrop().into(this)
}

/**
 * @return 设置全局的错误图片 防止更改时多地方修改
 * 当图片加载失败的时候显示
 */
@get:DrawableRes
private val errorImage: Int
    private get() = R.mipmap.ic_launcher

/**
 * @return 设置全局的占位图 防止更改时多地方修改
 * 当图片没有加载出来的时候显示
 */
@get:DrawableRes
private val placeholder: Int
    get() = R.mipmap.ic_launcher

/**
 * @return 返回当前石头 跳过内存缓存
 * true 不缓存 false 缓存
 */
private val isSkipMemoryCache = false

/**
 * @return 这里默认设置全部为禁止缓存
 * 设置缓存
 *
 *
 * Glide有两种缓存机制，一个是内存缓存，一个是硬盘缓存。
 * 内存缓存的主要作用是防止应用重复将图片数据读取到内存当中，
 * 而硬盘缓存的主要作用是防止应用重复从网络或其他地方重复下载和读取数据
 * diskCacheStrategy 参数 DiskCacheStrategy.NONE： 表示不缓存任何内容
 * DiskCacheStrategy.DATA： 表示只缓存原始图片
 * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片
 * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片
 * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）
 */
private fun initOptions(transformation: BitmapTransformation): RequestOptions {
    return RequestOptions()
        .transform(transformation)
        .skipMemoryCache(isSkipMemoryCache) //是否允许内存缓存
        .onlyRetrieveFromCache(false) //是否只从缓存加载图片
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) //禁止磁盘缓存
}

private fun initOptions(): RequestOptions {
    return RequestOptions()
        .skipMemoryCache(isSkipMemoryCache) //是否允许内存缓存
        .onlyRetrieveFromCache(true) //是否只从缓存加载图片
        .diskCacheStrategy(DiskCacheStrategy.NONE) //禁止磁盘缓存
}

/**
 * 清楚内容缓存
 */
fun clearMemory(context: Context) {
    Glide.get(context).clearMemory()
}

/**
 * 清除磁盘缓存
 */
fun clearDiskCache(context: Context) {
    Glide.get(context).clearDiskCache()
}

/**
 * @param transformation transformation
 * @return RequestOptions
 * 设置加载的效果
 */
private fun bitmapTransform(transformation: BitmapTransformation): RequestOptions {
    return RequestOptions()
}