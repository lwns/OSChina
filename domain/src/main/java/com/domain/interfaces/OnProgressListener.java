package com.domain.interfaces;

/**
 * @author op
 * @version 1.0
 * @description 上传下载进度监听器
 * @createDate 2016/12/20
 */
public interface OnProgressListener {

    /**
     * 上传，下载进度监听
     *
     * @param currentBytesCount 当前进度
     * @param totalBytesCount   总大小
     */
    void onProgress(long currentBytesCount, long totalBytesCount);
}
