package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ClientTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        WebClient webClient = WebClient.builder().build();
        WebClient.RequestBodyUriSpec spec = webClient.method(HttpMethod.GET);

        CompletableFuture<String> body = spec.uri("http://localhost:28080/demo5").retrieve().bodyToMono(String.class).toFuture();
        Thread.sleep(1000* 10);
        System.out.println(body.get());
    }

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        WebClient webClient = WebClient.builder().build();
        WebClient.RequestBodyUriSpec spec = webClient.method(HttpMethod.GET);

        String body = spec.uri("http://localhost:28080/demo6").retrieve().bodyToMono(String.class).block(Duration.ofSeconds(1L));
//        Thread.sleep(1000 * 10);
        System.out.println(body);
    }
}
