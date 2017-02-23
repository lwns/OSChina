package com.core.op.feature.back;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.core.op.R;
import com.core.op.base.BackActivity;
import com.core.op.databinding.ActSimplebackBinding;
import com.core.op.di.components.DaggerSimpleBackComponent;
import com.core.op.di.components.SimpleBackComponent;
import com.core.op.di.modules.SimpleBackModule;
import com.core.op.lib.di.HasComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

@RootView(R.layout.act_simpleback)
public final class SimpleBackActivity extends BackActivity<SimpleBackViewModel, ActSimplebackBinding> implements HasComponent<SimpleBackComponent> {

    SimpleBackComponent component;

    public final static void instance(Context context, SimpleBackPage simpleBackPage) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra("page", simpleBackPage.getValue());
        context.startActivity(intent);
    }

    /**
     * 带数据fragment
     *
     * @param context
     * @param simpleBackPage
     * @param bundle
     */
    public final static void instance(Context context, SimpleBackPage simpleBackPage, Bundle bundle) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra("page", simpleBackPage.getValue());
        intent.putExtra("data", bundle);
        context.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {//软键盘处理
        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        super.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerSimpleBackComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .simpleBackModule(new SimpleBackModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar;
    }

    @Override
    public SimpleBackComponent getComponent() {
        return component;
    }
}
