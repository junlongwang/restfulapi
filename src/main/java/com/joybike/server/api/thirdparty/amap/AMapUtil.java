package com.joybike.server.api.thirdparty.amap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;

public class AMapUtil {
	public static void main(String[] args) throws Exception {
		/*String str="116.482498,39.984253;116.482468,39.984219;116.48217,39.983971;116.481667,39.983494;116.481232,39.983086;116.480728,39.982643;116.479652,39.981667;116.478661,39.980793;116.478081,39.98027;116.47805,39.980225;116.477989,39.980179;116.477692,39.979889;116.477577,39.979778;116.477203,39.979435;116.47686,39.979115;116.476715,39.978996;116.476044,39.97839;116.475105,39.977547;116.475105,39.977539;116.475037,39.977478;116.474304,39.976818;116.473877,39.976429;116.473282,39.975891;116.472763,39.97543;116.472382,39.975117;116.471321,39.974148;116.469719,39.972683;116.469086,39.972122;116.468285,39.971401;116.46653,39.969799;116.465225,39.96862;116.464951,39.968372;116.464828,39.968258;116.4646,39.968056;116.462814,39.966442;116.462769,39.966389;116.4617,39.965416;116.4608,39.964596;116.460121,39.963982;116.46003,39.963902;116.459412,39.963341;116.459129,39.963089;116.458076,39.962135;116.457954,39.962021;116.457916,39.961987;116.457329,39.961449;116.457306,39.96143;116.456947,39.961113;116.456757,39.960964;116.456657,39.96088;116.456299,39.960636;116.456039,39.960468;116.455711,39.960297;116.455505,39.960194;116.454758,39.959839;116.454582,39.959743;116.45443,39.959648;116.454185,39.959446";
		String[] split = str.split(";");
		List<String> list = new ArrayList<String>();
		for (String string : split) {
			list.add(string);
		}
		print(list);//生成图片
		Integer distance = distances(list);//计算距离
		System.out.println("总距离是："+distance);*/
		String str = "116.48217,39.983971";
		String address = getAddress(str);//获取地址
		System.out.println(address);
	}
	/**
	 * 逆地理编码
	 * @param position 坐标
	 * @return	地址
	 */
	public static String getAddress(String position){
		String address = null;
		String url="http://restapi.amap.com/v3/geocode/regeo?key=ee95e52bf08006f63fd29bcfbcf21df0&poitype=%E5%95%86%E5%8A%A1%E5%86%99%E5%AD%97%E6%A5%BC&radius=1000&extensions=all&batch=false&roadlevel=1&output=json&location="+position;
		JSONObject json = readJsonFromUrl(url);
		System.out.println(json);
		try {
			address=(String)((JSONObject)json.get("regeocode")).get("formatted_address");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		catch (Exception e)
		{

		}
		return address;
	}
	/**
	 * 距离计算
	 * @param list<String>
	 * @return Integer
	 */
	public static Integer distances(List<String> list){
		Integer distance=0;
		for (int i = 0; i < list.size()-1; i++) {
			Integer cal = AMapUtil.calculate(list.get(i), list.get(i+1),"3");
			distance+=cal;
		}
		return distance;
	}

	/**
	 * 生成图片
	 * @param list<String>
	 */
	public static void print(List<String> list){
		String zoom="12";//地图级别
		String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String path = "E:/image/";                      //把要下载的图片存档在image文件夹下
		File dir = new File(path);
		if(!dir.exists())
			dir.mkdir();                                                //如果目录不存在则创建目录
		path += name + ".jpg";
		String strUrl="http://restapi.amap.com/v3/staticmap?key=ee95e52bf08006f63fd29bcfbcf21df0&zoom="+zoom+"&scale=1&size=1024*1024";
		strUrl+="&markers=mid,0x26df3f,起:"+list.get(0)+"|mid,0xFF0000,终:"+list.get(list.size()-1)+"&paths=8,0x0000ff,1,,:";
		String str=""+list.get(0);
		for (int i = 1; i < list.size(); i++) {
			str+=";"+list.get(i);
		}
		download(strUrl+str,path);
	}

	/**
	 *
	 * @param start 起点
	 * @param end	终点
	 * @param type	距离计算方式   0：直线距离    1：驾车规划距离   2：公交规划距离   3：步行规划距离（5km以内）
	 * @return 	Integer	两点间距离
	 * @throws Exception
	 */
	private static Integer calculate(String start,String end,String type){
		String strUrl="http://restapi.amap.com/v3/distance?key=ee95e52bf08006f63fd29bcfbcf21df0&type="+type+"&origins="+start+"&destination="+end;
		int distance=0;
		JSONObject json = readJsonFromUrl(strUrl);
		try {
			distance=Integer.parseInt((String)((JSONObject)((JSONArray) json.get("results")).get(0)).get("distance"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return distance;
	}
	/**
	 *
	 * @param url 要访问的URL地址
	 * @return		访问URL返回的数据（以JSONObject的形式）
	 */
	private static JSONObject readJsonFromUrl(String url) {
		InputStream is = null;
		JSONObject json = null;
		try {
			is = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			String jsonText = sb.toString();
			json = new JSONObject(jsonText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		return json;
	}

	/**
	 * @param strUrl 图片的URL地址
	 * @param path 图片保存路径
	 */
	private static void download(String strUrl, String path) {
		URL url = null;
		try {
			url = new URL(strUrl);
		} catch (MalformedURLException e2) {
			//e2.printStackTrace();
			return;
		}
		InputStream is = null;
		try {
			is = url.openStream();
		} catch (IOException e1) {
			//e1.printStackTrace();
			return;
		}

		OutputStream os = null;
		try {
			os = new FileOutputStream(path);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0,
					8192)) != -1) { /*
									 * buffer数组存放读取的字节，如果因为流位于文件末尾而没有可用的字节，则返回值-
									 * 1，以整数形式返回实际读取的字节数
									 */
				os.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			return;
		} finally {
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
	}
}

