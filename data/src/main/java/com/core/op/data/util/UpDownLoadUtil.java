package com.core.op.data.util;

import com.core.op.data.api.exception.UploadException;
import com.domain.interfaces.OnProgressListener;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.R.attr.data;


/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/12/20
 */
public class UpDownLoadUtil {

    /**
     * 上传图片文件
     *
     * @return
     */
    public static MultipartBody.Part uploadImageFile(String param, String path, OnProgressListener onProgressListener) {
        File file = new File(path);
        if (!file.exists())
            throw new UploadException("upload image file not exists!");
        RequestBody requestBody = new ProgressRequestBody(RequestBody.create(MediaType.parse("multipart/form-data"), file), onProgressListener);
        return MultipartBody.Part.createFormData(param, file.getName(), requestBody);
    }
}
