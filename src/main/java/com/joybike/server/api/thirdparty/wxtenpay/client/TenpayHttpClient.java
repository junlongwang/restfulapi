package com.joybike.server.api.thirdparty.wxtenpay.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import com.joybike.server.api.thirdparty.wxtenpay.util.*;

/**
 * 财付通http或�?�https网络通信客户�?<br/>
 * ========================================================================<br/>
 * api说明�?<br/>
 * setReqContent($reqContent),设置请求内容，无论post和get，都用get方式提供<br/>
 * getResContent(), 获取应答内容<br/>
 * setMethod(method),设置请求方法,post或�?�get<br/>
 * getErrInfo(),获取错误信息<br/>
 * setCertInfo(certFile, certPasswd),设置证书，双向https时需要使�?<br/>
 * setCaInfo(caFile), 设置CA，格式未pem，不设置则不�?�?<br/>
 * setTimeOut(timeOut)�? 设置超时时间，单位秒<br/>
 * getResponseCode(), 取返回的http状�?�码<br/>
 * call(),真正调用接口<br/>
 * getCharset()/setCharset(),字符集编�?<br/>
 * 
 * ========================================================================<br/>
 *
 */
public class TenpayHttpClient {
	
	private static final String USER_AGENT_VALUE = 
		"Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)";
	
	private static final String JKS_CA_FILENAME = 
		"tenpay_cacert.jks";
	
	private static final String JKS_CA_ALIAS = "tenpay";
	
	private static final String JKS_CA_PASSWORD = "";
	
	/** ca证书文件 */
	private File caFile;
	
	/** 证书文件 */
	private File certFile;
	
	/** 证书密码 */
	private String certPasswd;
	
	/** 请求内容，无论post和get，都用get方式提供 */
	private String reqContent;
	
	/** 应答内容 */
	private String resContent;
	
	/** 请求方法 */
	private String method;
	
	/** 错误信息 */
	private String errInfo;
	
	/** 超时时间,以秒为单�? */
	private int timeOut;
	
	/** http应答编码 */
	private int responseCode;
	
	/** 字符编码 */
	private String charset;
	
	private InputStream inputStream;
	
	public TenpayHttpClient() {
		this.caFile = null;
		this.certFile = null;
		this.certPasswd = "";
		
		this.reqContent = "";
		this.resContent = "";
		this.method = "POST";
		this.errInfo = "";
		this.timeOut = 30;//30�?
		
		this.responseCode = 0;
		this.charset = "GBK";
		
		this.inputStream = null;
	}

	/**
	 * 设置证书信息
	 * @param certFile 证书文件
	 * @param certPasswd 证书密码
	 */
	public void setCertInfo(File certFile, String certPasswd) {
		this.certFile = certFile;
		this.certPasswd = certPasswd;
	}
	
	/**
	 * 设置ca
	 * @param caFile
	 */
	public void setCaInfo(File caFile) {
		this.caFile = caFile;
	}
	
	/**
	 * 设置请求内容
	 * @param reqContent 表求内容
	 */
	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}
	
	/**
	 * 获取结果内容
	 * @return String
	 * @throws IOException 
	 */
	public String getResContent() {
		try {
			this.doResponse();
		} catch (IOException e) {
			this.errInfo = e.getMessage();
			//return "";
		}
		
		return this.resContent;
	}
	
	/**
	 * 设置请求方法post或�?�get
	 * @param method 请求方法post/get
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	
	/**
	 * 获取错误信息
	 * @return String
	 */
	public String getErrInfo() {
		return this.errInfo;
	}
	
	/**
	 * 设置超时时间,以秒为单�?
	 * @param timeOut 超时时间,以秒为单�?
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	/**
	 * 获取http状�?�码
	 * @return int
	 */
	public int getResponseCode() {
		return this.responseCode;
	}
	
	/**
	 * 执行http调用。true:成功 false:失败
	 * @return boolean
	 */
	public boolean call() {
		
		boolean isRet = false;
		
		//http
		if(null == this.caFile && null == this.certFile) {
			try {
				this.callHttp();
				isRet = true;
			} catch (IOException e) {
				this.errInfo = e.getMessage();
			}
			return isRet;
		}
		
		//https
		try {
			this.callHttps();
			isRet = true;
		} catch (UnrecoverableKeyException e) {
			this.errInfo = e.getMessage();
		} catch (KeyManagementException e) {
			this.errInfo = e.getMessage();
		} catch (CertificateException e) {
			this.errInfo = e.getMessage();
		} catch (KeyStoreException e) {
			this.errInfo = e.getMessage();
		} catch (NoSuchAlgorithmException e) {
			this.errInfo = e.getMessage();
		} catch (IOException e) {
			this.errInfo = e.getMessage();
		}
		
		return isRet;
		
	}
	
	protected void callHttp() throws IOException {
		
		if("POST".equals(this.method.toUpperCase())) {
			String url = HttpClientUtil.getURL(this.reqContent);
			String queryString = HttpClientUtil.getQueryString(this.reqContent);
			byte[] postData = queryString.getBytes(this.charset);
			this.httpPostMethod(url, postData);
			
			return ;
		}
		
		this.httpGetMethod(this.reqContent);
		
	} 
	
	protected void callHttps() throws IOException, CertificateException,
			KeyStoreException, NoSuchAlgorithmException,
			UnrecoverableKeyException, KeyManagementException {

		// ca目录
		String caPath = this.caFile.getParent();

		File jksCAFile = new File(caPath + "/"
				+ TenpayHttpClient.JKS_CA_FILENAME);
		if (!jksCAFile.isFile()) {
			X509Certificate cert = (X509Certificate) HttpClientUtil
					.getCertificate(this.caFile);

			FileOutputStream out = new FileOutputStream(jksCAFile);

			// store jks file
			HttpClientUtil.storeCACert(cert, TenpayHttpClient.JKS_CA_ALIAS,
					TenpayHttpClient.JKS_CA_PASSWORD, out);

			out.close();

		}

		FileInputStream trustStream = new FileInputStream(jksCAFile);
		FileInputStream keyStream = new FileInputStream(this.certFile);

		SSLContext sslContext = HttpClientUtil.getSSLContext(trustStream,
				TenpayHttpClient.JKS_CA_PASSWORD, keyStream, this.certPasswd);
		
		//关闭�?
		keyStream.close();
		trustStream.close();
		
		if("POST".equals(this.method.toUpperCase())) {
			String url = HttpClientUtil.getURL(this.reqContent);
			String queryString = HttpClientUtil.getQueryString(this.reqContent);
			byte[] postData = queryString.getBytes(this.charset);
			
			this.httpsPostMethod(url, postData, sslContext);
			
			return ;
		}
		
		this.httpsGetMethod(this.reqContent, sslContext);

	}
	
	public boolean callHttpPost(String url, String postdata) {
		boolean flag = false;
		byte[] postData;
		try {
			postData = postdata.getBytes(this.charset);
			this.httpPostMethod(url, postData);
			flag = true;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 以http post方式通信
	 * @param url
	 * @param postData
	 * @throws IOException
	 */
	protected void httpPostMethod(String url, byte[] postData)
			throws IOException {

		HttpURLConnection conn = HttpClientUtil.getHttpURLConnection(url);

		this.doPost(conn, postData);
	}
	
	/**
	 * 以http get方式通信
	 * 
	 * @param url
	 * @throws IOException
	 */
	protected void httpGetMethod(String url) throws IOException {
		
		HttpURLConnection httpConnection =
			HttpClientUtil.getHttpURLConnection(url);
		
		this.setHttpRequest(httpConnection);
		
		httpConnection.setRequestMethod("GET");
		
		this.responseCode = httpConnection.getResponseCode();
		
		this.inputStream = httpConnection.getInputStream();
		
	}
	
	/**
	 * 以https get方式通信
	 * @param url
	 * @param sslContext
	 * @throws IOException
	 */
	protected void httpsGetMethod(String url, SSLContext sslContext)
			throws IOException {

		SSLSocketFactory sf = sslContext.getSocketFactory();

		HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);

		conn.setSSLSocketFactory(sf);

		this.doGet(conn);

	}
	
	protected void httpsPostMethod(String url, byte[] postData,
			SSLContext sslContext) throws IOException {

		SSLSocketFactory sf = sslContext.getSocketFactory();

		HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);

		conn.setSSLSocketFactory(sf);

		this.doPost(conn, postData);

	}
	
	/**
	 * 设置http请求默认属�??
	 * @param httpConnection
	 */
	protected void setHttpRequest(HttpURLConnection httpConnection) {
		
		//设置连接超时时间
		httpConnection.setConnectTimeout(this.timeOut * 1000);
		
		//User-Agent
		httpConnection.setRequestProperty("User-Agent", 
				TenpayHttpClient.USER_AGENT_VALUE);
		
		//不使用缓�?
		httpConnection.setUseCaches(false);
		
		//允许输入输出
		httpConnection.setDoInput(true);
		httpConnection.setDoOutput(true);
		
	}
	
	/**
	 * 处理应答
	 * @throws IOException
	 */
	protected void doResponse() throws IOException {
		
		if(null == this.inputStream) {
			return;
		}

		//获取应答内容
		this.resContent=HttpClientUtil.InputStreamTOString(this.inputStream,this.charset); 

		//关闭输入�?
		this.inputStream.close();
		
	}
	
	/**
	 * post方式处理
	 * @param conn
	 * @param postData
	 * @throws IOException
	 */
	protected void doPost(HttpURLConnection conn, byte[] postData)
			throws IOException {

		// 以post方式通信
		conn.setRequestMethod("POST");

		// 设置请求默认属�??
		this.setHttpRequest(conn);

		// Content-Type
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		BufferedOutputStream out = new BufferedOutputStream(conn
				.getOutputStream());

		final int len = 1024; // 1KB
		HttpClientUtil.doOutput(out, postData, len);

		// 关闭�?
		out.close();

		// 获取响应返回状�?�码
		this.responseCode = conn.getResponseCode();

		// 获取应答输入�?
		this.inputStream = conn.getInputStream();

	}
	
	/**
	 * get方式处理
	 * @param conn
	 * @throws IOException
	 */
	protected void doGet(HttpURLConnection conn) throws IOException {
		
		//以GET方式通信
		conn.setRequestMethod("GET");
		
		//设置请求默认属�??
		this.setHttpRequest(conn);
		
		//获取响应返回状�?�码
		this.responseCode = conn.getResponseCode();
		
		//获取应答输入�?
		this.inputStream = conn.getInputStream();
	}

	
}
