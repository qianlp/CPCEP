/**
 * 
 */
package htos.coresys.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * @author 温勋
 * @ClassName : MD5Util
 * @Version V1.1
 * @ModifiedBy 温勋
 * @Copyright 华胜龙腾
 * @date 2017年9月26日 下午3:20:38
 */
public class MD5Util {
	public static String EncoderByMd5(String str) {
		//确定计算方法
		MessageDigest md5=null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//加密字符
		BASE64Encoder base64en = new BASE64Encoder();
		// 加密后的字符串
		String newstr="";
		try {
			newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newstr;
	}
}
