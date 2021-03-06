
## 序列化
- 序列化插件：
```
//app目录下的build.gradle中添加以下配置
plugins {
    id 'kotlin-parcelize'
}
```
- 在需要序列化的class上方增加注解：@kotlinx.parcelize.Parcelize，并且实现: android.os.Parcelable接口即可


## 协程（异步处理）
- 项目中禁止自定义Thread或者ThreadPool！
- 统一从工具类'LaunchUtil'中调用，分别有子线程/主线程切换、只切换到主线程两种使用方式完成异步处理


## 网络请求
- 在'ITestApi'中定义接口
- 通过'ApiClient'创建接口类的实例
- 通过'RequestClient'完成调用：
```
    RequestClient.fetch({ ApiClient.testApi.getUser() }, {
        //成功
        baseView.hiddenLoading()
    }, {
        //失败
    })
```

## loading
- 如果在Activity / Fragment界面中，可以直接通过showLoading / hiddenLoading完成调用
- 如果在一些公共类中，可以将activity/ fragment对象的实例作为参数传入，接收参数的地方可以通过iBaseView类型接收：
```
    fun loadData(baseView: IBaseView) {
        baseView.showLoading()
        baseView.hiddenLoading()
    }
```

## 获取当前界面Activity实例
- 通过'CustomApplication.currentActivityCache'直接调用


## 界面title
- 布局中统一使用自定义控件 CustomTextView，可以设置属性：
```
    app:titleText="@string/app_name"
    app:titleTextColor="@color/black"
    app:titleTextSize="@dimen/sp_18"
```

## BaseQuickAdapter
具体使用可参考ChildFragment中的列表，其他功能：
- 设置空布局
- 设置header/ footer
- 开启动画：adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
- 是否只让条目动画只执行一次：adapter.isFirstOnly(false);
- 出现了头布局就不会显示Empty：
```
     adapter.setHeaderAndEmpty(true);
```
     或者
```
     adapter.setHeaderFooterEmpty(true,true);
```
- 更多功能参考：https://blog.csdn.net/qq_35957672/article/details/100766004
             https://blog.csdn.net/qq_35957672/article/details/100766004
             https://blog.csdn.net/weixin_45120905/article/details/108107842


## SmartRefreshLayout 下拉刷新/上拉加载
- 通用设置：
```
    smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
        override fun onRefresh(refreshLayout: RefreshLayout) {
            //下拉刷新
        }
        override fun onLoadMore(refreshLayout: RefreshLayout) {
            //上拉加载
        }
    })
```
- 设置下拉header样式：在布局文件中SmartRefreshLayout下添加ClassicsHeader控件，可参考fg_child_layout.xml
- 结束刷新：smartRefreshLayout.finishRefresh()
- 结束加载：smartRefreshLayout.finishLoadMore()
- 刷新不可用: smartRefreshLayout.isEnableRefresh = false
- 上拉加载不可用:smartRefreshLayout.isEnableLoadMore = false
- 更多使用细节可参考网上资料:
    https://www.jianshu.com/p/29e315ff44a6
    https://www.cnblogs.com/shen-hua/p/8052459.html
    https://gitee.com/scwang90/SmartRefreshLayout
    https://github.com/scwang90/SmartRefreshLayout/blob/master/art/md_property.md


## monkey测试
- 执行命令：adb shell
- 执行monkey命令：monkey -s 100000 -p com.example.aiapp --throttle 300  --pct-touch 45 --pct-motion 45 --pct-appswitch 3 --pct-syskeys 3 --pct-anyevent 2 --pct-flip 2 --ignore-crashes --ignore-timeouts --ignore-security-exceptions --ignore-native-crashes 10000000 > /data/monkey.log &
- 到处monkey日志：logcat -v time *:E > /data/locatE.log &
- 停止monkey：执行命令：top | grep monkey；然后根据展示的monkey列表选择要停止的id执行命令：kill xxx