package me.ffs.www.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


public class BasePathUtil {
    
  public static String getVistBasePath(HttpServletRequest request){
    	
    	String path = request.getContextPath();
    	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//    	String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
    	
    	 return basePath;
    }
}
