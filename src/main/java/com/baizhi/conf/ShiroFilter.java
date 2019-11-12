package com.baizhi.conf;

import com.baizhi.realm.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

//声明为配置
@Configuration
public class ShiroFilter {

    //把配置类给工厂管理
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);


        Map<String, String> map = new HashMap<>();
//        AnonymousFilter           匿名拦截器   anon
//        FormAuthenticationFilter  认证拦截器   authc
        //定义map过滤链
        map.put("/admin/login","anon");
        map.put("/code/getCode","anon");
        map.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/admin/login.jsp");
        return shiroFilterFactoryBean;
    }

    //创建安全管理器

    @Bean
    public SecurityManager getSecurityManager(ShiroRealm shiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将自定义realm设置安全管理器
        securityManager.setRealm(shiroRealm);
        //securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    /*@Bean
    public CacheManager getCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        return ehCacheManager;
    }*/
    //自定义realm
    @Bean
    public ShiroRealm getShiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }


}
