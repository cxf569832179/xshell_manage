package me.ffs.www.shiro.enums;

/**
 *       <p>
 *       :登录类型
 */
public enum LoginType {

    PASSWORD("psw"), // 密码登录
    NOPASSWD("no-psw"); // 免密登录

    private String code;// 状态值

    private LoginType(String code) {
	this.code = code;
    }

    public String getCode() {
	return code;
    }
}
