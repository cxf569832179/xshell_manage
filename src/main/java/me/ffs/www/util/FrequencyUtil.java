package me.ffs.www.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FrequencyUtil {
	private static Log log = LogFactory.getLog(FrequencyUtil.class);
    private static  Map<String,Object> tryCountsMap=new HashMap<String, Object>();
	
	  /**
     * 检测操作频率
     * @return
     */
	public static boolean checkFrequency(String bbnum) {
		// -记录操作时间--
		long tryTime = tryCountsMap.get("tryTime_" + bbnum) != null ? (Long) tryCountsMap.get("tryTime_" + bbnum) : new Date().getTime();
		if ((new Date().getTime() - tryTime) / (1000 * 60) > 30)
			tryCountsMap.put("tryCounts_" + bbnum, 1);

		int tryCounts = tryCountsMap.get("tryCounts_" + bbnum) != null ? (Integer) tryCountsMap.get("tryCounts_" + bbnum) : 0;

		tryCountsMap.put("tryCounts_" + bbnum, tryCounts + 1);
		tryCountsMap.put("tryTime_" + bbnum, new Date().getTime());

		if (tryCounts > 5) {

			log.error("用户:" + bbnum + ",累计尝试验证次数:" + tryCounts);

			return true;

		}

		return false;

		// -记录操作时间--完毕---
	}
	
	/**
	 * 检测操作频率
	 * @return
	 */
	public static boolean checkFrequency(String bbnum,int count) {
		// -记录操作时间--
		long tryTime = tryCountsMap.get("tryTime_" + bbnum) != null ? (Long) tryCountsMap.get("tryTime_" + bbnum) : new Date().getTime();
		if ((new Date().getTime() - tryTime) / (1000 * 60) > 30)
			tryCountsMap.put("tryCounts_" + bbnum, 1);
		
		int tryCounts = tryCountsMap.get("tryCounts_" + bbnum) != null ? (Integer) tryCountsMap.get("tryCounts_" + bbnum) : 0;
		
		tryCountsMap.put("tryCounts_" + bbnum, tryCounts + 1);
		tryCountsMap.put("tryTime_" + bbnum, new Date().getTime());
		
		if (tryCounts > count) {
			
			log.error("用户:" + bbnum + ",累计尝试验证次数:" + tryCounts);
			
			return true;
			
		}
		
		return false;
		
		// -记录操作时间--完毕---
	}
	/**
	 * 检测操作频率 time 单位分钟
	 * @return
	 */
	public static boolean checkFrequencyByTime(String bbnum,int count,int time) {
		// -记录操作时间--
		long tryTime = tryCountsMap.get("tryTime_" + bbnum) != null ? (Long) tryCountsMap.get("tryTime_" + bbnum) : new Date().getTime();
		if ((new Date().getTime() - tryTime) / (1000 * 60) > time)
			tryCountsMap.put("tryCounts_" + bbnum, 1);
		
		int tryCounts = tryCountsMap.get("tryCounts_" + bbnum) != null ? (Integer) tryCountsMap.get("tryCounts_" + bbnum) : 0;
		
		tryCountsMap.put("tryCounts_" + bbnum, tryCounts + 1);
		tryCountsMap.put("tryTime_" + bbnum, new Date().getTime());
		
		if (tryCounts > count) {
			
			log.error("用户:" + bbnum + ",累计尝试验证次数:" + tryCounts);
			
			return true;
			
		}
		
		return false;
		
		// -记录操作时间--完毕---
	}
	
	
	public static void  removeFrequency(String checkflag){
		 tryCountsMap.remove("tryCounts_"+checkflag);//绑定成功移除绑定key
    	 tryCountsMap.remove("tryTime_"+checkflag);//绑定成功移除绑定异常时间
	}

}
