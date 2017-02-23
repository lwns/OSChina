package com.core.op.bindingadapter.textview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.core.op.lib.command.ReplyCommand;


/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter {

    @android.databinding.BindingAdapter({"requestFocus"})
    public static void requestFocusCommand(TextView textView, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            textView.setFocusableInTouchMode(true);
            textView.requestFocus();
        } else {
            textView.setFocusable(false);
        }
    }

    @android.databinding.BindingAdapter({"movementMethod"})
    public static void setMovementMethod(TextView textView, final MovementMethod movementMethod) {
        textView.setMovementMethod(movementMethod);
    }
}

