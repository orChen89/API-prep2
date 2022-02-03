package com.or.model;

import java.util.Calendar;

public class Download {

    private final String fileName;
    private final String url;
    private final double size;
    private int dataFetched;
    private boolean isActive = true;
    private Calendar finishedAt = null;

    public Download(String fileName, String url, double size) {
        this.fileName = fileName;
        this.url = url;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUrl() {
        return url;
    }

    public double getSize() {
        return size;
    }

    public double getDataFetched() {
        return dataFetched;
    }

    public boolean isActive() {
        return isActive;
    }

    public Calendar getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Calendar finishedAt) {
        this.finishedAt = finishedAt;
    }

    public void addData(int data) {
        this.dataFetched += data;
        if(this.dataFetched > this.getSize()){
            this.setDataFetched((int) this.getSize());
        }
    }

    public String getProgress() {
       double currentProgress =  (this.dataFetched * 100) / this.size;
       return currentProgress + "%";
    }

    public void stop() {
        isActive = false;
    }

    public void start() {
        isActive = true;
    }

    public void setDataFetched(int dataFetched) {
        this.dataFetched = dataFetched;
    }

    @Override
    public String toString() {
        return "Download{" +
                "fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", size=" + size +
                ", dataFetched=" + dataFetched +
                ", isActive=" + isActive +
                '}';
    }
}
