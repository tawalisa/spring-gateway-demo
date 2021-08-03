package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class Momo {

    @Test
    public void test() {
        AtomicReference<Integer> a = new AtomicReference<>(100);
        Mono<Integer> mono = Mono.fromCallable(() -> {
            log.info("1111111111111111111");
            Thread.sleep(10000L);
            log.info("222222222222222222");
//            a.set(1 / 0);
            return a.get();
        });
//        .map(integer -> {
////            int aa = 1/0;
//            return integer + 1;
//        });
//        log.info(String.valueOf(mono.block()));
//        log.info(String.valueOf(mono.toFuture().get()));
//                .retryWhen(Retry.from(retrySignalFlux -> (
//                retrySignalFlux.
//                ))).doOnSuccess(integer -> {
//            log.info("doOnSuccess---{}", integer);
//        })
//                .then(Mono.just(1)).doOnNext(integer -> {
//            log.info("onnext---{}", integer);
//        }).doOnError(error -> {
//            log.info("doOnError---{}", error.getMessage());
//            error.printStackTrace();
//        }).timeout(Duration.ofSeconds(100)).subscribeOn(Schedulers.elastic());
//        Thread.sleep(1000);
        log.info("33333333333333");
        try{
//            System.out.println(mono.timeout(Duration.ofMillis(100)).block());
            System.out.println(mono.toFuture().get(200, TimeUnit.MILLISECONDS));
        }catch (Exception e){
            log.error(e.getMessage());
        }

//        log.info(String.valueOf(mono.timeout(Duration.ofMillis(100)).toFuture().get(200, TimeUnit.MILLISECONDS)));
        log.info("44444444444444444");
    }

    @Test
    void test1() throws InterruptedException, ExecutionException {
        CompletableFuture<String> mono = Mono.fromCallable(() -> {

            log.info(Thread.currentThread().getName() + " mock http response");
            Thread.sleep(1000L);
            return "Http response as string";

        }).toFuture();

//        Thread.sleep(1000L);
//        log.info(Thread.currentThread().getName() +" main thread 1111");
//        Thread.sleep(1000L);
        try {
            log.info("11111111111111");
            log.info(mono.get(100, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Test
    void test2() throws InterruptedException, ExecutionException {
        CompletableFuture<String> mono = Mono.fromCallable(() -> {

            System.out.println(Thread.currentThread().getName() + " mock http response");

            return "Http response as string";

        }).timeout(Duration.ofNanos(1)).toFuture();
        Thread.sleep(1000L);
        System.out.println(Thread.currentThread().getName() +" main thread 1111");
        Thread.sleep(1000L);
        System.out.println(mono.get());
    }
}
