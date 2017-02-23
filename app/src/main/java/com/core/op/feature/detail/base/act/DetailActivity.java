package com.core.op.feature.detail.base.act;


import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.core.op.R;
import com.core.op.base.BackActivity;
import com.core.op.base.BaseActivity;
import com.core.op.databinding.ActDetailBinding;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.utils.inject.RootView;

@RootView(R.layout.act_detail)
public abstract class DetailActivity<V extends BAViewModel> extends BackActivity<V, ActDetailBinding> {

    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar;
    }
}
