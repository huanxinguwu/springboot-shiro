package com.example.shiro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 3.创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);//就是第二步返回的对象securityManager
        /**
         * 添加shiro内置过滤器，拦截相关网页
         * 常用过滤器有：
         * 1. anon:无需权限即可访问
         * 2.authc:必须认证才可以访问
         * 3.user：如果使用了rememberme功能就可以访问
         * 4.perms:该资源必须得到资源权限才可以访问
         * 5.role：该资源必须得到角色权限才能访问
         */
        Map<String,String> chainMap=new LinkedHashMap<>();
        //拦截新增页面(这里写接口路径)，导致新增页面无法访问，网页被拦截之后，默认跳转到login.jsp
        chainMap.put("/add","authc");
        chainMap.put("/update","authc");
//        下面的时执行授权逻辑的，不是认证的
        chainMap.put("/add","perms[add]");
        //如果想要某文件夹下的所有页面都被拦截，可以使用通配符 如：chainMap.put("/user/*","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chainMap);
        //设置如果拦截成功，不让他调到默认的login.jsp,而是让跳转到login.html页面
        shiroFilterFactoryBean.setLoginUrl("/tologin");   //注意在shiro中拦截的都是controller中的接口路径


        //当被授权拦截之后，需要设置一个呗拦截之后的跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");
        return shiroFilterFactoryBean;
    }

   /**
     * 2创建DefaultWebSecurityManager
    */
   @Bean(name = "securityManager")
   public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
       DefaultWebSecurityManager securityManager= new DefaultWebSecurityManager();
       //关联realm
       securityManager.setRealm(userRealm);
       return securityManager;
   }

    /**
     * 1.创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

}
