package com.core.op.bindingadapter.common.factories;

import android.widget.AdapterView;

import com.core.op.bindingadapter.common.BindingListViewAdapter;
import com.core.op.bindingadapter.common.ItemViewArg;


public interface BindingAdapterViewFactory {
    <T> BindingListViewAdapter<T> create(AdapterView adapterView, ItemViewArg<T> arg);

    BindingAdapterViewFactory DEFAULT = new BindingAdapterViewFactory() {
        @Override
        public <T> BindingListViewAdapter<T> create(AdapterView adapterView, ItemViewArg<T> arg) {
            return new BindingListViewAdapter<>(arg);
        }
    };
}
