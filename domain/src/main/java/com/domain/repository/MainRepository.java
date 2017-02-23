package com.domain.repository;

import com.domain.bean.Banner;
import com.domain.bean.Blog;
import com.domain.bean.BlogDetail;
import com.domain.bean.Event;
import com.domain.bean.EventDetail;
import com.domain.bean.News;
import com.domain.bean.NewsDetail;
import com.domain.bean.Question;
import com.domain.bean.QuestionDetail;
import com.domain.bean.ShakeNews;
import com.domain.bean.SoftwareDec;
import com.domain.bean.SoftwareDetail;
import com.domain.bean.SoftwareList;
import com.domain.bean.Tweet;
import com.domain.bean.base.PageBean;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/10/11
 */
public interface MainRepository {

    Observable<PageBean<News>> getNews(String pageToken);

    Observable<PageBean<Banner>> getBanner(String catalog);

    Observable<PageBean<Blog>> getBlog(String catalog, String pageToken);

    Observable<PageBean<Question>> getQuestion(String catalog, String pageToken);

    Observable<PageBean<Event>> getEvent(String pageToken);

    Observable<PageBean<Tweet>> getTweet(String tag, String pageToken);

    Observable<PageBean<Tweet>> getUserTweet(String authorId, String pageToken);

    Observable<NewsDetail> getNewsDetail(String id);

    Observable<BlogDetail> getBlogDetail(String id);

    Observable<QuestionDetail> getQuestionDetail(String id);

    Observable<SoftwareDetail> getSoftwareDetail(String id);

    Observable<EventDetail> getEventDetail(String id);

    Observable<Tweet> getTweetDetail(String id);

    Observable<SoftwareList> getSoftware(String searchTag, String pageIndex, String pageSize);

    Observable<ShakeNews> getShake();

    Observable pubTweet(String content);

}
