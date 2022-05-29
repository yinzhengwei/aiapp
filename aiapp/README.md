
# 序列化
- 序列化插件：id 'kotlin-parcelize'
- 在需要序列化的class上方增加注解：@Parcelize，并且实现: Parcelable接口即可


# 协程（异步处理）
- 项目中禁止自定义Thread或者ThreadPool！
- 统一从工具类'LaunchUtil'中调用，分别有子线程/主线程切换、只切换到主线程两种使用方式完成异步处理


# 网络请求
- 在'ITestApi'中定义接口
- 通过'ApiClient'创建接口类的实例
- 通过'RequestClient'完成调用： RequestClient.fetch({ ApiClient.testApi.getUser() }, {
                                    //成功
                                    baseView.hiddenLoading()
                                }, {
                                    //失败
                                })

# loading
- 如果在Activity / Fragment界面中，可以直接通过showLoading / hiddenLoading完成调用
- 如果在一些公共类中，可以将activity/ fragment对象的实例作为参数传入，接收参数的地方可以通过iBaseView类型接收：
 fun loadData(baseView: IBaseView) {
        baseView.showLoading()
        baseView.hiddenLoading()
 }

# 获取当前界面Activity实例
- 通过'CustomApplication.currentActivityCache'直接调用


# 界面title
- 布局中统一使用自定义控件 CustomTextView，可以设置属性：
 app:titleText="@string/app_name"
 app:titleTextColor="@color/black"
 app:titleTextSize="@dimen/sp_18"


# BaseQuickAdapter / 下拉刷新 / 上拉加载
- 下拉刷新,通过设置代码：
    adapter.isUpFetchEnable = true;
    adapter.setUpFetchListener {
        loadData()
    }
- 上拉加载,通过设置代码：
    adapter.setEnableLoadMore(true)
    adapter.setOnLoadMoreListener({
       //do something
    },recyclerView)
- 更多功能参考：https://blog.csdn.net/qq_35957672/article/details/100766004
             https://blog.csdn.net/qq_35957672/article/details/100766004
             https://blog.csdn.net/weixin_45120905/article/details/108107842

