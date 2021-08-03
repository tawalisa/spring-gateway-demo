package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
public class DemoTest {

    @Test
    public void test() throws InterruptedException, ExecutionException {
        String input = "a,b";
        ParallelFlux<String> flux = getData(input);
        log.info("----------------1");
        Mono<List<String>> f = flux.sequential()
                .collectList().log().doOnNext(strings -> log.info("!!!!!!!!!!{}", strings));
        log.info("----------------2");
        f.subscribe(System.out::println);
        Thread.sleep(1000*10L);
    }

    private String combineDate(Flux<String> flux) {
        StringBuffer a = new StringBuffer();
        flux.doOnNext(s -> a.append(s));
//        flux.filter(f -> f!=null).doOnEach(s -> System.out.println(s.get())).blockLast();
        log.info(a+"----");
        return a.toString();
    }

    private ParallelFlux<String> getData(String input) {
        Scheduler scheduler = Schedulers.parallel();
        return Flux.fromArray(input.split(",")).parallel(2).runOn(scheduler).map(s -> mockhttp(s));
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
}
