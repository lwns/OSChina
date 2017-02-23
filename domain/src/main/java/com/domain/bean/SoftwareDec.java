package com.domain.bean;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 软件列表
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2015年1月20日 下午3:34:52
 */
@Root(name = "software", strict = false)
public class SoftwareDec extends Entity {
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "description", required = false)
    private String description;
    @Element(name = "url", required = false)
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
