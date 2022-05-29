package com.example.aiapp.core.common

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.aiapp.base.BaseActivity
import com.example.aiapp.base.BaseViewModel
import com.example.aiapp.config.PathRouterUrl.PATH_WEBVIEW_ACTIVITY
import com.example.aiapp.databinding.AttyWebviewLayoutBinding

/**
 * @author yzw
 * @date 2022/5/28
 * @describe
 */
@Route(path = PATH_WEBVIEW_ACTIVITY)
class WebViewActivity : BaseActivity<AttyWebviewLayoutBinding, BaseViewModel>() {

    override fun layoutInflate() = AttyWebviewLayoutBinding.inflate(layoutInflater)

    override fun getViewModel() = null

    override fun initView() {
        binding.webView.run {
            this.webChromeClient = webChromeClientListener
            this.webViewClient = webViewClientListener
        }
    }

    override fun initData() {
        val linkPath = intent.extras?.getString("link") ?: return
        binding.webView.loadUrl(linkPath)
    }

    private val webChromeClientListener = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            binding.progressHorizontal.progress = newProgress
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            binding.titleView.setTitle(title ?: "")
        }
    }

    private val webViewClientListener = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressHorizontal.visibility = View.GONE
        }
    }

}