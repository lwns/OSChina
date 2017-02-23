package com.domain.bean;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年9月27日 下午2:45:57
 */
@Root(name = "oschina", strict = false)
public class LoginUserBean extends Entity {

    @Element(name = "result")
    private Result result;

    @Element(name = "user", required = false)
    private User user;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}