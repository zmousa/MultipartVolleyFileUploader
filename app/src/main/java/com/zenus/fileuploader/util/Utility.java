package com.zenus.fileuploader.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.zenus.fileuploader.AppApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class Utility {
    public static ArrayList<String> getFilesInDir(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<>();

        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();

        if (files.length == 0)
            return null;
        else {
            for (int i=0; i<files.length; i++)
                MyFiles.add(files[i].getName());
        }
        return MyFiles;

    }

    public static byte[] getImageData(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String getMimeType(String url)
    {
        String extension = url.substring(url.lastIndexOf("."));
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extension);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap);
        return mimeType;
    }

    public static void toast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toast(String msg) {
        toast(AppApplication.getContext(), msg);
        Logger.log("toast", msg);
    }
}
