package com.core.op.bindingadapter.webview;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.webkit.WebView;

import com.core.op.lib.weight.OWebView;

/**
 * Created by kelin on 16-4-29.
 */
public class ViewBindingAdapter {
    @BindingAdapter({"render"})
    public static void loadHtml(WebView webView, final String html) {
        if (!TextUtils.isEmpty(html)) {
            webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        }
    }

    @BindingAdapter(value = {"body", "finishCommand"}, requireAll = true)
    public static void loadHtml(OWebView webView, final String body, Runnable runnable) {
        if (!TextUtils.isEmpty(body)) {
            webView.loadDetailDataAsync(body, runnable);
        }
    }
}
