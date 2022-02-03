package com.or.Task;

import com.or.NetworkUtil;
import com.or.model.Download;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class DownloadTask implements Runnable{

    private Download download;

    public DownloadTask(Download download) {
        this.download = download;
    }

    public DownloadTask(){}

    @Override
    public void run() {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(download.getFileName() + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (download.isActive()) {

                download.addData(NetworkUtil.getCurrentSpeed());
                try {
                    writer.write("Download progress: " + download.getProgress() + " with current speed: "
                            + NetworkUtil.getCurrentSpeed() + " | data obtained: " + download.getDataFetched() +
                            " with frequency of: " + NetworkUtil.getCurrentFrequency() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(NetworkUtil.getCurrentFrequency());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (download.getDataFetched() == download.getSize()) {
                    Calendar date = Calendar.getInstance();
                    download.setFinishedAt(date);
                    download.stop();
                    System.out.println(download + " as finished at: " + download.getFinishedAt().getTime());
             }
        }
    }
}

