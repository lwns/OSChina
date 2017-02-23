package com.core.op.feature.back.mine.blog;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.DateUtil;
import com.domain.bean.Mention;
import com.domain.bean.SubBean;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import static android.R.attr.author;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2017/2/13
 */
@PerActivity
public class BlogItemViewModel extends BViewModel {

    public final SubBean data;

    public final String time;

    @Inject
    public BlogItemViewModel(RxAppCompatActivity activity, SubBean data) {
        super(activity);
        this.data = data;

        time = String.format("@%s %s",
                (data.getAuthor().getName().length() > 9 ? data.getAuthor().getName().substring(0, 9) : data.getAuthor().getName()),
                DateUtil.formatSomeAgo(data.getPubDate().trim()));
    }

}
