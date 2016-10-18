package com.joybike.server.api.util;

import java.util.Date;

public final class UnixTimeUtils {
	private UnixTimeUtils() {}
	public static int getUnixTimestamp(Date date){
		return (int)(date.getTime() / 1000L);
	}
	
	public static Date fromUnixTimestamp(long unixTimestamp){
		return new Date(unixTimestamp * 1000L);
	}
	
	/**
	 * get now system time  of Unix timestamp format
	 * @return
	 */
	public static int now(){
//		return (int) (System.currentTimeMillis() / 1000L);
		return getUnixTimestamp(new Date());
	}
}
