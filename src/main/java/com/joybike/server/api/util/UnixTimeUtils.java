package com.joybike.server.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	public static int StringDateToInt(String StringDate){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = null;
		int iDate = 0;
		try {

			date = df.parse(StringDate);
			iDate = (int) (date.getTime()/1000L);
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return iDate;
	}

}
