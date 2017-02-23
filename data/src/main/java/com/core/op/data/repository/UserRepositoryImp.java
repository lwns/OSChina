package com.core.op.data.repository;

import com.core.op.data.ApiClient;
import com.core.op.data.XmlClient;
import com.core.op.data.api.transformer.ErrorTransformer;
import com.core.op.data.util.UpDownLoadUtil;
import com.domain.bean.LoginUserBean;
import com.domain.bean.Mention;
import com.domain.bean.SubBean;
import com.domain.bean.User;
import com.domain.bean.UserV2;
import com.domain.bean.base.PageBean;
import com.domain.interfaces.OnProgressListener;
import com.domain.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/8/8
 */
@Singleton
public class UserRepositoryImp implements UserRepository {

    XmlClient xmlClient;
    ApiClient apiClient;

    @Inject
    public UserRepositoryImp(XmlClient xmlClient,
                             ApiClient apiClient) {
        this.xmlClient = xmlClient;
        this.apiClient = apiClient;
    }

    @Override
    public Observable<LoginUserBean> login(String catalog, String openid_info) {
        return xmlClient.login(catalog, openid_info);
    }

    @Override
    public Observable<LoginUserBean> userLogin(String userName, String psw, String keep) {
        return xmlClient.userLogin(userName, psw, keep);
    }

    @Override
    public Observable<UserV2> getUserInfo() {
        return apiClient.getUserInfo().compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<UserV2> uploadUserHead(String path, OnProgressListener onProgressListener) {
        return apiClient.uploadUserHead(UpDownLoadUtil.uploadImageFile("portrait", path, onProgressListener))
                .compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<PageBean<Mention>> getMineMessage(String token) {
        return apiClient.getMineMessage(token).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<PageBean<SubBean>> getMineBlog(String authorId, String authorName, String token) {

        return apiClient.getMineBlog(authorId, authorName, token).compose(new ErrorTransformer<>());
    }
}
