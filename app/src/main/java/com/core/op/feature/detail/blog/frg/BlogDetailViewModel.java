package com.core.op.feature.detail.blog.frg;


import android.databinding.ObservableField;
import android.view.View;

import com.core.op.BR;
import com.core.op.R;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.databinding.FrgBlogdetailBinding;
import com.core.op.feature.detail.base.DetailAboutViewModel;
import com.core.op.feature.detail.base.frg.DetailViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.domain.bean.BlogDetail;
import com.domain.bean.NewsDetail;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.core.op.feature.detail.base.act.DetailViewModel.REFESHC_COMPLATE;

@PerActivity
public class BlogDetailViewModel extends DetailViewModel<BlogDetail, FrgBlogdetailBinding> {

    public final ObservableField<BlogDetail> data = new ObservableField<>();

    public final List<DetailAboutViewModel> abouts = new ArrayList<>();

    public final ItemView aboutItemView = ItemView.of(BR.viewModel, R.layout.include_blog_detail_about);

    public final Runnable finishCommand = () -> {
        Messenger.getDefault().sendNoMsg(REFESHC_COMPLATE);
    };

    @Inject
    public BlogDetailViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        data.set((BlogDetail) fragment.getArguments().getSerializable("data"));
        if (data == null) return;
        Observable.from(data.get().getAbouts())
                .doOnNext(data -> {
                    abouts.add(new DetailAboutViewModel(activity, data));
                })
                .subscribe((data) -> {
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding.layWebview != null) {
            binding.layWebview.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (binding.layWebview != null) {
            binding.layWebview.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binding.layWebview != null) {
        }
    }
}