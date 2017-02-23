package com.domain.bean;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * 实体类
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public abstract class Entity extends Base {

    @Element(name = "id", required = false)
    protected int id;

    protected String cacheKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }
}
