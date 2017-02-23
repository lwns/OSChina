package com.domain.repository;

import com.domain.bean.LoginUserBean;
import com.domain.bean.Mention;
import com.domain.bean.SubBean;
import com.domain.bean.User;
import com.domain.bean.UserV2;
import com.domain.bean.base.PageBean;
import com.domain.interfaces.OnProgressListener;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description TODO
 * @createDate 2016/10/10
 */


public interface UserRepository {

    Observable<LoginUserBean> login(String catalog, String openid_info);//第三方登陆

    Observable<LoginUserBean> userLogin(String userName, String psw, String keep);//用户名密码登陆

    Observable<UserV2> getUserInfo();

    Observable<UserV2> uploadUserHead(String path, OnProgressListener onProgressListener);

    Observable<PageBean<Mention>> getMineMessage(String token);

    Observable<PageBean<SubBean>> getMineBlog(String authorId, String authorName, String token);
}
