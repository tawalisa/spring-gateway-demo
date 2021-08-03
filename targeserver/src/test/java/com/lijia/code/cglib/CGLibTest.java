package com.lijia.code.cglib;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.FixedValue;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

public class CGLibTest {
    @Test
    public void test(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PersonService.class);
        enhancer.setCallback((FixedValue) () -> "11");
        PersonService proxy = (PersonService) enhancer.create();

        String res = proxy.sayHello1(null);

        System.out.println(res);
//        System.out.println(proxy.lengthOfName(null));
    }

    @Test
    public void test1(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PersonService.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                return "Hello Tom!";
            } else {
                return proxy.invokeSuper(obj, args);
            }
        });

        PersonService proxy = (PersonService) enhancer.create();

        int lengthOfName = proxy.lengthOfName("Mary");

        System.out.println(lengthOfName);

    }

    @SneakyThrows
    @Test
    public void beanTest(){
        BeanGenerator beanGenerator = new BeanGenerator();

        beanGenerator.addProperty("name", String.class);
        Object myBean = beanGenerator.create();
        Method setter = myBean.getClass().getMethod("setName", String.class);
        setter.invoke(myBean, "some string value set by a cglib");

        Method getter = myBean.getClass().getMethod("getName");
        System.out.println(getter.invoke(myBean));
    }
}

class PersonService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
    public String sayHello1(String name) {
        return "Hello " + name;
    }
    public Integer lengthOfName(String name) {
        return name.length();
    }
}