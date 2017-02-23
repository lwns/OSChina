package com.core.op.bindingadapter.viewgroup;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.core.op.lib.base.BViewModel;
import com.core.op.bindingadapter.common.ItemView;


/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter {

    @BindingAdapter({"itemView", "viewModels"})
    public static void addViews(ViewGroup viewGroup, final ItemView itemView, final ObservableList<BViewModel> viewModelList) {
        if (viewModelList != null && !viewModelList.isEmpty()) {
            viewGroup.removeAllViews();
            for (BViewModel viewModel : viewModelList) {
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        itemView.layoutRes(), viewGroup, true);
                binding.setVariable(itemView.bindingVariable(), viewModel);
            }
        }
    }
}

