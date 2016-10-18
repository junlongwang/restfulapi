package com.joybike.server.api.util;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5DigestUtils {
	private MD5DigestUtils() {}
	//private static Logger logger = LoggerFactory.getLogger(UCCaller.class);
	
	public static String md5(String input){
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");
			byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
			byte[] digest = instance.digest(bytes);
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();			
		} catch (NoSuchAlgorithmException e) {
			//logger.error("md5 errors",e);
		}
		return null;
	}
}
