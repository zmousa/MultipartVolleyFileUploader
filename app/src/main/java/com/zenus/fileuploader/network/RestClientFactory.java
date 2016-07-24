package com.zenus.fileuploader.network;

public class RestClientFactory {

    public static VolleyMultipartRestClient getMultipartClient(){
        return new VolleyMultipartRestClient();
    }
}
