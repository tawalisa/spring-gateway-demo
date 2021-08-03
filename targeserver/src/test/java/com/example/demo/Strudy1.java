package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class Strudy1 {

    @Test
    public void test(){

        Mono<String> data = Mono.just("foo");
        data.map(data1 -> data1 + "a").subscribe(s -> System.out.println(s));
        String a = null;
        switch (a){
            case "1":
                System.out.println("1");
                break;

            case "2":
                System.out.println("2");
                break;
            default:
                System.out.println("2222");
        }
    }
}
