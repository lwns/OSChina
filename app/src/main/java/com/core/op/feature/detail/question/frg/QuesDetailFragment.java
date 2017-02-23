package com.core.op.feature.detail.question.frg;

import android.os.Bundle;

import com.core.op.R;
import com.core.op.base.BaseFragment;
import com.core.op.databinding.FrgQuesdetailBinding;
import com.core.op.di.components.QuesDetailComponent;
import com.core.op.feature.detail.news.frg.NewsDetailFragment;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.domain.bean.NewsDetail;
import com.domain.bean.QuestionDetail;

import javax.inject.Inject;

@RootView(R.layout.frg_quesdetail)
public final class QuesDetailFragment extends BaseFragment<QuesDetailViewModel, FrgQuesdetailBinding> {

    public static QuesDetailFragment instance(QuestionDetail data) {
        QuesDetailFragment fragment = new QuesDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BeforeViews
    void beforViews() {
        getComponent(QuesDetailComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {

    }
}
