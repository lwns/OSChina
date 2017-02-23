package com.core.op.feature.scan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.core.op.R;
import com.core.op.base.BackActivity;
import com.core.op.databinding.ActScanBinding;
import com.core.op.di.components.DaggerScanComponent;
import com.core.op.di.components.ScanComponent;
import com.core.op.di.modules.ScanModule;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

@RootView(R.layout.act_scan)
public final class ScanActivity extends BackActivity<ScanViewModel, ActScanBinding> {

    ScanComponent component;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, ScanActivity.class));
    }


    @BeforeViews
    void beforViews() {
        component = DaggerScanComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .scanModule(new ScanModule())
                .build();
        component.inject(this);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕长亮
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar;
    }
}
