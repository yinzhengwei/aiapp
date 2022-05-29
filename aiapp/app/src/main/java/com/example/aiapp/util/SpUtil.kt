package com.example.aiapp.util

import android.app.Application
import android.os.Parcelable
import com.example.aiapp.config.ParamsConstant
import com.tencent.mmkv.MMKV

/**
 * @author yzw
 * @date 2022/5/24
 *
 *
 * Sp存储工具类---使用MMVK实现
 */
object SpUtil {

    private var KV: MMKV? = null

    /**
     * 初始化MMKV地址
     */
    private fun init(application: Application?) {
        MMKV.initialize(application)
        KV = MMKV.mmkvWithID("aiapp", MMKV.MULTI_PROCESS_MODE)
    }

    private fun checkKV() {
        if (KV == null) {
            init(ParamsConstant.mContext)
        }
    }

    /**
     * 存储bean对象
     *
     * @param key   key
     * @param value value
     */
    fun save(key: String?, value: Parcelable?) {
        checkKV()
        KV!!.encode(key, value)
    }

    /**
     * 获取bean对象
     *
     * @param key   key
     * @param value value
     */
    fun <T : Parcelable?> get(key: String?, value: Class<T>?): T {
        checkKV()
        return KV!!.decodeParcelable(key, value)
    }

    /**
     * 存储
     *
     * @param key   key
     * @param value value
     */
    fun save(key: String?, value: String?) {
        checkKV()
        KV!!.encode(key, value)
    }

    /**
     * 取String
     *
     * @param key key
     * @return value value
     */
    fun getString(key: String?): String {
        checkKV()
        return KV!!.decodeString(key)
    }

    /**
     * 存储int
     *
     * @param key   key
     * @param value value
     */
    fun save(key: String?, value: Int) {
        checkKV()
        KV!!.encode(key, value)
    }

    /**
     * 取Int
     *
     * @param key key
     * @return value value
     */
    fun getInt(key: String?): Int {
        checkKV()
        return KV!!.decodeInt(key)
    }

    /**
     * 取Int
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value value
     */
    fun getInt(key: String?, defaultValue: Int): Long {
        checkKV()
        return KV!!.decodeInt(key, defaultValue).toLong()
    }

    /**
     * 存储long
     *
     * @param key   key
     * @param value value
     */
    fun save(key: String?, value: Long) {
        checkKV()
        KV!!.encode(key, value)
    }

    /**
     * 取Long
     *
     * @param key key
     * @return value value
     */
    fun getLong(key: String?): Long {
        checkKV()
        return KV!!.decodeLong(key)
    }

    /**
     * 取Long
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value value
     */
    fun getLong(key: String?, defaultValue: Long): Long {
        checkKV()
        return KV!!.decodeLong(key, defaultValue)
    }

    /**
     * 存储float
     *
     * @param key   key
     * @param value value
     */
    fun save(key: String?, value: Float) {
        checkKV()
        KV!!.encode(key, value)
    }

    /**
     * 取float
     *
     * @param key key
     * @return value value
     */
    fun getFloat(key: String?): Float {
        checkKV()
        return KV!!.decodeFloat(key)
    }

    /**
     * 取float
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value value
     */
    fun getFloat(key: String?, defaultValue: Float): Float {
        checkKV()
        return KV!!.decodeFloat(key, defaultValue)
    }

    /**
     * 存储double
     *
     * @param key   key
     * @param value value
     */
    fun save(key: String?, value: Double) {
        checkKV()
        KV!!.encode(key, value)
    }

    /**
     * 取double
     *
     * @param key key
     * @return value value
     */
    fun getDouble(key: String?): Double {
        checkKV()
        return KV!!.decodeDouble(key)
    }

    /**
     * 取double
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value value
     */
    fun getDouble(key: String?, defaultValue: Double): Double {
        checkKV()
        return KV!!.decodeDouble(key, defaultValue)
    }

    /**
     * 存储Boolean
     *
     * @param key   key
     * @param value value
     */
    fun save(key: String?, value: Boolean) {
        checkKV()
        KV!!.encode(key, value)
    }

    /**
     * 取Boolean
     *
     * @param key key
     * @return value value
     */
    fun getBoolean(key: String?): Boolean {
        checkKV()
        return KV!!.decodeBool(key)
    }

    /**
     * 取Boolean
     *
     * @param key key
     * @return value value
     */
    fun getBoolean(key: String?, defaultState: Boolean): Boolean {
        checkKV()
        return KV!!.decodeBool(key, defaultState)
    }

    /**
     * 存储byte[]
     *
     * @param key   key
     * @param value value
     */
    fun save(key: String?, value: ByteArray?) {
        checkKV()
        KV!!.encode(key, value)
    }

    /**
     * 取byte[]
     *
     * @param key key
     * @return value value
     */
    fun getBytes(key: String?): ByteArray {
        checkKV()
        return KV!!.decodeBytes(key)
    }

    /**
     * 取byte[]
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value value
     */
    fun getBytes(key: String?, defaultValue: ByteArray?): ByteArray {
        checkKV()
        return KV!!.decodeBytes(key, defaultValue)
    }

    /**
     * 是否已存key
     *
     * @param key key
     * @return boolean 是否已存有
     */
    fun hasKey(key: String?): Boolean {
        checkKV()
        return KV!!.containsKey(key)
    }

    /**
     * 删除
     *
     * @param key key
     */
    fun remove(key: String?) {
        checkKV()
        KV!!.removeValueForKey(key)
    }

    /**
     * 批量删除
     *
     * @param key String[]
     */
    fun removeKeys(key: Array<String?>?) {
        checkKV()
        KV!!.removeValuesForKeys(key)
    }
}