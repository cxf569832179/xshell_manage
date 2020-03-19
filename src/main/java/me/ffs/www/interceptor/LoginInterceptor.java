package me.ffs.www.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.gserv.serv.commons.util.IpUtil;
import me.ffs.www.util.BasePathUtil;
import me.ffs.www.util.PublicTool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor{
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
		System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
		String remoteIp = IpUtil.getRemoteIp(request);
		boolean result = false;
		logger.warn("[{}]未登录访问后台",remoteIp);
		try {
			String isLogin =
					request.getSession().getAttribute(PublicTool.MANAGER_USERINFO_SESSION)!=null?
					(String)request.getSession().getAttribute(PublicTool.MANAGER_USERINFO_SESSION):"";// 获取session中的值,看用户是否登录
			if (StringUtils.isNotBlank(isLogin)) {
				result = true;
			} else {
				response.sendRedirect(request.getContextPath() + "/m/login/index");// 重定向到重新登录页面
				result = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("登录拦截器出现异常,异常为:{}", e);
			result = false;
		}
		return result;
	}

}
