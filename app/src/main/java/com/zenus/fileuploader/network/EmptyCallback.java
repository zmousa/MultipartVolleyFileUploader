package com.zenus.fileuploader.network;


import com.zenus.fileuploader.util.Logger;

public final class EmptyCallback implements RestCallback {

    @Override
    public void onResponse(Object response) {
        // Just Do noting
        Logger.log("APICall", String.valueOf(response));
    }

    @Override
    public void onError(Object error) {
        // Just Do noting
        Logger.log("APICall", String.valueOf(error));
    }
}
