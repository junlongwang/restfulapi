package com.joybike.server.api.thirdparty.huaxinSdk;

public class RequestParametersHolder {
    private HuaXinMap protocalMustParams;
    private HuaXinMap protocalOptParams;
    private HuaXinMap applicationParams;

    public RequestParametersHolder() {
    }

    public HuaXinMap getProtocalMustParams() {
        return this.protocalMustParams;
    }

    public void setProtocalMustParams(HuaXinMap protocalMustParams) {
        this.protocalMustParams = protocalMustParams;
    }

    public HuaXinMap getProtocalOptParams() {
        return this.protocalOptParams;
    }

    public void setProtocalOptParams(HuaXinMap protocalOptParams) {
        this.protocalOptParams = protocalOptParams;
    }

    public HuaXinMap getApplicationParams() {
        return this.applicationParams;
    }

    public void setApplicationParams(HuaXinMap applicationParams) {
        this.applicationParams = applicationParams;
    }
}

