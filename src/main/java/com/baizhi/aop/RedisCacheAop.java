package com.baizhi.aop;
import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.RedisCache;
import org.apache.poi.hssf.record.DVALRecord;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.Set;
@Aspect  //通知
@Configuration //表名这个类是配置类
public class RedisCacheAop {
    @Autowired
    private Jedis jedis;
    //切入点表达式  切所有service的查询方法
    @Around("execution(* com.baizhi.service.*.selectAll(..))")
    //ProceedingJoinPoint：获取当前方法和参数
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取类的名称     目标的类的名称
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        //获取方法的名称
        String methodName = proceedingJoinPoint.getSignature().getName();
        //获取参数实参
        Object[] args = proceedingJoinPoint.getArgs();
        //获得目标
        MethodSignature methodSignature  = (MethodSignature) proceedingJoinPoint.getSignature();
        //获得方法
        Method method = methodSignature.getMethod();
        //判断当前的方法有没有注解
        boolean b = method.isAnnotationPresent(RedisCache.class);
        //声明一个变量
        Object result = null;
        if (b) {
            //存在注解  StringBuilder是可变长字符序列  用来拼接
            StringBuilder sbr = new StringBuilder();
            //追加  className是外部名    把methodName 放在sbr里
            sbr.append(methodName);
            // 遍历值（sbr）
            for (Object arg : args) {
                sbr.append(arg);
            }
            //将值key格式化Java格式
            String key = sbr.toString();
            //判断redis中是否含有这个key
            if (jedis.hexists(className,key)){
                //缓存中存在该key
                String json = jedis.hget(className, key);
                result = JSONObject.parse(json);
            }else {
                //缓存中不含有该key 放行方法
                result=proceedingJoinPoint.proceed();
                //将查询的结果放入到redis里
                jedis.hset(className,key,JSONObject.toJSONString(result));
            }
                //关闭资源
            jedis.close();
            //返回一个结果
            return result;
        } else {
            //不存在注解
            result = proceedingJoinPoint.proceed();
        }
            jedis.close();
        return result;
    }


    //:AfterReturning目标方法一旦有异常，则不会执行切面中的代码（删除缓存）
    @AfterReturning("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.selectAll(..))")
    public void  after(JoinPoint joinPoint){
        //获取当前目标对象的全名
        String className = joinPoint.getTarget().getClass().getName();
        //获取这个值
        Set<String> keys = jedis.keys("*");
        //遍历这个值判断 如果有就清空
        for (String key : keys) {
            if(key.startsWith(className)){
                jedis.del(className);
            }
        }
        //关闭jedis的连接
        jedis.close();
    }
}
