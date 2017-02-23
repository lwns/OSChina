package com.core.op.lib.base;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/7/21
 */
public abstract class BDViewModel<T> {

    T binding;

    public void setBinding(T binding) {
        this.binding = binding;
    }
}
