package com.xiao91.sky.mvp.model;

import android.util.Log;

import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;
import com.xiao91.sky.bean.AddFocus;
import com.xiao91.sky.bean.CancelFocus;
import com.xiao91.sky.utils.TGUrls;

/**
 * 关注
 * Created by xl on 2017/2/17 0017.
 */

public class FocusModel {

    private final MyOkHttp okHttp;

    public FocusModel() {
        okHttp = new MyOkHttp();
    }

    public void requestAddFocus(String userId, String uid, final OnCompleteDataListener<AddFocus> listener) {
        String url = TGUrls.FOCUS_ADD + "&user_id=" + userId + "&uid=" + uid;

        Log.e("focus", "url=" + url);

        okHttp.get()
                .tag(this)
                .url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e("focus", "数据：" + response);

                        Gson gson = new Gson();
                        AddFocus focus = gson.fromJson(response, AddFocus.class);

                        if (listener != null) {
                            listener.onComplete(focus);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (listener != null) {
                            listener.onError(500);
                        }
                    }
                });
    }

    public void requestCancelFocus(String focusId, final OnCompleteDataListener<CancelFocus> listener) {
        String url = TGUrls.FOCUS_CANCEL + "&focus_id=" + focusId;

        okHttp.get()
                .tag(this)
                .url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e("focus", "数据：" + response);

                        Gson gson = new Gson();
                        CancelFocus focus = gson.fromJson(response, CancelFocus.class);

                        if (listener != null) {
                            listener.onComplete(focus);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (listener != null) {
                            listener.onError(500);
                        }
                    }
                });
    }
}
