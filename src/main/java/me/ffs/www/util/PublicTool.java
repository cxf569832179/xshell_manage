package me.ffs.www.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PublicTool {
	/**
	 * 微信用户信息session
	 */
	public static final String WX_USERINFO_SESSION = "WX_USERINFO_SESSION"; 
	/**
	 * 微信用户信息上级session
	 */
	public static final String WX_USERINFO_SESSION_FROM = "WX_USERINFO_SESSION_FROM"; 
	/**
	 * 前台登录用户session
	 */
	public static final String FRONT_USERINFO_SESSION = "FRONT_USERINFO_SESSION";
	/**
	 * 后台登录用户session
	 */
	public static final String MANAGER_USERINFO_SESSION = "MANAGER_USERINFO_SESSION";
	/**
	 * 后台菜单session
	 */
	public static final String MENU_SESSION = "MENU_SESSION";

	/** 年月日时分秒(无下划线) yyyyMMddHHmmss */
	public static final String dtLong = "yyyyMMddHHmmss";
	private int randomId = 0;
	private static PublicTool instance = new PublicTool();
	

	/**
	 * 获取uuid
	 * 
	 * @return
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取uuid 不带“-”
	 * 
	 * @return
	 */
	public static String getUuidNo() {
		return UUID.randomUUID().toString().replaceAll("\\-", "");
	}

	/**
	 * 返回4位随机数
	 * 
	 * @return
	 */
	public static String getRandom() {
		Random random = new Random();
		int rand = random.nextInt(10000);
		String str = rand + "";
		int num = Integer.parseInt(str);
		String strnew = String.format("%04d", num);
		return strnew;
	}

	/**
	 * @return 获取固定六位自增序列号
	 */
	public synchronized String getNextID() {
		randomId = (randomId >= 99999) ? 1 : (randomId + 1);
		String s = "00000" + randomId;
		// System.out.println("randomId:::" + randomId);
		s = s.substring(s.length() - 6, s.length());
		return s;
	}

	public static PublicTool getInstance() {
		return instance;
	}

	/**
	 * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
	 * 
	 * @return 以yyyyMMddHHmmss为格式的当前系统时间
	 */
	public synchronized String getOrderNum() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtLong);
		return df.format(date) + getInstance().getNextID();
	}

	/**
	 * 正则表达式防止sql注入
	 * 
	 * @param str
	 * @return
	 */
	public static String TransactSQLInjection(String str) {
		return str.replaceAll(".*([';]+|(--)+).*", " ");

		// 我认为 应该是return str.replaceAll("([';])+|(--)+","");

	}

	/***
	 * 判断是否是以1开头的11为数字
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^(1+\\d{10})$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * 
	 * @return String
	 */
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	/**
	 * JAVA中获得一个有中文的字符串的字节长度
	 */
	public static int getWordCount(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		return length;

	}

	public static String NumToStar(String star) {
		String str;
		if (star.equals("1")) {
			str = "一";
		} else if (star.equals("2")) {
			str = "二";
		} else if (star.equals("3")) {
			str = "三";
		} else if (star.equals("4")) {
			str = "四";
		} else if (star.equals("5")) {
			str = "五";
		} else {
			str = "普通";
		}
		return str;
	}

	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
	/**
	 * 获取指定时间的Date
	 */
	public static Date getDate(String date) {
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date myDate2=null;
		try {
			myDate2 = dateFormat2.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myDate2;
	}
	/**
	 * （时间对比）精确到时分秒 
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getIntervalDays(Date fDate, Date oDate) {

	       if (null == fDate || null == oDate) {

	           return -1;

	       }

	       long intervalMilli = oDate.getTime() - fDate.getTime();

	       return (int) (intervalMilli / (24 * 60 * 60 * 1000));

	    }
	/**
	 * （时间对比）只判断日期
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {

	       Calendar aCalendar = Calendar.getInstance();

	       aCalendar.setTime(fDate);

	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

	       aCalendar.setTime(oDate);

	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

	       return day2 - day1;

	    }
	/**
	 * 包括跨年
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int alldaysOfTwo(Date fDate, Date oDate) {
		
		long betweenDate = (oDate.getTime() - fDate.getTime())/(60*60*24*1000);
		
		return Integer.parseInt(betweenDate+"");
		
	}
	
	public static Date StringZhDate(String dateStr){
        Date date = new Date();  
        //注意format的格式要与日期String的格式相匹配  
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        try {  
            date = sdf.parse(dateStr);  
            return date;
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;
	}
	public static Date StringZhDate(String dateStr,String format){
		Date date = new Date();  
		//注意format的格式要与日期String的格式相匹配  
		DateFormat sdf = new SimpleDateFormat(format);  
		try {  
			date = sdf.parse(dateStr);  
			return date;
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return null;
	}
	/**
	 * 获取路径带参数
	 * @param request
	 * @return
	 */
	public static String getUrl(HttpServletRequest request){
        StringBuffer requestUrl = request.getRequestURL();
        String queryString = request.getQueryString();
        String url = requestUrl +"?"+queryString;
        return url;
    }
	
	/**
	 * 返回指定数值内的随机数
	 * @param num
	 * @return
	 */
	public static Integer getRandom(Integer num){
		Random random = new Random();
		return random.nextInt(num);
	}
	/**
	 * date转换String
	 * @param date
	 * @param format
	 * @return
	 */
	public static String DateZhString(Date date,String format){
		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
		return dateFormater.format(date);
	}
	
	/**
	 * 从session取user信息
	 * @param request
	 * @return
	 */
	/*public static UserInfo getUserBySession(HttpServletRequest request){
		UserInfo user =request.getSession().getAttribute(WX_USERINFO_SESSION)!=null?
				(UserInfo)request.getSession().getAttribute(WX_USERINFO_SESSION):null;
		return user;
	}
	public static UserInfo getUserFromBySession(HttpServletRequest request){
		UserInfo user =request.getSession().getAttribute(WX_USERINFO_SESSION_FROM)!=null?
				(UserInfo)request.getSession().getAttribute(WX_USERINFO_SESSION_FROM):null;
				return user;
	}*/
	
	/**
	 * 给时间加上一段时间
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static Date dayAddDays(Date fDate, String type,int num) {
		
		Calendar aCalendar = Calendar.getInstance();
		
		aCalendar.setTime(fDate);
		if("y".equals(type)){
			aCalendar.add(Calendar.YEAR, num);
		}else if("m".equals(type)){
			aCalendar.add(Calendar.MONTH, num);
		}else if("d".equals(type)){
			aCalendar.add(Calendar.DATE, num);
		}else if("h".equals(type)){
			aCalendar.add(Calendar.HOUR, num);
		}else if("min".equals(type)){
			aCalendar.add(Calendar.MINUTE, num);
		}else if("s".equals(type)){
			aCalendar.add(Calendar.SECOND, num);
		}
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date dte=aCalendar.getTime();
		String f=sdf.format(dte);
		
		return getDate(f);
		
	}
}
