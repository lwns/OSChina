package com.core.op.lib.base;

import android.support.v4.app.FragmentManager;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/7/21
 */
public class BViewModel<T> {

    protected final RxAppCompatActivity activity;

    public BViewModel() {
        activity = null;
    }

    public BViewModel(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    public RxAppCompatActivity getActivity() {
        return activity;
    }

    protected T binding;

    public void setBinding(T binding) {
        this.binding = binding;
    }

    protected void onStart() {

    }

    protected void onResume() {

    }

    protected void onPause() {

    }

    protected void onStop() {

    }

    protected void onDestroy() {

    }
}
