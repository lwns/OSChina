package com.core.op.feature.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;

import com.core.op.R;
import com.core.op.BR;
import com.core.op.base.BaseActivity;
import com.core.op.databinding.ActMainBinding;
import com.core.op.databinding.IncludeNavigationHeaderBinding;
import com.core.op.di.components.DaggerMainComponent;
import com.core.op.di.components.MainComponent;
import com.core.op.di.modules.MainModule;

import com.core.op.lib.di.HasComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.core.op.lib.weight.SolarSystemView;

import java.util.Random;

import javax.inject.Inject;


@RootView(R.layout.act_main)
public final class MainActivity extends BaseActivity<MainViewModel, ActMainBinding> implements HasComponent<MainComponent> {

    MainComponent component;

    @Inject
    NavHeaderViewModel navHeaderViewModel;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @BeforeViews
    void beforViews() {
        component = DaggerMainComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .mainModule(new MainModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
        initNav();
    }

    void initNav() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerlayout, binding.include.toolbar, R.string.app_drawer_open,
                R.string.app_drawer_close);
        toggle.syncState();
        binding.drawerlayout.addDrawerListener(toggle); // 给抽屉布局添加 导航 监听

        IncludeNavigationHeaderBinding navBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.include_navigation_header, binding.navigationView, false);
        navHeaderViewModel.setBinding(navBinding);
        navBinding.setVariable(BR.viewModel, navHeaderViewModel);
        initSolar(navBinding);
        if (binding.navigationView != null) {
            //需要注意的是，必须绑定完数据后才可以尝试获取并添加binding内的根视图到NavigationView中
            binding.navigationView.addHeaderView(navBinding.getRoot());
            navBinding.userViewSolarSystem.accelerate();
        }
        navHeaderViewModel.afterViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navHeaderViewModel.unRegisterBroadCast();
    }

    /**
     * init solar view
     */
    private void initSolar(IncludeNavigationHeaderBinding navBinding) {

        binding.getRoot().post(new Runnable() {
            @Override
            public void run() {

                if (navBinding.rlShowMyInfo == null) return;
                int width = navBinding.rlShowMyInfo.getWidth();
                float rlShowInfoX = navBinding.rlShowMyInfo.getX();

                int height = navBinding.userInfoIconContainer.getHeight();
                float y1 = navBinding.userInfoIconContainer.getY();

                float x = navBinding.ivPortrait.getX();
                float y = navBinding.ivPortrait.getY();
                int ciOrtraitWidth = navBinding.ivPortrait.getWidth();

                float mPx = x + +rlShowInfoX + (width >> 1);
                float mPy = y1 + y + (height - y) / 2;
                int mMaxRadius = (int) (navBinding.userViewSolarSystem.getHeight() - mPy + 50);
                int mR = (ciOrtraitWidth >> 1);
                updateSolar(navBinding.userViewSolarSystem, mPx, mPy, mMaxRadius, mR);
            }
        });
    }

    /**
     * update solar
     *
     * @param px float
     * @param py float
     */
    private void updateSolar(SolarSystemView solarSystemView, float px, float py, int maxRadius, int r) {

        Random random = new Random(System.currentTimeMillis());
        solarSystemView.clear();
        for (int i = 60, radius = r + i; radius <= maxRadius; i = (int) (i * 1.4), radius += i) {
            SolarSystemView.Planet planet = new SolarSystemView.Planet();
            planet.setClockwise(random.nextInt(10) % 2 == 0);
            planet.setAngleRate((random.nextInt(35) + 1) / 1000.f);
            planet.setRadius(radius);
            solarSystemView.addPlanets(planet);

        }
        solarSystemView.setPivotPoint(px, py);
    }

    @Override
    public MainComponent getComponent() {
        return component;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        navHeaderViewModel.onActivityResult(requestCode, resultCode, data);
    }
}
