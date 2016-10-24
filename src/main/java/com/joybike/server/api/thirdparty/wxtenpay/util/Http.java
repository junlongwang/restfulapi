package com.joybike.server.api.thirdparty.wxtenpay.util;

public class Http extends CPHttpConnection {

    /**
     * 构�?�方法，获得接收请求的地�?，端口号和超时时�?
     * 
     * @param httpURL
     *            http地址，以http://�?�?
     * @param timeOut
     *            http超时时间
     */
    public Http(String httpURL, String timeOut) {
        URL = httpURL;

        this.timeOut = timeOut;
    }

    public byte[] getReceiveData() {
        return receiveData;
    }
}