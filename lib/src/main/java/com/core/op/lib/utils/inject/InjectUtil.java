package com.core.op.lib.utils.inject;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;

import java.lang.reflect.Method;

import com.core.op.lib.AppException;

import static android.R.attr.fragment;

/**
 * @author op
 * @version 1.0
 * @description view依赖注入
 * @createDate 2016/2/1
 */
public class InjectUtil {

    /**
     * Inject setContentView and AfterViews
     *
     * @param activity
     */
    public static void injectRootView(Activity activity) {
        Class<?> clazz = activity.getClass();
        if (clazz.isAnnotationPresent(RootView.class)) {
            RootView rootView = clazz.getAnnotation(RootView.class);
            int id = rootView.value();
            //Inject setcontentView
            try {
                // 获得Method对象
                Method method = clazz.getMethod("setContentView", int.class);
                // 设置属性可访问(私有)
                method.setAccessible(true);
                method.invoke(activity, id);
            } catch (Exception e) {
                AppException.inject(e).printStackTrace();
            }
        }
    }

    /**
     * Inject BindRootView and AfterViews
     *
     * @param activity
     */
    public static <T extends ViewDataBinding> T injectBindRootView(Activity activity) {
        Class<?> clazz = activity.getClass();
        T mBinding = null;
        if (clazz.isAnnotationPresent(RootView.class)) {
            RootView rootView = clazz.getAnnotation(RootView.class);
            int id = rootView.value();
            try {
                mBinding = DataBindingUtil.setContentView(activity, id);//绑定布局
            } catch (Exception e) {
                AppException.inject(e).printStackTrace();
            }
        }
        return mBinding;
    }

    /**
     * Inject object
     *
     * @param object
     */
    public static int injectFrgRootView(Object object) {
        Class<?> clazz = object.getClass();
        if (clazz.isAnnotationPresent(RootView.class)) {
            RootView rootView = clazz.getAnnotation(RootView.class);
            return rootView.value();
        }
        return -1;
    }


    /**
     * inject afterviews
     *
     * @param object
     */
    public static void injectAfterView(Object object) {
        Method[] methods = object.getClass().getDeclaredMethods();
        //Inject AfterViews
        injectAfterView(object, methods);
    }

    /**
     * inject injectBeforeView
     *
     * @param object
     */
    public static void injectBeforeView(Object object) {
        Method[] methods = object.getClass().getDeclaredMethods();
        //injectBeforeView
        injectBeforeView(object, methods);
    }


    private static void injectAfterView(Object object, Method[] methods) {
        //Inject AfterViews
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                if (method.isAnnotationPresent(AfterViews.class)) {
                    try {
                        method.setAccessible(true);
                        method.invoke(object, new Object[0]);
                    } catch (Exception e) {
                        AppException.inject(e).printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectBeforeView(Object object, Method[] methods) {
        //injectBeforeView
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                if (method.isAnnotationPresent(BeforeViews.class)) {
                    try {
                        method.setAccessible(true);
                        method.invoke(object, new Object[0]);
                    } catch (Exception e) {
                        AppException.inject(e).printStackTrace();
                    }
                }
            }
        }
    }
}
