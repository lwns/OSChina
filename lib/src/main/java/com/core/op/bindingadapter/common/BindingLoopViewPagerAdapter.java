package com.core.op.bindingadapter.common;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/10/10
 */
public class BindingLoopViewPagerAdapter<T> extends BindingViewPagerAdapter<T> {

    public BindingLoopViewPagerAdapter(@NonNull ItemViewArg arg) {
        super(arg);
    }

    @Override
    public int getCount() {
        return items == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (inflater == null) {
            inflater = LayoutInflater.from(container.getContext());
        }

        position %= items.size();
        T item = items.get(position);
        itemViewArg.select(position, item);

        ViewDataBinding binding = onCreateBinding(inflater, itemViewArg.layoutRes(), container);
        onBindBinding(binding, itemViewArg.bindingVariable(), itemViewArg.layoutRes(), position, item);

        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = binding.getRoot().getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(binding.getRoot());
        }
        container.addView(binding.getRoot());
        binding.getRoot().setTag(item);
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof View) {
            container.removeView((View) object);
        }
    }
}
