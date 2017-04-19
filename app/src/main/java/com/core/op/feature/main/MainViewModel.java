package com.core.op.feature.main;


import android.databinding.ObservableField;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.core.op.R;
import com.core.op.bindingadapter.bottomnavigation.NavigationRes;
import com.core.op.bindingadapter.bottomnavigation.ViewBindingAdapter;
import com.core.op.databinding.ActMainBinding;
import com.core.op.feature.back.SimpleBackActivity;
import com.core.op.feature.back.SimpleBackPage;
import com.core.op.feature.dialog.option.OptionDialog;
import com.core.op.feature.main.explore.ExploreFragment;
import com.core.op.feature.main.news.NewFragment;
import com.core.op.feature.main.tweet.TweetPagerFragment;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.AppToast;
import com.core.op.utils.AccountUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class MainViewModel extends BAViewModel<ActMainBinding> {

    public ObservableField<String> title = new ObservableField<>();

    public final FragmentManager fragmentManager;

    public final ObservableField<Integer> selectPosition = new ObservableField<>(0);

    public final ObservableField<Integer> pageLimit = new ObservableField<>(3);

    public final List<Fragment> fragments = new ArrayList<>();

    OptionDialog dialog;
    public final NavigationRes navigation = NavigationRes.of(R.array.tab_colors, R.menu.bottom_navigation_main).setAccent(R.color.app_theme_colorPrimary);

    public final ReplyCommand pubClick = new ReplyCommand(() -> {
        SimpleBackActivity.instance(activity, SimpleBackPage.PUB);

//        dialog.show();
    });

    public final ReplyCommand<ViewBindingAdapter.NavigationDataWrapper> selectedCommand = new ReplyCommand<>(p -> {
        switch (p.position) {
            case 0:
                title.set(activity.getString(R.string.app_bottom_menu_new));
                break;
            case 1:
                title.set(activity.getString(R.string.app_bottom_menu_tweet));
                break;
            case 2:
                title.set(activity.getString(R.string.app_bottom_menu_explore));
                break;
        }
        selectPosition.set(p.position);
    });

    @Inject
    public MainViewModel(RxAppCompatActivity activity, OptionDialog dialog) {
        super(activity);
        fragmentManager = activity.getSupportFragmentManager();
        fragments.add(new NewFragment());
        fragments.add(new TweetPagerFragment());
        fragments.add(new ExploreFragment());
        title.set(activity.getString(R.string.app_bottom_menu_new));
        this.dialog = dialog;
    }

    @Override
    public void afterViews() {

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.item_message:
                        if (varifyLogin())
                            SimpleBackActivity.instance(activity, SimpleBackPage.MESSAGE);
                        break;
                    case R.id.item_blog:
                        if (varifyLogin())
                            SimpleBackActivity.instance(activity, SimpleBackPage.BLOG);
                        break;
                }
                return true;
            }
        });
    }

    private boolean varifyLogin() {
        if (AccountUtil.isLogin(activity))
            return true;
        AppToast.show(activity, activity.getString(R.string.app_login_error));
        return false;
    }
}