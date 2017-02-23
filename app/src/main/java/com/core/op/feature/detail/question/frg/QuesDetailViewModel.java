package com.core.op.feature.detail.question.frg;


import android.databinding.ObservableField;

import com.core.op.databinding.FrgQuesdetailBinding;
import com.core.op.feature.detail.base.frg.DetailViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.domain.bean.QuestionDetail;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import static com.core.op.feature.detail.base.act.DetailViewModel.REFESHC_COMPLATE;

@PerActivity
public class QuesDetailViewModel extends DetailViewModel<QuestionDetail, FrgQuesdetailBinding> {

    public final ObservableField<QuestionDetail> data = new ObservableField<>();
    public final Runnable finishCommand = () -> {
        Messenger.getDefault().sendNoMsg(REFESHC_COMPLATE);
    };

    @Inject
    public QuesDetailViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        data.set((QuestionDetail) fragment.getArguments().getSerializable("data"));
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