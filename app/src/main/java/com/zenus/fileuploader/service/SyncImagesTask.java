package com.zenus.fileuploader.service;

import android.os.AsyncTask;
import android.os.Environment;

import com.zenus.fileuploader.AppApplication;
import com.zenus.fileuploader.R;
import com.zenus.fileuploader.network.Callback;
import com.zenus.fileuploader.util.Constants;
import com.zenus.fileuploader.util.Logger;
import com.zenus.fileuploader.util.Utility;

import java.io.File;
import java.util.ArrayList;

public class SyncImagesTask extends AsyncTask<Void, String, String> {
    private static final String t = SyncImagesTask.class.getName();
    private FileSyncListener mStateListener;
    private int callbackReceived = 0;
    private int numberOfFailedSync = 0;
    private int numberOfSynced = 0;

    @Override
    protected String doInBackground(Void... params) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.FOLDER_CAMERA_IMAGES);
        ArrayList<String> filesInFolder = Utility.getFilesInDir(mediaStorageDir.getPath() + "/");
        if (filesInFolder != null) {
            for (String filename : filesInFolder) {
                String[] msg = AppApplication.getInstance().getApiController().postMultipartImage(filename, new Callback() {
                    @Override
                    public void before(Object... params) {
                        Logger.log(t, "Before SyncImagesTask Callback");
                    }

                    @Override
                    public Object after(Object... params) {
                        Logger.log(t, "After SyncImagesTask Callback");
                        callbackReceived++;
                        return null;
                    }

                    @Override
                    public void error(Object... params) {
                        Logger.log(t, AppApplication.getContext().getString(R.string.server_error));
                        numberOfFailedSync++;
                        callbackReceived++;
                    }
                });
            }

            while (filesInFolder != null && callbackReceived != filesInFolder.size()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return AppApplication.getContext().getString(R.string.sync_images_finish_result, numberOfSynced, numberOfFailedSync);
    }

    @Override
    protected void onPostExecute(String value) {
        synchronized (this) {
            if (mStateListener != null) {
                mStateListener.recordsSyncComplete(value);
            }
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        synchronized (this) {
            if (mStateListener != null) {
                mStateListener.progressUpdate(values[0], new Integer(values[1]).intValue(),
                        new Integer(values[2]).intValue());
            }
        }

    }

    public void setSyncListener(FileSyncListener sl) {
        synchronized (this) {
            mStateListener = sl;
        }
    }
}
