package com.core.op.bindingadapter.common.factories;

import android.support.v7.widget.RecyclerView;

import com.core.op.bindingadapter.common.BindingRecyclerViewAdapter;
import com.core.op.bindingadapter.common.ItemViewArg;


public interface BindingRecyclerViewAdapterFactory {
    <T> BindingRecyclerViewAdapter<T> create(RecyclerView recyclerView, ItemViewArg<T> arg);

    BindingRecyclerViewAdapterFactory DEFAULT = new BindingRecyclerViewAdapterFactory() {
        @Override
        public <T> BindingRecyclerViewAdapter<T> create(RecyclerView recyclerView, ItemViewArg<T> arg) {
            return new BindingRecyclerViewAdapter<>(arg);
        }
    };
}
