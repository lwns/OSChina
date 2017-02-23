package com.core.op.bindingadapter.emptyLayout;

import android.databinding.BindingAdapter;

import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.weight.EmptyLayout;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/10/9
 */
public final class ViewBindingAdapter {
    /**
     * 设置错误类型
     */
    @BindingAdapter(value = {"errorType"}, requireAll = false)
    public static void setErrorType(final EmptyLayout emptyLayout, int errorType) {
        emptyLayout.setErrorType(errorType);
    }

    @BindingAdapter({"errorClickCommand"})
    public static void layoutClickCommand(final EmptyLayout emptyLayout, final ReplyCommand clickCommand) {
        emptyLayout.setOnLayoutClickListener(v -> {
            if (clickCommand != null) {
                clickCommand.execute();
            }
        });
    }
}
