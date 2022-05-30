package com.example.aiapp.util

import android.content.Context
import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * @author yzw
 * @date 2022/5/30
 * @describe 读取assets中文件内容
 */
object AssetsDataManagerUtil {
    fun readStringFromAssets(context: Context, fileName: String?): String {
        //通过设备管理对象 获取Asset的资源路径
        val assetManager = context.assets
        var inputStream: InputStream? = null
        var isr: InputStreamReader? = null
        var br: BufferedReader? = null
        val sb = StringBuilder()
        try {
            inputStream = assetManager.open(fileName!!)
            isr = InputStreamReader(inputStream)
            br = BufferedReader(isr)
            sb.append(br.readLine())
            var line: String? = null
            while (br.readLine().also { line = it } != null) {
                sb.append("\n").append(line)
            }
            br.close()
            isr.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                br?.close()
                isr?.close()
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }
}