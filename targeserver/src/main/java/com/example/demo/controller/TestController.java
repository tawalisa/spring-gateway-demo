package com.example.demo.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/**")
    public Mono<String> getGreeting(ServerHttpRequest serverHttpRequest) {
        return Mono.just(serverHttpRequest.getURI().toString());
    }
}
