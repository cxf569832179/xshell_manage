package me.ffs.www.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5_u {
	
	 /** 
		 * 利用MD5进行加密
		 * 
		 * @param str
		 *            待加密的字符串
		 * @return 加密后的字符串
		 * @throws NoSuchAlgorithmException
		 *             没有这种产生消息摘要的算法
		 * @throws UnsupportedEncodingException
		 */
public static String EncoderByMd5(String str){
   // 加密后的字符串
	String newstr="";
	try {
		// 确定计算方法
		MessageDigest md5=MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   return newstr;
}

/***
* md5--16位加密
* @param plainText
* @return
*/
public static String md5s(String plainText) {
	String md5str = "";
	try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plainText.getBytes("utf-8"));
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}
		md5str = buf.toString().substring(8, 24);
//		System.out.println("result: " + buf.toString());// 32位的加密
//		System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	return md5str;
}

/***
* md5--32位加密
* @param plainText
* @return
*/
public static String md5s_32(String plainText) {
	String md5str = "";
	try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plainText.getBytes("utf-8"));
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}
		md5str = buf.toString();
//		System.out.println("result: " + buf.toString());// 32位的加密
//		System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	return md5str;
}

//public static void main(String[] a) {
//	String aa = MD5_u.md5s_32("12");
//	String bb = MD5_u.EncoderByMd5("12");
//	System.out.println(aa);
//	System.out.println(bb);
//}

}

