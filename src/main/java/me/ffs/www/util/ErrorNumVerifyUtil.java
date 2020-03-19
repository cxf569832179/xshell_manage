package me.ffs.www.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *       <p>
 *       错误次数验证
 */
public class ErrorNumVerifyUtil {

    private static Map<String, Integer> errorNumMap = new HashMap<>();// 用户错误次数记录

    private static Map<String, Long> lockUserMap = new HashMap<>();// 用户锁定时间记录

    /**
     * @param username    : 用户名
     * @param pastTime    : 过期时间(规定时间内)
     * @param errorMaxNum : 限定最大错误次数
     * @return true : 锁定, false:没有锁定
     *         <P>
     *         用户是否锁定,(用来判断规定时间内密码的错误次数是否超过规定的次数)
     */
    public static boolean isLockUser(String username, Integer pastTime, Integer errorMaxNum) {
	if (lockUserMap.get(username) == null) {
	    return false;
	} else {
	    // 判断规定时间内用户密码的错误次数
	    if (new Date().getTime() - lockUserMap.get(username) <= pastTime) {// 时间之内
		// 判断次数
		if (errorNumMap.get(username) >= errorMaxNum) {
		    
		    return true;
		} else {
		    return false;
		}
	    } else {// 时间之外
		removeUserErrorData(username);
		return false;
	    }
	}

    }

    /**
     * @param username : 用户名
     *                 <p>
     *                 密码错误时记录
     */
    public static void pswtoErro(String username) {
	if (errorNumMap.get(username) == null) {// 没有错误过
	    lockUserMap.put(username, new Date().getTime());// 记录用户密码错误的开始时间
	    errorNumMap.put(username, 1);
	} else {// 错误过
	    errorNumMap.put(username, errorNumMap.get(username) + 1);
	}
    }

    /**
     * @param username : 用户名
     *                 <p>
     *                 删除关于用户错误的数据,用于登录成功,用户锁定过期
     */
    public static void removeUserErrorData(String username) {
	errorNumMap.remove(username);
	lockUserMap.remove(username);
    }

}
