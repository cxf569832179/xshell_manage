package me.ffs.www.conf;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import me.ffs.www.model.ManageUser;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.gserv.serv.commons.util.JsonMapper;


@Aspect
@Component
public class WebLogConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebLogConfig.class);

    @Pointcut("execution(public * me.ffs.www.controller..*.*(..))")
    public void webLog() {
    }

    @Before(value = "webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
	try {
	    // 获取request
	    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
		    .getRequestAttributes();
	    HttpServletRequest request = attributes.getRequest();
	    // 记录日志:
	    logger.info("----------------------------------REQUEST----------------------------------");

	    Object object = SecurityUtils.getSubject().getPrincipal();
	    if (object != null) {
		ManageUser admin = (ManageUser) object;
		logger.info("ADMIN : {}", JsonMapper.toJsonString(admin.getUserName()));
	    }

	    logger.info("URL : {}", request.getRequestURL().toString());
	    logger.info("HTTP_METHOD : {}", request.getMethod());
	    logger.info("IP : {}", request.getRemoteAddr());
	    logger.info("CLASS_METHOD : {}",
		    joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
	    logger.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));
	    logger.info("PARAMETER : {}", JsonMapper.toJsonString(request.getParameterMap()));
	} catch (Exception e) {
	    logger.error(">>>WebLogConfig:ERROR:doBefore");
	}

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
	try {
	    logger.info("---------------------------------RESPONSE----------------------------------");
	    if (ret instanceof String) {
		logger.info("DATA : {}", ret);
	    } else {
		logger.info("DATA : {}", JsonMapper.toJsonString(ret));
	    }
	} catch (Exception e) {
	    logger.error(">>>WebLogConfig:ERROR:doAfterReturning");
	}
    }

}
