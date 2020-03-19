package me.ffs.www.conf;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class WebConfig {

    @Value("${manage.pageSize}")
    private int managePageSize;
    @Value("${static.back}")
    private String staticBack;


    @Bean
    public int getManagePageSize() {
	return managePageSize;
    }
    @Bean
    public String getStaticBack() {
	return staticBack;
    }

  

}
