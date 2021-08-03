package com.example.demo.client;

import com.example.demo.model.Employee;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Client {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        WebClient client = WebClient.builder().baseUrl("http://localhost:8080").build();
        WebClient client = WebClient.create("http://localhost:8080");
        Mono<String> employeeMono = client.get()
                .uri("/employees/{id}", "abcd")
                .retrieve()
                .bodyToMono(String.class);
        System.out.println("111111111111111111111111111");
        CompletableFuture<String> f = employeeMono.toFuture();
        System.out.println("222222222222222222222222");
//        employeeMono.subscribe(System.out::println);
        System.out.println(f.get());

//        Thread.sleep(1000* 10L);

    }
}
