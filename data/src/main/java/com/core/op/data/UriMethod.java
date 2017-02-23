package com.core.op.data;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/5/1
 */
public class UriMethod {

    public final static String SERVER_VERSION = "1.0";//1.0 接口版本
    public final static String SERVER_PORT = "00001";//00001 PAD端接口
    public final static String SERVER_format = "json";//接口返回格式json
    public final static String BASE_URL = "/phone/service?v=" + SERVER_VERSION + "&appKey=" + SERVER_PORT + "&format=" + SERVER_format + "&method=";// 接口基础信息
    public final static String LOGIN = "phone/investor/login";
    public final static String PRODUCTINTERFACEMENU = BASE_URL + "jinbao.phone.sys.interfaceMenu.list";//产品接口菜单

}
