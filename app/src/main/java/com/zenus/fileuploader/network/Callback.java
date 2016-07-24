package com.zenus.fileuploader.network;

public interface Callback {
    void before(Object... params);

    Object after(Object... params);

    void error(Object... params);
}
