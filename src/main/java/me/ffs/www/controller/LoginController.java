package me.ffs.www.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.ffs.www.dao.mapper.ManageUserMapper;
import me.ffs.www.model.ManageUser;
import me.ffs.www.shiro.token.MyToken;
import me.ffs.www.util.ErrorNumVerifyUtil;
import me.ffs.www.util.PublicTool;
import me.ffs.www.util.ValidateCodeBack;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import serv.utils.JsonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("m/login")
public class LoginController {
	private static final Logger logger =LoggerFactory.getLogger(LoginController.class);

	@Resource
	private ManageUserMapper manageUserMapper;

	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping("index")
	public String index(){
		return "login";
	}
	@ResponseBody
	@PostMapping("login")
	public Map<String,Object> login(String username, String password, String imageCode, HttpSession session) {
		Map<String,Object> resMap=new HashMap<String,Object>();
		resMap.put("status","error");

		String imgCode=session.getAttribute("backend_key")!=null?
				session.getAttribute("backend_key").toString():"";
		// 判断验证码:
		if (imgCode == null || !imgCode.equalsIgnoreCase(imageCode)) {
			resMap.put("msg","验证码错误");
			return resMap;
		}
		// 判断用户密码错误次数是否过多
		if (ErrorNumVerifyUtil.isLockUser(username, 1000 * 60 * 30, 5)) {
			logger.info("密码错误次数过多,当前管理员为:{}", username);
			resMap.put("msg","密码错误次数过多");
			return resMap;
		}
		MyToken token = new MyToken(username, password);
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 登录
			currentUser.login(token);
			// 从session取出用户信息
			ManageUser admin=(ManageUser)currentUser.getPrincipal();
			logger.info("当前的管理员为:{}", JsonUtils.toJson(admin));
				if (admin.getState() == 0) {// 账户已被冻结
					currentUser.logout();
					resMap.put("msg","账户已被冻结");
					return resMap;
				}
				ErrorNumVerifyUtil.removeUserErrorData(username);
				session.setAttribute(PublicTool.MANAGER_USERINFO_SESSION, username);
				// 返回登录用户的信息给前台，含用户的所有角色和权限
				logger.info("管理员登录成功.....当前的管理员为:{}", username);
				resMap.put("status","success");
				return resMap;
		} catch (UnknownAccountException uae) {
			resMap.put("msg","用户不存在");
			return resMap;

		} catch (IncorrectCredentialsException ice) {
			ErrorNumVerifyUtil.pswtoErro(username);
			resMap.put("msg","密码错误");
			return resMap;

		} catch (AuthenticationException ae) {
			resMap.put("msg","登录异常");
			return resMap;
		}

	}
	/***
	 * 生成随机验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "random")
	public void randomPicCode(HttpServletRequest request,HttpServletResponse response) {
		logger.info("生成验证码!");
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		//禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		HttpSession session = request.getSession();

		ValidateCodeBack vCode = new ValidateCodeBack(120,40);
		logger.info("图形验证码："+vCode.getCode().toLowerCase());
		session.setAttribute("code", vCode.getCode().toLowerCase());
		session.setAttribute("backend_key", vCode.getResult());
		try {
			vCode.write(response.getOutputStream());
		} catch (IOException e) {
			logger.error("生成验证码异常!",e);
			e.printStackTrace();
		}
	}
	/**
	 * 后台登出
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpSession session){
		session.invalidate();
//		return PublicService.BACK_INDEX;
		return "login";
	}

}
