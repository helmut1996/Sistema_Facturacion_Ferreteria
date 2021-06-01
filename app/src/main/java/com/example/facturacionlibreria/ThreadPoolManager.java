package com.example.facturacionlibreria;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
    private ExecutorService service;

    private ThreadPoolManager() {
        int num = Runtime.getRuntime().availableProcessors() * 20;
        service = Executors.newFixedThreadPool(num);
    }

    private static final com.example.facturacionlibreria.ThreadPoolManager manager = new com.example.facturacionlibreria.ThreadPoolManager();

    public static com.example.facturacionlibreria.ThreadPoolManager getInstance() {
        return manager;
    }

    public void executeTask(Runnable runnable) {
        service.execute(runnable);
    }

}
