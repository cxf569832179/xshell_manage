package me.ffs.www.service;

import java.util.HashMap;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 *  定时器
 * @author btzg
 * @time 2018年11月3日 下午2:24:07
 */
@Component
@Service
public class TimerService {
	private static final Logger logger = LoggerFactory.getLogger(TimerService.class);
	
	@Autowired 
	private WebApplicationContext webApplicationConnect;
	public WebApplicationContext getWebApplicationConnect() {
		return webApplicationConnect;
	}
	public void setWebApplicationConnect(WebApplicationContext webApplicationConnect) {
		this.webApplicationConnect = webApplicationConnect;
	}

	/**
	 * 定期清空ipMap
	 */
	@Scheduled(initialDelay = 0, fixedDelay = 1000*60*60)
    public void clearIpMap(){
		ServletContext app = webApplicationConnect.getServletContext();
		app.setAttribute("IP_MAP_SERVLET_CONTEXT", new HashMap<String, Integer>());
		logger.info(">>>>>>>>>>>>>>>>>>清空ipMap<<<<<<<<<<<<<<<<<<<<<<<<<");
		return;
    }
	
}
