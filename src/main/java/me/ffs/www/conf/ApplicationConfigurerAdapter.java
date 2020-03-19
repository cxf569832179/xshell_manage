package me.ffs.www.conf;


import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@Configuration
public class ApplicationConfigurerAdapter{


    /*************************************404  500  页面****************************************/
    @Bean
    public WebServerFactoryCustomizer containerCustomizer(){
        return new MyCustomizer();
    }
    private static class MyCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>  {
        @Override
        public void customize(ConfigurableServletWebServerFactory container) {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND , "/404"));
            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/500"));
          //  container.addErrorPages(new ErrorPage(PATH,"/m/login/index"));
        }
    }

    @Bean
    public javax.validation.Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }
    
    @Bean
    public TaskScheduler scheduledExecutorService() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(8);
        scheduler.setThreadNamePrefix("scheduled-thread-");
        return scheduler;
    }
}