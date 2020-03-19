package me.ffs.www.util;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import serv.utils.JsonUtils;

public class Send{
    private static Log logger = LogFactory.getLog(Send.class);

  public boolean send(String url,String parmars,String tmplcode,String privatekey,String mobile)  {
	  	logger.debug("send sms................................");
	    HttpClient http = new HttpClient();
	    http.getParams().setParameter("http.protocol.content-charset", "UTF-8");
	    PostMethod post = new PostMethod(url);
	    try {
	    	post.addParameter("mobile", mobile);
	    //	String parmars = StringUtils.join(array, "^");
	    	post.addParameter("parmars", parmars);
	    	post.addParameter("tmplcode", tmplcode);
	    	logger.info("短信下发:"+mobile+"---"+parmars);
	    	// sign
	    	String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	    	post.addParameter("timestamp", timestamp);
	    	post.addParameter("sign", MD5_u.md5s_32(mobile + parmars + privatekey + tmplcode + timestamp));
	    	// fire
	    	http.executeMethod(post);
	    	// 
	    	if (post.getStatusCode() != 200) {
	    		
	    		logger.error("http request faild. state = " + post.getStatusCode() + ", body [" + post.getResponseBodyAsString() + "]");
	    		
	    		return false;
	    	}
	    	//
	    	if (post.getResponseBodyAsString().indexOf("\"state_code\":\"true\"") == -1) {
	    		logger.error("send sms faild. body [" + post.getResponseBodyAsString() + "]");
	    		return false;
	    	}
	    	System.out.println("成功了。。。。。");
	    	System.out.println("短信下发:"+mobile+"---"+parmars);;
	    	return true;
	    	
	    	
	    } catch(Exception e){
	    	
	    	logger.error("send sms faild",e);	
	    	
	    	 return false;
	    	 
	    }finally {
	    	((SimpleHttpConnectionManager) http.getHttpConnectionManager()).shutdown();
	    }
  }
}

