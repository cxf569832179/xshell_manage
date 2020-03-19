package me.ffs.www.shiro.config;

import com.github.gserv.serv.commons.security.XSSSecurityFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.ffs.www.shiro.MyRealm;

@Configuration
public class ShiroConfig {

    // 注入自定义的realm，告诉shiro如何获取用户信息来做登录或权限控制
    @Bean
    public Realm realm() {
	MyRealm realm = new MyRealm();
	realm.setCacheManager(getEhCacheManager());// shiro缓存设置
	return realm;
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
	DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
	/**
	 * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
	 * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。 加入这项配置能解决这个bug
	 */
	creator.setUsePrefix(true);
	return creator;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
	DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
	// 访问控制
	chain.addPathDefinition("/404", "anon");// 404页面
	chain.addPathDefinition("/500", "anon");
	chain.addPathDefinition("/css/**", "anon");// 静态资源文件
	chain.addPathDefinition("/js/**", "anon");
	chain.addPathDefinition("/img/**", "anon");
	chain.addPathDefinition("/m/login/**", "anon");// 登录不能拦截
	chain.addPathDefinition("/api/**", "anon");

	// 其它路径均需要登录
	chain.addPathDefinition("/**", "authc");// 其他使用注解判断

	return chain;
    }

    // 配置org.apache.shiro.web.session.mgt.DefaultWebSessionManager(shiro session的管理)
    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager() {
	DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
	defaultWebSessionManager.setGlobalSessionTimeout(1000 * 60 * 30);// 会话过期时间，单位：毫秒(在无操作时开始计时)
	defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
	defaultWebSessionManager.setSessionIdCookieEnabled(true);
	return defaultWebSessionManager;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
	DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
	dwsm.setRealm(realm());// 自定义realm
	dwsm.setCacheManager(getEhCacheManager());// 启用shiro缓存
	dwsm.setSessionManager(getDefaultWebSessionManager());// session管理
	return dwsm;
    }

    /**
     * shiro注解:
     * 
     * @RequiresGuest 只有游客可以访问
     * @RequiresAuthentication 需要登录才能访问---->可以直接添加到类上
     * @RequiresUser 已登录的用户或“记住我”的用户能访问
     * @RequiresRoles 已登录的用户需具有指定的角色才能访问
     * @RequiresPermissions 已登录的用户需具有指定的权限才能访问
     */

    // 启用shiro缓存:shiro框架自带缓存
    /*
     * @Bean protected CacheManager cacheManager() { return new
     * MemoryConstrainedCacheManager(); }
     */

    // 启用缓存,使用自定义缓存
    @Bean
    public EhCacheManager getEhCacheManager() {
	EhCacheManager em = new EhCacheManager();
	em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
	return em;
    }

    public static void main(String[] args) {// 手动生成盐值和密码
	String random = new SecureRandomNumberGenerator().nextBytes().toHex(); //
	// 将原始密码加盐（上面生成的盐），并且用sha1算法加密1024次，将最后结果存入数据库中
	String result = new Sha1Hash("sms0318", random, 1024).toString();
	System.out.println("私钥:" + random);
	System.out.println("密码:" + result);
    }

}
