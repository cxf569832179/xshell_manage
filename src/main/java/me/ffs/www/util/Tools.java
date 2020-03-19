/**   

* @Title: Tools.java

* @Package com.wx_sxltkf.www.jifenservice

* @Description: TODO

* @author 陈云   

* @date 2014-4-30 上午9:10:40

* QQ 604311358   

*/


package me.ffs.www.util;

import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.github.gserv.serv.commons.util.IdWorker;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;



/**

 * @ClassName: Tools

 * @Description: TODO

 * @author 陈云 

 * @date 2014-4-30 上午9:10:40

 *  QQ 604311358 


 */

public class Tools {

	private static final Logger logger = LoggerFactory.getLogger(Tools.class);
    /** 年月日时分秒(无下划线) yyyyMMddHHmmss */
    public static final String dtLong = "yyyyMMddHHmmss";
    private int randomId = 0;
    private static Tools instance = new Tools();

    private static String allChar = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String allNum = "0123456789";
    
    public static final String key="!@#$%qwertZXCVB";
    /**
     * @return 获取固定六位自增序列号
     */
    public synchronized String getNextID() {
	randomId = (randomId >= 99999) ? 1 : (randomId + 1);
	String s = "00000" + randomId;
	//System.out.println("randomId:::" + randomId);
	s = s.substring(s.length() - 6, s.length());
	return s;
    }

    public static Tools getInstance() {
	return instance;
    }

    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * 
     * @return 以yyyyMMddHHmmss为格式的当前系统时间
     */
    public static String getOrderNum() {
	Date date = new Date();
	DateFormat df = new SimpleDateFormat(dtLong);
	return df.format(date) + getInstance().getNextID();
    }

    
    
    /**
     * @return 获取固定六位自增序列号
     */
    public synchronized String getNextID_2() {
	randomId = (randomId >= 999) ? 1 : (randomId + 1);
	String s = "000" + randomId;
	//System.out.println("randomId:::" + randomId);
	s = s.substring(s.length() - 3, s.length());
	return s;
    }

    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * 
     * @return 以yyyyMMddHHmmss为格式的当前系统时间
     */
    public static String getCodeQue() {
	return "TYDH"+ getInstance().getNextID_2();
    }

    
    /**
     * 正则表达式防止sql注入
     * @param str
     * @return
     */
    public static String TransactSQLInjection(String str)
    {
          return str.replaceAll(".*([';]+|(--)+).*", " ");

       // 我认为 应该是return str.replaceAll("([';])+|(--)+","");

    }
	/***
	 * 判断是否是以1开头的11为数字
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){
		Pattern p = Pattern.compile("^(1+\\d{10})$");  
		Matcher m = p.matcher(mobiles);  
		return m.matches();  
	}
	
    /**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return String
	 */ 
	public static String getCurrTime(String sdf) {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat(sdf);
		String s = outFormat.format(now);
		return s;
	}
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
	   public  static int getWordCount(String s)
	    {
	         int  length  =   0 ;
	         for ( int  i  =   0 ; i  <  s.length(); i ++ )
	        {
	             int  ascii  =  Character.codePointAt(s, i);
	             if (ascii  >=   0   &&  ascii  <= 255 )
	                length ++ ;
	             else 
	                length  +=   2 ;
	                
	        }
	         return  length;
	        
	    }

	    public static boolean isMobileNumber(String phone) {
	    	try {
	    	    phone = phone.trim();
	    	    if (phone == null || phone.length() < 11) {
	    		return false;
	    	    }
	    	    phone = phone.substring(phone.length() - 11, phone.length());
	    	    return phone != null && phone.matches("^1{1}\\d{10}$");
	    	} catch (Exception e) {
	    	    return false;
	    	}
	        }

	    //替换非汉字
//	    public static String repalace(String txt){
//	    	String zhengz="<p[^>]*>";
//	    	String zhengz="[^\u4e00-\u9fa5|,]+";
//	    	String zhengz_="</p>";
//	    	txt=txt.replaceAll(zhengz, "");
//	    	txt=txt.replaceAll(zhengz_, " ");
//	    	txt=txt.replaceAll("<br/>", "");
//	    	txt=Jsoup.parse(txt).text();
//	    	return txt;
//	    }
	    
	    
	    public static StringBuffer getfourAddress() {
	    	allChar=allChar.toLowerCase();
	    	StringBuffer sb = new StringBuffer();
	    	java.util.Random random = new java.util.Random();
	    	for (int i = 0; i < 4; i++) {
	    		sb.append(allChar.charAt(random.nextInt(allChar.length())));
	    	}
	    	return sb;
	    }
	    
	    public static StringBuffer getfourNum() {
			StringBuffer sb = new StringBuffer();
			java.util.Random random = new java.util.Random();
			for (int i = 0; i < 4; i++) {
				sb.append(allNum.charAt(random.nextInt(allNum.length())));
			}
			return sb;
		}
	    
	    public static StringBuffer getSixAddress() {
			allChar=allChar.toLowerCase();
			StringBuffer sb = new StringBuffer();
			java.util.Random random = new java.util.Random();
			for (int i = 0; i < 6; i++) {
				sb.append(allChar.charAt(random.nextInt(allChar.length())));
			}
			return sb;
		}
		   /**  
	     * 计算两个日期之间相差的天数  
	     * @param smdate 较小的时间 
	     * @param bdate  较大的时间 
	     * @return 相差天数 
	     * @throws ParseException  
	     */    
	    public static int daysBetween(Date smdate,Date bdate) {  
	    	try{
		        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		        smdate=sdf.parse(sdf.format(smdate));  
		        bdate=sdf.parse(sdf.format(bdate));  
		        Calendar cal = Calendar.getInstance();    
		        cal.setTime(smdate);    
		        long time1 = cal.getTimeInMillis();                 
		        cal.setTime(bdate);    
		        long time2 = cal.getTimeInMillis();         
		        long between_days=(time2-time1)/(1000*3600*24);  
		            
		       return Integer.parseInt(String.valueOf(between_days));    
	    	}catch (Exception e) {
				return 0;
			}
	    }    
	    /**
	     * 获取指定位数的随机数
	     * @param length
	     * @return
	     */
	    public static String randomNum(int length){
	        StringBuilder sb = new StringBuilder();
	        for(int i = 0; i < length; i++){
	            double random = Math.random();
	            int randomNum = (int) Math.floor(((random*10) % 10));
	            sb.append(randomNum);
	        }
	        return sb.toString();
	    }
	    
	    //发展人编号
	    public static String getDeveNum(){
	    	
	    	return "de_"+getfourNum();
	    }
	    //分销人编号
	    public static String getFxNum(){
	    	
	    	return "fx_"+getfourNum();
	    }
	    
	    /** 
		  * 从request中获得参数Map，并返回可读的Map 
		  *  
		  * @param request 
		  * @return 
		  */  
		 public static Map getParameterMap(HttpServletRequest request) {  
		    // 参数Map  
		    Map properties = request.getParameterMap();
		    // 返回值Map  
		    Map returnMap = new HashMap();  
		    Iterator entries = properties.entrySet().iterator();  
		    Map.Entry entry;  
		    String name = "";  
		    String value = "";  
		    while (entries.hasNext()) {  
		        entry = (Map.Entry) entries.next();  
		        name = (String) entry.getKey();  
		        Object valueObj = entry.getValue();  
		        if(null == valueObj){  
		            value = "";  
		        }else if(valueObj instanceof String[]){  
		            String[] values = (String[])valueObj;  
		            for(int i=0;i<values.length;i++){  
		                value = values[i] + ",";  
		            }  
		            value = value.substring(0, value.length()-1);  
		        }else{  
		            value = valueObj.toString();  
		        }  
		        try{
		        	value=URLDecoder.decode(value,"UTF-8");
				}catch (Exception e) {
				}
		        returnMap.put(name, value);  
		    } 
		    return returnMap;  
		}  
		 public static Map getParameterMapNODecode(HttpServletRequest request) {  
			 // 参数Map  
			 Map properties = request.getParameterMap();
			 // 返回值Map  
			 Map returnMap = new HashMap();  
			 Iterator entries = properties.entrySet().iterator();  
			 Map.Entry entry;  
			 String name = "";  
			 String value = "";  
			 while (entries.hasNext()) {  
				 entry = (Map.Entry) entries.next();  
				 name = (String) entry.getKey();  
				 Object valueObj = entry.getValue();  
				 if(null == valueObj){  
					 value = "";  
				 }else if(valueObj instanceof String[]){  
					 String[] values = (String[])valueObj;  
					 for(int i=0;i<values.length;i++){  
						 value = values[i] + ",";  
					 }  
					 value = value.substring(0, value.length()-1);  
				 }else{  
					 value = valueObj.toString();  
				 }  
				 returnMap.put(name, value);  
			 } 
			 return returnMap;  
		 }  
		 
	 /**
	     * 功能：Java读取txt文件的内容
	     * 步骤：1：先获得文件句柄
	     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	     * 备注：需要考虑的是异常情况
	     * @param filePath
	     */
	    public String readFile(String filename){
	        try {
	        	String filePath=this.getClass().getClassLoader().getResource(filename).getPath();
	            String encoding="utf-8";
	            File file=new File(filePath);
	            if(file.isFile() && file.exists()){ //判断文件是否存在
	            	String json=FileUtils.readFileToString(file,encoding);
	            	return json;
	            	
			        }else{
			            System.out.println("找不到指定的文件");
			        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
	        }
	        return "";
	    }
	    
	    
	    public String readFileFromClassPath(String filename){
	        try {
	        	Resource classPathResource = new ClassPathResource(filename);
	        	InputStream is;
	        	is = classPathResource.getInputStream();
				List<String> readLines = IOUtils.readLines(is, "utf-8");
				StringBuilder sb = new StringBuilder();
				for (String string : readLines) {
					sb.append(string);
				}
				return sb.toString();
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	        }
	        return "";
	    }
	    
	    public static int getOrderStats(int stats){
	    	switch (stats) {
			case 2:return 3;				
			case 3:return 4;				
			case 16:return 4;				
			case 17:return 4;				
	    	}
	    	return stats;
	    }
	   
		
		
		/**
	     * 使用Gson拍平json字符串，即当有多层json嵌套时，可以把多层的json拍平为一层
	     * @param map
	     * @param json
	     * @param parentKey
	     */
	    public static void Json2SimpleMap(Map map, String json, String parentKey){
	        JsonElement jsonElement = new JsonParser().parse(json);
	        if (jsonElement.isJsonObject()) {
	            JsonObject jsonObject = jsonElement.getAsJsonObject();
	            parseJson2Map(map,jsonObject,parentKey);
	            //传入的还是一个json数组
	        }else if (jsonElement.isJsonArray()){
	            JsonArray jsonArray = jsonElement.getAsJsonArray();
	            Iterator<JsonElement> iterator = jsonArray.iterator();
	            while (iterator.hasNext()){
	                parseJson2Map(map,iterator.next().getAsJsonObject(),parentKey);
	            }
	        }else if (jsonElement.isJsonPrimitive()){
	            System.out.println("please check the json format!");
	        }else if (jsonElement.isJsonNull()){
	        }
	    }
	    

		public static void parseJson2Map(Map map,JsonObject jsonObject,String parentKey){
	        for (Map.Entry<String, JsonElement> object : jsonObject.entrySet()) {
	            String key = object.getKey();
	            JsonElement value = object.getValue();
	            String fullkey = (null == parentKey || parentKey.trim().equals("")) ? key : parentKey.trim() + "." + key;
	            //判断对象的类型，如果是空类型则安装空类型处理
	            if (value.isJsonNull()){
	            	put(map, fullkey, null);
	                continue;
	            //如果是JsonObject对象则递归处理
	            }else if (value.isJsonObject()){
	                parseJson2Map(map,value.getAsJsonObject(),fullkey);
	            //如果是JsonArray数组则迭代，然后进行递归
	            }else if (value.isJsonArray()){
	                JsonArray jsonArray = value.getAsJsonArray();
	                Iterator<JsonElement> iterator = jsonArray.iterator();
	                while (iterator.hasNext()) {
	                    JsonElement jsonElement1 = iterator.next();
	                    parseJson2Map(map, jsonElement1.getAsJsonObject(), fullkey);
	                }
	                continue;
	             // 如果是JsonPrimitive对象则获取当中的值,则还需要再次进行判断一下
	            }else if (value.isJsonPrimitive()){
	                try {
	                    JsonElement element = new JsonParser().parse(value.getAsString());
	                    if (element.isJsonNull()){
	                    	put(map, fullkey, value.getAsString());
	                    }else if (element.isJsonObject()) {
	                        parseJson2Map(map, element.getAsJsonObject(), fullkey);
	                    } else if (element.isJsonPrimitive()) {
	                        JsonPrimitive jsonPrimitive = element.getAsJsonPrimitive();

	                        if (jsonPrimitive.isNumber()) {
	                        	put(map, fullkey, jsonPrimitive.getAsNumber());
	                        } else {
	                        	put(map, fullkey, jsonPrimitive.getAsString());
	                        }
	                    } else if (element.isJsonArray()) {
	                        JsonArray jsonArray = element.getAsJsonArray();
	                        Iterator<JsonElement> iterator = jsonArray.iterator();
	                        while (iterator.hasNext()) {
	                            parseJson2Map(map, iterator.next().getAsJsonObject(), fullkey);
	                        }
	                    }
	                }catch (Exception e){
	                    put(map,fullkey,value.getAsString());
	                }
	            }
	        }
	    }
		
		public static final String JSON_ARRAY_SEPERATOR = "_$_";
		
		public static void put(Map map,String key,Object value){
			//put之前检测该key是否已经存在，存在则通过修改命名去重
			int i =1;
			while(map.containsKey(key)){
				if(key.indexOf(JSON_ARRAY_SEPERATOR) != -1){
					key = key.substring(0,key.indexOf(JSON_ARRAY_SEPERATOR));
				}
				key += JSON_ARRAY_SEPERATOR + i;
				i++;
			}
			map.put(key, value);
		}
}
