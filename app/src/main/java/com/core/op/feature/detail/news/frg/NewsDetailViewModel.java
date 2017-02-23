package com.core.op.feature.detail.news.frg;


import android.databinding.ObservableField;

import com.core.op.BR;
import com.core.op.R;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.databinding.FrgNewsdetailBinding;
import com.core.op.feature.detail.base.DetailAboutViewModel;
import com.core.op.feature.detail.base.frg.DetailViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.domain.bean.NewsDetail;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static android.R.attr.data;
import static com.core.op.feature.detail.base.act.DetailViewModel.REFESHC_COMPLATE;


@PerActivity
public class NewsDetailViewModel extends DetailViewModel<NewsDetail, FrgNewsdetailBinding> {

    public final List<DetailAboutViewModel> abouts = new ArrayList<>();

    public final ItemView aboutItemView = ItemView.of(BR.viewModel, R.layout.include_blog_detail_about);

    public final ObservableField<NewsDetail> data = new ObservableField<>();
    public final Runnable finishCommand = () -> {
        Messenger.getDefault().sendNoMsg(REFESHC_COMPLATE);
    };

//    public final List<NewsDetailCommentViewModel> commentViewModel = new ArrayList<>();
//
//    public final ItemView commentItemView = ItemView.of(BR.viewModel, R.layout.include_comment_item);

    @Inject
    public NewsDetailViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        data.set((NewsDetail) fragment.getArguments().getSerializable("data"));
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