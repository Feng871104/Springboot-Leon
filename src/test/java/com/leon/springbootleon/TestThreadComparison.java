package com.leon.springbootleon;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;

/**
 * Benchmark to demonstrate Java 21 Virtual Threads performance with rate limiting.
 */
public class TestThreadComparison {

    // Total number of tasks to execute
    // 預設值 (如果環境變數沒設定時使用)
    private static final int DEFAULT_TASKS_COUNT = 6;
    private static final int DEFAULT_CONCURRENCY_LIMIT = 6; // 通常併發數會比總數小，避免瞬間把目標網站打掛

    private static int tasksCount;
    private static int concurrencyLimit;

    private static final LongAdder successCounter = new LongAdder();
    private static final LongAdder failureCounter = new LongAdder();

    private static JdkClientHttpRequestFactory getJdkClientHttpRequestFactory() {
        var httpClient = HttpClient.newBuilder()
                .executor(Executors.newVirtualThreadPerTaskExecutor())
                .version(HttpClient.Version.HTTP_2)
                .build();
        var factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(10000);
        return factory;
    }

    private static final RestClient restClient = RestClient.builder()
            .requestFactory(getJdkClientHttpRequestFactory())
            .baseUrl("https://core.kitazaki.com.tw/")
            .build();

    @Test
    void case1() {
        // 從環境變數讀取配置
        tasksCount = getEnvInt("BENCHMARK_TASKS", DEFAULT_TASKS_COUNT);
        concurrencyLimit = getEnvInt("BENCHMARK_LIMIT", DEFAULT_CONCURRENCY_LIMIT);

        System.out.println("========================================");
        System.out.println("環境變數配置:");
        System.out.println("總任務數 (BENCHMARK_TASKS): " + tasksCount);
        System.out.println("併發限制 (BENCHMARK_LIMIT): " + concurrencyLimit);
        System.out.println("========================================");
        System.out.println("Starting benchmark...");
        testVirtualThreads();
    }

    /**
     * 讀取環境變數的 Helper 方法
     */
    private static int getEnvInt(String key, int defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("警告: 環境變數 " + key + " 格式錯誤 (" + value + ")，將使用預設值: " + defaultValue);
            return defaultValue;
        }
    }

    /**
     * Simulates a blocking I/O operation by making an HTTP request.
     * Virtual threads are ideal for this kind of "wait-heavy" workload.
     */
    @SneakyThrows
    private static boolean killWeb() {
        try {
            restClient.get()
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36")
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void testVirtualThreads() {

        successCounter.reset();
        failureCounter.reset();

        long start = System.currentTimeMillis();
        // Limit the number of concurrent requests to prevent overwhelming the server or local resources
        Semaphore limiter = new Semaphore(concurrencyLimit);
        // Java 21+: Creates an Executor that starts a new Virtual Thread for each task.
        // Using try-with-resources ensures Structured Concurrency.
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < tasksCount; i++) {
                try {
                    // Acquire a permit before submitting.
                    // This blocks the main thread if 1000 tasks are currently running.
                    limiter.acquire();
                    int finalI = i;
                    executor.submit(() -> {
                        try {
                            if (killWeb()) {
                                successCounter.increment();
                            } else {
                                failureCounter.increment();
                            }
                            ; // Execute the blocking task
                        } finally {
                            // Crucial: Release the permit back to the pool.
                            // This signals that a slot is free for the next task.
                            limiter.release();
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        // The implicit executor.close() waits here until ALL submitted tasks are completed.
        long end = System.currentTimeMillis();
        System.out.printf("Virtual Threads execution time: %d ms%n", (end - start));
        System.out.println("========================================");
        System.out.println("- 成功請求數 ( Success ): " + successCounter.sum());
        System.out.println("- 失敗請求數: ( Failure ): " + failureCounter.sum());
        System.out.printf("- 每秒交易吞吐量 ( Transactions Per Second ) : %.2f%n", (double) tasksCount / ((end - start) / 1000.0));
        System.out.println("### TPS 越高最好，直到不再上升即為最大處理極限");
        System.out.println("========================================");
    }

}
