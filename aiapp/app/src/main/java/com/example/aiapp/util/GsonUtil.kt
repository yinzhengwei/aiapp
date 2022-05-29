package com.example.aiapp.util

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*

/**
 * @author yzw
 * @date 2022/3/30
 * @describe
 */
object GsonUtil {

    //json转bean
    fun <T> jsonToBean(json: String?, tClass: Class<T>?): T {
        return Gson().fromJson(json, tClass)
    }

    //bean转json
    fun beanToJson(tClass: Any?): String {
        return Gson().toJson(tClass)
    }

    //json转list
    fun <T> jsonToList(json: String?, clazz: Class<T>): List<T> {
        return Gson().fromJson(json, ParameterizedTypeImpl(clazz, MutableList::class.java))
    }

    fun <E> jsonToVector(json: String?, clazz: Class<E>): Vector<E> {
        return Gson().fromJson(json, ParameterizedTypeImpl(clazz, Vector::class.java))
    }

    private class ParameterizedTypeImpl(var clazz: Class<*>, var rawType: Class<*>) :
        ParameterizedType {
        override fun getActualTypeArguments(): Array<Type> {
            return arrayOf(clazz)
        }

        override fun getRawType(): Type {
            return rawType
        }

        override fun getOwnerType(): Type? {
            return null
        }
    }
}