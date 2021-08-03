package com.lijia.code.redis;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"com.lijia.code.redis"})
class SpringRedisTestContext {

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//        return new LettuceConnectionFactory("a205469-rsa-redis-dev.1swv5v.clustercfg.use1.cache.amazonaws.com", 6379);
        RedisClusterConfiguration clusterConfiguration = new
                RedisClusterConfiguration();
        clusterConfiguration.clusterNode("a205469-rsa-redis-dev.1swv5v.clustercfg.use1.cache.amazonaws.com", 6379);
        return new LettuceConnectionFactory(clusterConfiguration);
    }

//    @Bean
//    public RedisClusterConfiguration redisClusterConfiguration() {
//        RedisClusterConfiguration clusterConfiguration = new
//                RedisClusterConfiguration();
//        clusterConfiguration.clusterNode("a205469-rsa-redis-dev.1swv5v.clustercfg.use1.cache.amazonaws.com", 6379);
//        return clusterConfiguration;
//    }

    @Bean
    ReactiveStringRedisTemplate redisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        return new ReactiveStringRedisTemplate(reactiveRedisConnectionFactory);
    }

}
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringRedisTestContext.class)
@Slf4j
public class RedisTest {
    private static final String LIST_NAME = "testkey1";
    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

    @Test
    public void givenListAndValues_whenLeftPushAndLeftPop_thenLeftPushAndLeftPop() {
        ReactiveListOperations<String, String> reactiveListOps = redisTemplate.opsForList();

        Mono<Long> lPush = reactiveListOps.leftPushAll(LIST_NAME, "first", "second")
                .log("Pushed");
        System.out.println("===========>>>"+lPush.block());
        Mono<String> lPop = reactiveListOps.leftPop(LIST_NAME)
                .log("Popped");
//        lPop.doOnSubscribe(System.out::println);
        System.out.println("------>>>"+lPop.block());
    }

    @Test
    public void hsetTest() throws InterruptedException {
//        ReactiveHashOperations<String, Object, Object> reactiveListOps = redisTemplate.opsForHash();
//
//        Flux<Map.Entry<Object, Object>> scan = reactiveListOps.scan("Currency.Names:500269",ScanOptions.scanOptions().match("Name:505074;404500*").build());
//        scan.toIterable().forEach(objectObjectEntry -> System.out.println(objectObjectEntry.getKey() + "===="+ objectObjectEntry.getValue()));

        ReactiveValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        System.out.println("====");
        try{
            Disposable a = valueOps.get("testkey317").doOnError(throwable -> log.error("=======", throwable)).subscribe(val -> log.info("---->{}", val));
//            System.out.println(valueOps.get("testkey317").block());
        }catch (Exception e){
            e.printStackTrace();
        }

        Thread.sleep(1000L* 5);
//                .subscribe(val ->log.info("---->{}",val));
//        scan.subscribe(val ->log.info("->{}",val));
    }


    @Test
    public void name() throws InterruptedException {
        ReactiveHashOperations<String, Object, Object> reactiveListOps = redisTemplate.opsForHash();

        Flux<Map.Entry<Object, Object>> scan = reactiveListOps.scan("daapi.Metadata2.ValueDomain.ValueDomainEnumeration.Names:1004366704;1010190",ScanOptions.scanOptions().match("Name:404500;505126*").build());
//        scan.toIterable().forEach(objectObjectEntry -> System.out.println(objectObjectEntry.getKey() + "===="+ objectObjectEntry.getValue()));
        Object result = scan.map(Map.Entry::getValue).defaultIfEmpty("====").blockFirst();
        System.out.println(result);
//        Map.Entry<Object, Object> result = scan.blockFirst();
//        System.out.println(result);
//        log.info("{}==========={}", result.getKey(), result.getValue());


//        Thread.sleep(1000L* 5);
//                .subscribe(val ->log.info("---->{}",val));
//        scan.subscribe(val ->log.info("->{}",val));
    }
}
