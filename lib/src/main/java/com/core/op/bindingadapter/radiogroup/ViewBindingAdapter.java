package com.core.op.bindingadapter.radiogroup;

import android.databinding.BindingAdapter;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.core.op.lib.command.ReplyCommand;

import java.util.concurrent.TimeUnit;

import rx.subjects.PublishSubject;

public class ViewBindingAdapter {

    @BindingAdapter(value = {"checked", "checkedChangeCommand"}, requireAll = false)
    public static void onCheckedChangeCommand(final RadioGroup radioGroup,
                                              final int resId,
                                              final ReplyCommand<Integer> replyCommand) {
        if (resId != 0)
            ((RadioButton) radioGroup.findViewById(resId)).setChecked(true);
        if (replyCommand != null) {
            radioGroup.setOnCheckedChangeListener((radio, arg) -> {
                replyCommand.execute(arg);
            });
        }
    }
}
