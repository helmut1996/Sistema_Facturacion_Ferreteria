package com.example.facturacioncarpintero;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
    private ExecutorService service;

    private ThreadPoolManager() {
        int num = Runtime.getRuntime().availableProcessors() * 20;
        service = Executors.newFixedThreadPool(num);
    }

    private static final com.example.facturacioncarpintero.ThreadPoolManager manager = new com.example.facturacioncarpintero.ThreadPoolManager();

    public static com.example.facturacioncarpintero.ThreadPoolManager getInstance() {
        return manager;
    }

    public void executeTask(Runnable runnable) {
        service.execute(runnable);
    }

}
