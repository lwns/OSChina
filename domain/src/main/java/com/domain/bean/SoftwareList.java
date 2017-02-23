package com.domain.bean;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Root(name = "oschina", strict = false)
public class SoftwareList implements Serializable {

    public final static String PREF_READED_SOFTWARE_LIST = "readed_software_list.pref";

    public final static String CATALOG_RECOMMEND = "recommend";
    public final static String CATALOG_TIME = "time";
    public final static String CATALOG_VIEW = "view";
    public final static String CATALOG_LIST_CN = "list_cn";

    @Element(name = "softwarecount", required = false)
    private int softwarecount;

    @Element(name = "pagesize", required = false)
    private int pagesize;

    @ElementList(name = "softwares")
    private List<SoftwareDec> softwarelist = new ArrayList<>();

    public int getSoftwarecount() {
        return softwarecount;
    }

    public void setSoftwarecount(int softwarecount) {
        this.softwarecount = softwarecount;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public List<SoftwareDec> getSoftwarelist() {
        return softwarelist;
    }

    public void setSoftwarelist(List<SoftwareDec> softwarelist) {
        this.softwarelist = softwarelist;
    }

    public List<SoftwareDec> getList() {
        return softwarelist;
    }
}
