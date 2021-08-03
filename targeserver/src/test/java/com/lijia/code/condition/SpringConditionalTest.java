package com.lijia.code.condition;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Configuration
@ComponentScan(basePackages = {"com.lijia.code.condition"})
class SpringTestContext {

    @Bean(name="env1")
    @SymbologyValidationRule
    public Env env1(){
        return new Env(false);
    }

    @Bean(name = "notebookPC")
    @ConditionalOnExpression("@env1.isLocal()")
    public Computer computer1(){
        return new Computer("笔记本电脑");
    }

    @ConditionalOnMissingBean(Computer.class)
    @Bean("reservePC")
    public Computer computer2(){
        return new Computer("备用电脑");
    }
}

interface Person{
    String say();
}

@Component("person")
@ConditionalOnLocalRun
@SymbologyValidationRule
class Chinese implements  Person{

    @Override
    public String say() {
        return "I am Chinese";
    }
}

//@Component("person")
//@ConditionalOnMissingBean(Person.class)
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//class English implements  Person{
//
//    @Override
//    public String say() {
//        return "I am English";
//    }
//}
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
class Computer{
    private String name;
}

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
class Env{
    private boolean isLocal;
}

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringTestContext.class)
public class SpringConditionalTest {
    @Autowired
    Computer computer;

    @Autowired
    Person person;

    @Autowired
    private ApplicationContext context;


    @Test
    public void test(){
        System.out.println(computer.getName());
    }

    @Test
    public void testPerson(){
        System.out.println(person.say());
        System.out.println(person);
        System.out.println(context.getBean("person"));
        System.out.println(context.getBean("person"));
        context.getBeansWithAnnotation(SymbologyValidationRule.class).keySet().forEach(System.out::println);
    }
}
