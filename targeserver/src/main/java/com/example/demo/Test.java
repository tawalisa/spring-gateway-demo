package com.example.demo;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        test1();
    }

    private static void test2() throws InterruptedException {


        Mono<String> mono = Mono.fromCallable(() -> {
            System.out.println(Thread.currentThread().getName() + " mock http response 1");
            Thread.sleep(3000L);
            System.out.println(Thread.currentThread().getName() + " mock http response 2");
            return "Http response as string";
        });
        Thread.sleep(1000L);
        System.out.println(Thread.currentThread().getName() +" main thread 1111");
        Thread.sleep(1000L);


//        System.out.println(mono.subscribeOn(Schedulers.parallel()).block(Duration.ofNanos(20000000L)));
//        System.out.println(mono.subscribe(System.out::println));
        System.out.println(mono.subscribeOn(Schedulers.parallel()).timeout(Duration.ofNanos(10000000L)).subscribe(System.out::println));
//        mono.timeout(Duration.ofNanos(1000L)).subscribe(m-> System.out.println("result: "  +m.toUpperCase() ));

        System.out.println(Thread.currentThread().getName() +" main thread 2222");
        Thread.sleep(1000L * 10L);
    }

    private static void test1() throws InterruptedException, ExecutionException {
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
