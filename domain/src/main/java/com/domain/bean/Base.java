package com.domain.bean;

import org.simpleframework.xml.Element;

import java.io.Serializable;

/**
 * 实体基类：实现序列化
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public abstract class Base implements Serializable {
    @Element(name = "notice", required = false)
    protected Notice notice;

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
