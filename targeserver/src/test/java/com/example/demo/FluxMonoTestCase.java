package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
public class FluxMonoTestCase{
    @Test
    public void window(){
        //一维Flux
        Flux<String> stringFlux1 = Flux.just("a","b","c","d","e","f","g","h","i");
        stringFlux1.count().subscribe(System.out::println);

        //二维Flux
        Flux<Flux<String>> stringFlux2 = stringFlux1.window(2);
        stringFlux2.subscribe(s -> {System.out.println("====");s.subscribe(System.out::println);});
        stringFlux2.count().subscribe(System.out::println);
        //三维Flux
        Flux<Flux<Flux<String>>> stringFlux3 = stringFlux2.window(3);
        stringFlux3.count().subscribe(System.out::println);
    }

    @Test
    public void merge() throws InterruptedException {
        Flux<Long> longFlux = Flux.interval(Duration.ofMillis(100)).take(10);
        Flux<Long> longFlux2 = Flux.interval(Duration.ofMillis(100)).take(10);
        Flux<Long> longFlux3 = Flux.merge(longFlux,longFlux2);
        longFlux3.subscribe(val ->log.info("->{}",val));
        Thread.sleep(2000);
    }

    @Test
    public void merge1() throws InterruptedException {
        Flux<Integer> longFlux = Flux.range(1, 10);
        Flux<Integer> longFlux2 = Flux.range(21, 10);
        Flux<Integer> longFlux3 = Flux.merge(longFlux,longFlux2);
        longFlux3.single().subscribe(System.out::println);
        Thread.sleep(2000);
    }

    @Test
    public void test() throws InterruptedException, ExecutionException {
//        Scheduler scheduler = Schedulers.newParallel("parallel-scheduler", 4);
//        Scheduler scheduler = Schedulers.parallel();
        Scheduler scheduler = Schedulers.boundedElastic();
        String input = "a,b";
        Flux<String> flux = getData(input);
        log.info("----------------1");
        Mono<List<String>> f = flux.buffer().single();
        log.info("----------------2");
        Mono<String> flux1 = f.map(list -> list.get(0)).subscribeOn(scheduler);
        flux1.subscribe(System.out::println);
//        f.subscribe(System.out::println);
//        Mono<String> mono = Mono.fromCallable(()-> combineDate(flux));
//        mono.subscribe(System.out::println);
        Thread.sleep(1000*10L);
    }

    private String combineDate(Flux<String> flux) {
        StringBuffer a = new StringBuffer();
        flux.doOnNext(s -> a.append(s));
//        flux.filter(f -> f!=null).doOnEach(s -> System.out.println(s.get())).blockLast();
        log.info(a+"----");
        return a.toString();
    }

    private Flux<String> getData(String input) {
        return Flux.fromArray(input.split(",")).map(s -> mockhttp(s));
    }

    private String mockhttp(String s) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("1111111111111111111111" + s+Thread.currentThread().getName());
        return s +"=>";
    }
    @Test
    void test1(){
        Flux.range(1, 10).flatMap(integer -> Mono.just(integer))
                .parallel(2)
                .runOn(Schedulers.parallel())
                .sequential()
                .collectList()
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));

    }

}