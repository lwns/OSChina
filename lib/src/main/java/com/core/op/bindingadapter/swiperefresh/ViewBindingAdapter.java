package com.core.op.bindingadapter.swiperefresh;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

import com.core.op.lib.command.ReplyCommand;


/**
 * Created by kelin on 16-4-26.
 */
public class ViewBindingAdapter {
    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final ReplyCommand onRefreshCommand) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshCommand != null) {
                    onRefreshCommand.execute();
                }
            }
        });
    }

    @BindingAdapter(value = {"refresh"}, requireAll = false)
    public static void setRefresh(final SwipeRefreshLayout swipeRefreshLayout, final Boolean refresh) {
        swipeRefreshLayout.setRefreshing(refresh);
    }

}
