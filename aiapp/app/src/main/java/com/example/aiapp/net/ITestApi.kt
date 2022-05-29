package com.example.aiapp.net

import com.example.aiapp.base.BaseData
import com.example.aiapp.bean.ProjectBean
import com.example.aiapp.bean.TestResultBean
import com.example.aiapp.bean.Treebean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName

/**
 * @author yzw
 * @date 2022/5/26
 * @describe 定义接口和返回值
 */
interface ITestApi {

    @GET("/project/tree/json")
    suspend fun getTree(): Treebean

    @GET("/project/list/1/json")
    suspend fun getTreeItem(@Query("cid") cid: Int): ProjectBean

    @GET("/lg/collect/list/0/json")
    suspend fun getLgCollect(): BaseData

}