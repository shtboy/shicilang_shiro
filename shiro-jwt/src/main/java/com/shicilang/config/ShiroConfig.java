package com.shicilang.config;

import com.shicilang.config.filter.UrlPermissionFilter;
import com.shicilang.config.realm.MyRealm;
import com.shicilang.config.resolver.RolePermissionResolver;
import com.shicilang.config.resolver.UrlPermissionResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Slf4j
public class ShiroConfig {
    /**
     * 配置Shiro的安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(Realm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置一个Realm，这个Realm是最终用于完成我们的认证号和授权操作的具体对象
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    /**
     * 配置一个自定义的Realm的bean，最终将使用这个bean返回的对象来完成我们的认证和授权
     */
    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        myRealm.setPermissionResolver(urlPermissionResolver());
        myRealm.setRolePermissionResolver(rolePermissionResolver());
        return myRealm;
    }

    @Bean
    public UrlPermissionResolver urlPermissionResolver() {
        return new UrlPermissionResolver();
    }

    @Bean
    public RolePermissionResolver rolePermissionResolver() {
        return new RolePermissionResolver();
    }


    /**
     * 配置一个Shiro的过滤器bean，这个bean将配置Shiro相关的一个规则的拦截
     * 如什么样的请求可以访问，什么样的请求不可以访问等等
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        /**
         * 覆盖默认的user拦截器(默认拦截器解决不了ajax请求 session超时的问题,若有更好的办法请及时反馈作者)
         */
        HashMap<String, Filter> myFilters = new HashMap<>();
        UrlPermissionFilter uriFilter = new UrlPermissionFilter();
        myFilters.put("urlPermissionFilter", uriFilter);//自定义拦截器
        shiroFilter.setFilters(myFilters);//添加自定义拦截器
        /**
         * 配置shiro拦截器链
         * anon  不需要认证
         * authc 需要认证
         * user  验证通过或RememberMe登录的都可以
         * 当应用开启了rememberMe时,用户下次访问时可以是一个user,但不会是authc,因为authc是需要重新认证的
         * 顺序从上到下,优先级依次降低
         */
        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/login", "anon");
        hashMap.put("/", "anon");
        hashMap.put("/**", "urlPermissionFilter");
        shiroFilter.setFilterChainDefinitionMap(hashMap);
        return shiroFilter;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions)
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}