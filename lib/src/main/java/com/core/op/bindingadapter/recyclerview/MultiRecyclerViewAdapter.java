package com.core.op.bindingadapter.recyclerview;

import android.support.annotation.NonNull;

import com.core.op.bindingadapter.common.BindingRecyclerViewAdapter;
import com.core.op.bindingadapter.common.ItemViewArg;


/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/9/19
 */
public class MultiRecyclerViewAdapter<T> extends BindingRecyclerViewAdapter {

    public MultiRecyclerViewAdapter(@NonNull ItemViewArg arg) {
        super(arg);
    }
}
