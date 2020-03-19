package me.ffs.www.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

import me.ffs.www.shiro.MyRealm;
import me.ffs.www.shiro.enums.LoginType;

public class MyToken extends UsernamePasswordToken {


    private LoginType type;

    private static final String PASSWORD_PHONE = "~!Q@W#E$R^Y~";// 手机号码登录通用密码

    public MyToken() {
	super();
    }

    public MyToken(String username, String password, LoginType type, boolean rememberMe, String host) {
	super(username, password, rememberMe, host);
	this.type = type;
    }

    /** 免密登录 */
    public MyToken(String username) {
	super(username, PASSWORD_PHONE, false, null);
	this.type = LoginType.NOPASSWD;
    }

    /** 账号密码登录 */
    public MyToken(String username, String pwd) {
	super(username, pwd, false, null);
	this.type = LoginType.PASSWORD;
    }

    public LoginType getType() {
	return type;
    }

    public void setType(LoginType type) {
	this.type = type;
    }

}
