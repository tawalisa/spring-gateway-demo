package com.example.demo;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
//        Flux.using(()-> {
//            return 1;
//        },c -> {
//            return Flux.just(c+3);
//                }
//        ,(a)-> {
//            System.out.println(11+a);
//        }).subscribeOn(Schedulers.single()).toIterable().forEach(System.out::println);
        DemoFeed<PriceTick> feed = new DemoFeed<>();
        Flux<PriceTick> flux =
                Flux.create(emitter ->
                {
                    feed.register(new Listener<PriceTick>() {
                        @Override
                        public void priceTick(PriceTick event) {
                            System.out.println("111111111111");
                            emitter.next(event);
                            System.out.println("222222222222");
                            if (event.isLast()) {
                                emitter.complete();
                            }
                        }

                        @Override
                        public void error(Throwable e) {
                            emitter.error(e);
                        }
                    });
                }, FluxSink.OverflowStrategy.BUFFER);

        ConnectableFlux<PriceTick> hot = flux.publish();
        hot.subscribeOn(Schedulers.fromExecutor(Executors.newCachedThreadPool()), true).subscribe(priceTick -> {
            System.out.println(priceTick+"===" + Thread.currentThread().getName());
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
            }
        });
        hot.connect();
        System.out.println(Thread.currentThread().getName());
//        new Thread(()->{
//            for(int i = 10;i < 20 ; i++){
//                PriceTick priceTick = new PriceTick();
//                priceTick.setPrice(i+"");
//                feed.send(priceTick);
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                }
//            }
//        }).start();
        for(int i = 0;i < 10 ; i++){
            PriceTick priceTick = new PriceTick();
            priceTick.setPrice(i+"");
            feed.send(priceTick);
            Thread.sleep(1000L);
        }

    }
    static class PriceTick{
        boolean last;
        String price;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public boolean isLast() {
            return last;
        }

        @Override
        public String toString() {
            return "PriceTick{" +
                    "last=" + last +
                    ", price='" + price + '\'' +
                    '}';
        }
    }
    static class DemoFeed<T> {
        Listener listener ;
        public void register(Listener listener) {
            this.listener = listener;
        }
        void send(T t){
            listener.priceTick(t);
        }
    }

    interface Listener<T>{
        void priceTick(T t);
        void error(Throwable e);
    }
}
