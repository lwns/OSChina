package com.core.op.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.base.BActivity;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2017/1/5
 */
public abstract class BackActivity<V extends BAViewModel, T extends ViewDataBinding> extends BaseActivity<V, T> {

    @Override
    protected void initAfterView() {
        setSupportActionBar(setToolBar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.initAfterView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract Toolbar setToolBar();
}
