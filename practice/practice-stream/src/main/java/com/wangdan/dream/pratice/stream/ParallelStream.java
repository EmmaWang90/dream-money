package com.wangdan.dream.pratice.stream;

import com.wangdan.dream.framework.ServiceBase;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ParallelStream extends ServiceBase {
    public ParallelStream(ServiceBase parent) {
        super(parent);
    }

    public void practice() {
        Integer[] integers = new Integer[1000];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = Integer.valueOf(i);
        }
//        Set<CompletableFuture> completableFutureSet = Arrays.asList(integers).stream()
//                .map(integer -> {
//                    CompletableFuture completableFuture = CompletableFuture.runAsync(()->{
//                        logger.info("begin to process integer:{}", integer);
//                        try {
//                            Thread.sleep(50);
//                        } catch (InterruptedException e) {
//                            logger.error("interupt", e);
//                        }
//                        logger.info("end to process integer:{}", integer);
//                    });
//                    return completableFuture;
//                })
//                .collect(Collectors.toSet());
//        completableFutureSet.stream().forEach(CompletableFuture::join);
        Arrays.asList(integers).parallelStream()
                .map(integer -> {
                    CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
                        logger.info("begin to process integer:{}", integer);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            logger.error("interupt", e);
                        }
                        logger.info("end to process integer:{}", integer);
                    });
                    return completableFuture;
                })
                .forEach(CompletableFuture::join);
    }
}
