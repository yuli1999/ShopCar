package com.umeng.soexample.shopcar.net;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * data:2018/11/21
 * author: HJL (磊)
 * function:
 */
public class HttpOk {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public HttpOk() {
    }

    public HttpOk get(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        StringBuilder stringBuilder = new StringBuilder();
        String s = stringBuilder.toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application charset=utf-8"), s);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                listener.success(response.body().string());
            }
        });
        return this;
    }


    private HttpOkListener listener;

    public void Result(HttpOkListener listener) {
        this.listener = listener;
    }


    public interface HttpOkListener {
        void success(String data);

        void Faile(String msg);
    }

}
