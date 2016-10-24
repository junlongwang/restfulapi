package com.joybike.server.api.thirdparty.wxtenpay.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

class CPHostnameVerifier implements HostnameVerifier {
    public CPHostnameVerifier() {
    }

    public boolean verify(String hostname, SSLSession session) {

        return true;
    }
}
