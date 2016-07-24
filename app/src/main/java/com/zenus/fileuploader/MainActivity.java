package com.zenus.fileuploader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.zenus.fileuploader.service.FileSyncListener;
import com.zenus.fileuploader.service.SyncImagesTask;
import com.zenus.fileuploader.util.Utility;

public class MainActivity extends Activity implements FileSyncListener {
    private SyncImagesTask mImagesSyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImagesSyncTask = new SyncImagesTask();
        mImagesSyncTask.setSyncListener(MainActivity.this);
        mImagesSyncTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void recordsSyncComplete(String result) {
        Utility.toast(result);
    }

    @Override
    public void progressUpdate(String currentFile, int progress, int total) {

    }
}
