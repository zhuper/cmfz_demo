package com.baizhi;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import redis.clients.jedis.Jedis;
import tk.mybatis.spring.annotation.MapperScan;

import javax.persistence.Basic;

@SpringBootApplication
@MapperScan("com.baizhi.dao")
public class CmfzDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmfzDemoApplication.class, args);

    }

   @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 1. 需要定义一个converter转换消息的对象
        FastJsonHttpMessageConverter fasHttpMessageConverter = new FastJsonHttpMessageConverter();
        // 2. 添加fastjson的配置信息，比如:是否需要格式化返回的json的数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 3. 在converter中添加配置信息
        fasHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fasHttpMessageConverter;
        return new HttpMessageConverters(converter);
    }
        //将jedis 交由Spring工厂管理

    @Bean("jedis")
    public Jedis getJedis(){
        //redis服务的机器ip  端口号
        return new Jedis("192.168.189.158",6379);
    }

/*

    //将shiro交给功能管理
        public ShiroFilterFactoryBean get
*/


}
