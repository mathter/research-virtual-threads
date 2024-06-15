package io.github.mathter.test.virtual.threads;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final int VIRTUAL_THREAD_COUNT = 1000000;

    public static void main(String[] args) throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(VIRTUAL_THREAD_COUNT);
        final long start = System.currentTimeMillis();

        for (int i = 0; i < VIRTUAL_THREAD_COUNT; i++) {
            Thread.ofVirtual().name("name_" + i).start(new Runnable() {
                @Override
                public void run() {
                    // This is load of thread
                    for (int i = 0; i < 100; i++)
                        System.out.println(
                                Thread.currentThread().getName() + " run at " + LocalDateTime.now() + " load: " + load()
                        );
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        System.out.println("Complete ... in " + (System.currentTimeMillis() - start) / 1000f + " seconds");
    }

    private static String load() {
        return IntStream.range(0, 100).mapToObj(e -> String.valueOf(e)).collect(Collectors.joining(", "));
    }
}
