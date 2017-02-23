package com.core.op.feature.main.news.question;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import com.core.op.R;
import com.core.op.feature.detail.blog.BlogDetailActivity;
import com.core.op.feature.detail.question.QuesDetailActivity;
import com.core.op.feature.detail.question.frg.QuesDetailFragment;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.DateUtil;
import com.domain.bean.Blog;
import com.domain.bean.Question;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import static android.R.attr.author;
import static com.core.op.feature.main.news.question.QuestionViewModel.TOKEN_QUESTION_GROUP;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/16
 */
@PerActivity
public class QuestionItemViewModel extends BViewModel {

    public SpannableStringBuilder spannable;

    public Question question;

    public String author;

    private int catalog = 1;

    public
    @IdRes
    int checked;

    public final ReplyCommand<Integer> checkedChangeCommand = new ReplyCommand<>(checkedId -> {
        switch (checkedId) {
            case R.id.rb_question:
                catalog = 1;
                break;
            case R.id.rb_share:
                catalog = 2;
                break;
            case R.id.rb_composite:
                catalog = 3;
                break;
            case R.id.rb_job:
                catalog = 4;
                break;
            case R.id.rb_server:
                catalog = 5;
                break;
        }
        Messenger.getDefault().send(catalog, TOKEN_QUESTION_GROUP);
    });


    public final ReplyCommand itemClick = new ReplyCommand(() -> {
        QuesDetailActivity.instance(activity, question.getId());
    });

    public QuestionItemViewModel(int catalog) {
        switch (catalog) {
            case 1:
                this.checked = R.id.rb_question;
                break;
            case 2:
                this.checked = R.id.rb_share;
                break;
            case 3:
                this.checked = R.id.rb_composite;
                break;
            case 4:
                this.checked = R.id.rb_job;
                break;
            case 5:
                this.checked = R.id.rb_server;
                break;
        }
    }

    public QuestionItemViewModel(RxAppCompatActivity activity, Question question) {
        super(activity);
        this.question = question;

        author = question.getAuthor();
        if (!TextUtils.isEmpty(author)) {
            author = author.trim();
            author = (author.length() > 9 ? author.substring(0, 9) : author) +
                    "  " + DateUtil.formatSomeAgo(question.getPubDate().trim());
        }
    }

}
