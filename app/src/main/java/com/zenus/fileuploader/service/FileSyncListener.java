package com.zenus.fileuploader.service;

public interface FileSyncListener {
    void recordsSyncComplete(String result);
    void progressUpdate(String currentFile, int progress, int total);
}
