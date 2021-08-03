package com.lijia.code.condition;

import com.lijia.code.OnLocalRun;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional({OnLocalRun.class})
public @interface ConditionalOnLocalRun {
    String value() default "true";
}
