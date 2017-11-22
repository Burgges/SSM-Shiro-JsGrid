package com.hand.annotation;

import java.lang.annotation.*;

/**
 * Custom annotation for user operator log
 * Created by huiyu.chen on 2017/7/31.
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    String value() default "";
}
