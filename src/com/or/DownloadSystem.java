package com.or;

import com.or.Task.DownloadTask;
import com.or.model.Download;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DownloadSystem {

    public static final DownloadSystem instance = new DownloadSystem();

    private DownloadSystem(){
        this.isRunning = true;
        thread = new Thread(){
            @Override
            public void run() {
                while(isRunning){
                    try {
                        Thread.sleep(20000);
                        for (Download download : downloads) {
                            if(download.getDataFetched() == download.getSize()){
                                System.out.println(download + " has been deleted");
                                downloads.remove(download);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private Thread thread;
    private Boolean isRunning;
    private static final Set<Download> downloads = new HashSet<>();
    private static final Scanner scanner = new Scanner(System.in);

    public void startSystem() {

        while (true) {
            System.out.println("Hello Expensive friend! Please choose between the following - ");
            System.out.println("1 - Download a new file | 2 - Pause download | 3 - Resume download |" +
                    " 4 - View all downloads | 5 - Exit :");
            int userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    newDownload();
                    break;
                case 2:
                    pauseDownload();
                    break;
                case 3:
                    resumeDownload();
                    break;
                case 4:
                    viewDownloads();
                    break;
                case 5:
                    ExitSystem();
                    return;
            }
        }
    }

    private void newDownload(){

        System.out.println("In order to create a new Download Please enter the file name: ");
        String name = scanner.next();
        System.out.println("Please enter the file url: ");
        String url = scanner.next();
        System.out.println("Please enter the file size: ");
        double size = scanner.nextDouble();

        Download newDownload = new Download(name, url, size);
        downloads.add(newDownload);

        Thread thread = new Thread(new DownloadTask(newDownload));
        thread.start();
    }

    private void pauseDownload(){

        System.out.println("In order to pause a Download Please enter the file name: ");
        String name = scanner.next();
        System.out.println("Please enter the file url: ");
        String url = scanner.next();

        for (Download download : downloads) {
            if(download.getFileName().equals(name) && download.getUrl().equals(url)){
                download.stop();
                System.out.println("Download paused in current status: " + download.getProgress() + " from: " + "100%");
            }
        }
     }

    private void resumeDownload(){

        System.out.println("In order to resume a Download Please enter the file name: ");
        String name = scanner.next();
        System.out.println("Please enter the file url: ");
        String url = scanner.next();

        for (Download download : downloads) {
            if(download.getFileName().equals(name) && download.getUrl().equals(url)){
                download.start();
                Thread thread = new Thread(new DownloadTask(download));
                thread.start();
                System.out.println("Download has resumed from last status: " + download.getProgress());
            }
        }
    }

    private void viewDownloads() {

        for (Download download : downloads) {
            System.out.println(download + " | Current status " + download.getProgress());
        }
    }

    private void ExitSystem() {
        System.out.println("Goodbye!");
        this.isRunning = false;
        for (Download download : downloads) {
            download.stop();
        }
    }
}
