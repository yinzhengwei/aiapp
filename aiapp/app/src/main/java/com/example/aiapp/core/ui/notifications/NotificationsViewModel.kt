package com.example.aiapp.core.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aiapp.base.BaseViewModel
import com.example.aiapp.bean.ProjectBean
import com.example.aiapp.bean.TestResultBean
import com.example.aiapp.bean.Treebean
import com.example.aiapp.net.ApiClient
import com.example.aiapp.net.RequestClient
import com.example.aiapp.util.ToastUtil

class NotificationsViewModel : BaseViewModel() {

    private val _text = createLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text


    val treeDataLiveData = createLiveData<MutableList<Treebean.TreeItemBean>>()
    val listDataLiveData = createLiveData<MutableList<ProjectBean.ProjectItemBean>>()

    fun loadTreeData() {
        RequestClient.fetch(baseView, { ApiClient.testApi.getTree() }, {
            treeDataLiveData.value = it.data
        }, {
            Log.d("RequestClient", it)
            ToastUtil.showToast(it)
        })
    }

    fun loadListData(cid: Int) {
        RequestClient.fetch(baseView, { ApiClient.testApi.getTreeItem(cid) }, {
            listDataLiveData.value = it.data.datas
        }, {
            Log.d("RequestClient", it)
            ToastUtil.showToast(it)
        })
    }
}