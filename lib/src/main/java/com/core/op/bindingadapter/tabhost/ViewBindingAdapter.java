package com.core.op.bindingadapter.tabhost;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.widget.TabHost;

import com.core.op.bindingadapter.common.BindingUtils;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewArg;
import com.core.op.lib.weight.AddFragmentTabHost;

import java.util.List;


public final class ViewBindingAdapter {

    @BindingAdapter(value = {"manager", "idRes", "itemView", "items"}, requireAll = false)
    public static <T> void initTabHost(AddFragmentTabHost tabHost, FragmentManager manager, @IdRes int idRes, final ItemViewArg<T> itemView, final List<TabBean<T>> items) {
        tabHost.setup(tabHost.getContext(), manager, idRes);
        if (items != null && !items.isEmpty()) {
            int index = 0;
            for (TabBean<T> bean : items) {
                itemView.select(index, bean.getItem());
                TabHost.TabSpec tab = tabHost.newTabSpec(bean.getTag());
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(tabHost.getContext()), itemView.layoutRes(), null, false);
                if (itemView.bindingVariable() != ItemView.BINDING_VARIABLE_NONE) {
                    boolean result = binding.setVariable(itemView.bindingVariable(), bean.getItem());
                    if (!result) {
                        BindingUtils.throwMissingVariable(binding, itemView.bindingVariable(), itemView.layoutRes());
                    }
                    binding.executePendingBindings();
                }
                tab.setIndicator(binding.getRoot());
                tabHost.addTab(tab, bean.getClazz(), bean.getBundle());
                index++;
            }
        }
    }

    @BindingAdapter("currentIndex")
    public static void setIndex(AddFragmentTabHost tabHost, final Integer currentIndex) {
        tabHost.setCurrentTab(currentIndex);
    }

    public static class TabBean<T> {
        private String tag;

        private T item;

        private Class clazz;

        private Bundle bundle;

        public TabBean(String tag, T item, Class clazz, Bundle bundle) {
            this.tag = tag;
            this.item = item;
            this.clazz = clazz;
            this.bundle = bundle;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        public Bundle getBundle() {
            return bundle;
        }

        public void setBundle(Bundle bundle) {
            this.bundle = bundle;
        }
    }
}

