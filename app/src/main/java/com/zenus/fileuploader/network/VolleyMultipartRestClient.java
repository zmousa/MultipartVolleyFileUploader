package com.zenus.fileuploader.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zenus.fileuploader.AppApplication;

import java.util.HashMap;
import java.util.Map;

public class VolleyMultipartRestClient {
    public void postMultipartFile(String url, final Map params, final Map<String, DataPart> body, final Map headers, final RestCallback callback){

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                if (callback != null)
                    callback.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> postParam = new HashMap<>(params);
                return postParam;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String,DataPart> postParam = new HashMap<>(body);
                return postParam;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> _headers = new HashMap<>(headers);
                return _headers;
            }
        };
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppApplication.getInstance().getRequestQueue().add(multipartRequest);
    }
}
