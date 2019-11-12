package com.baizhi.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  //注解可用范围：用于方法上
@Retention(RetentionPolicy.RUNTIME)  //使用时机：运行时使用
public @interface RedisCache {

}
