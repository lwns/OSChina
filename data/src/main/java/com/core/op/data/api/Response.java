package com.core.op.data.api;

import okhttp3.ResponseBody;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/7/15
 */
public class Response<T> {

    private okhttp3.Response response;
    private BaseResponse<T> body;
    private ResponseBody errorBody;

    public okhttp3.Response getResponse() {
        return response;
    }

    public void setResponse(okhttp3.Response response) {
        this.response = response;
    }

    public BaseResponse<T> getBody() {
        return body;
    }

    public void setBody(BaseResponse<T> body) {
        this.body = body;
    }

    public ResponseBody getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(ResponseBody errorBody) {
        this.errorBody = errorBody;
    }
}
