package com.core.op.feature.back;


import com.core.op.R;
import com.core.op.feature.back.active.ActiveFragment;
import com.core.op.feature.back.mine.blog.BlogFragment;
import com.core.op.feature.back.mine.message.MessageFragment;
import com.core.op.feature.back.pub.PubFragment;
import com.core.op.feature.back.setting.SettingFragment;
import com.core.op.feature.back.shake.ShakeFragment;
import com.core.op.feature.back.software.SoftwareFragment;

public enum SimpleBackPage {

    SETTING(1, R.string.app_setting, SettingFragment.class),

    SOFTWARE(2, R.string.app_software, SoftwareFragment.class),

    SHAKE(3, R.string.app_shake, ShakeFragment.class),

    ACTIVE(4, R.string.app_explore_active, ActiveFragment.class),

    PUB(5, R.string.app_pub, PubFragment.class),

    MESSAGE(6, R.string.app_mine_message, MessageFragment.class),

    BLOG(7, R.string.app_mine_blog, BlogFragment.class);

    private int title;
    private Class<?> clz;
    private int value;

    private SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }


}
