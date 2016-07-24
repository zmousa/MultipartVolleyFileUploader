package com.zenus.fileuploader.controller;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.android.volley.NetworkResponse;
import com.zenus.fileuploader.AppApplication;
import com.zenus.fileuploader.network.Callback;
import com.zenus.fileuploader.network.DataPart;
import com.zenus.fileuploader.network.RestCallback;
import com.zenus.fileuploader.network.RestClientFactory;
import com.zenus.fileuploader.network.RestRouter;
import com.zenus.fileuploader.util.Constants;
import com.zenus.fileuploader.util.Utility;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ApiController {

    private final String POST_IMAGE_PARAM = "image";
    private final String POST_IMAGE_NAME_PARAM = "image_name";
    private final String USERNAME = "USERNAME";
    private final String PASSWORD = "PASSWORD";

    public String[] postMultipartImage(final String imageName, final Callback callback){
        final String msg[] = {""};

        if ("".equals(imageName)) {
            if (callback != null)
                callback.error("empty file");
            return msg;
        }

        if (callback != null)
            callback.before(this);

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.FOLDER_CAMERA_IMAGES);
        final File imageToSend = new File(mediaStorageDir.getPath() + File.separator + imageName);
        byte[] fileBytes = new byte[0];
        try {
            Uri filePath = Uri.fromFile(imageToSend);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(AppApplication.getContext().getContentResolver(), filePath);
            fileBytes = Utility.getImageData(bitmap);
        } catch (Exception e){
            if (callback != null)
                callback.error(e.getMessage());
            msg[0] = e.getMessage();
            return msg;
        }

        if (fileBytes != null && fileBytes.length > 0) {
            String route = RestRouter.getDefault().to(RestRouter.Route.IMAGE_POST);

            Map<String,String> postParam = new HashMap<>();
            postParam.put(POST_IMAGE_NAME_PARAM, imageName);

            Map<String, DataPart> bodyParam = new HashMap<>();
            bodyParam.put(POST_IMAGE_PARAM, new DataPart(imageName, fileBytes, Utility.getMimeType(imageName)/*"image/jpeg"*/));

            RestClientFactory.getMultipartClient().postMultipartFile(route, postParam, bodyParam, getAuthenticationHeaders(), new RestCallback<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    String resultResponse = new String(response.data);
                    if (imageToSend != null && imageToSend.exists())
                        imageToSend.delete();
                    if (callback != null)
                        callback.after(resultResponse);
                }

                @Override
                public void onError(Object error) {
                    if (error != null) {
                        msg[0] = error.toString();
                        callback.error(error);
                    }
                }
            });
        }

        return msg;
    }

    private Map<String, String> getAuthenticationHeaders(){
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(USERNAME, "");
        headerMap.put(PASSWORD, "");
        return headerMap;
    }
}
