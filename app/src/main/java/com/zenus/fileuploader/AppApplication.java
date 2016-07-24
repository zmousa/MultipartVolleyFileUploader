package com.zenus.fileuploader;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zenus.fileuploader.controller.ApiController;

public class AppApplication extends Application {
    public static final String APP_TAG = "ChatClient";
    private static AppApplication instance;
    private RequestQueue requestQueue;
    private ApiController apiController;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setApiController(new ApiController());
    }

    public static synchronized AppApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return AppApplication.getInstance().getApplicationContext();
    }


    public synchronized RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }

    public ApiController getApiController() {
        return apiController;
    }

    public void setApiController(ApiController apiController) {
        this.apiController = apiController;
    }
}
