package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池配置
 */
public class ConnectionPoolSetting {
    public static ExecutorService executorService;
    // 配置线程池大小
    private static final int poolSize = 3;

    static {
        executorService = Executors.newFixedThreadPool(poolSize);
    }
}
