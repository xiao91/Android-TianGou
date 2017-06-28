package com.quanmin.sky.listener;

/**
 * 文件上传进度条
 * @author xiao 2017-3-30.
 */

public interface OnFileUploadProgressListener {

    /**
     * 上传进度
     * @param currentBytes 当前值
     * @param totalBytes 总共值
     */
    void onProgress(long currentBytes, long totalBytes);

}
