package com.core.op.data;

import com.domain.bean.LoginUserBean;
import com.domain.bean.SoftwareDec;
import com.domain.bean.SoftwareList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description TODO
 * @createDate 2016/12/2
 */


public interface XmlClient {

    @FormUrlEncoded
    @POST("action/api/openid_login")
    Observable<LoginUserBean> login(@Field("catalog") String catalog, @Field("openid_info") String openid_info);

    @FormUrlEncoded
    @POST("action/api/login_validate")
    Observable<LoginUserBean> userLogin(@Field("username") String username, @Field("pwd") String pwd, @Field("keep_login") String keep);

    @GET("action/api/software_list")
    Observable<SoftwareList> getSoftware(@Query("searchTag") String searchTag, @Query("pageIndex") String pageIndex, @Query("pageSize") String pageSize);

}
