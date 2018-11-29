package com.imstlife.marvsmart_mqtt;

/**
 * Created by hengweiyu on 2018/8/29.
 */

public class MQTTInfo {
    private String code;//推送升级 1001
    private CheckUpdateResponse info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CheckUpdateResponse getInfo() {
        return info;
    }

    public void setInfo(CheckUpdateResponse info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "MQTTInfo{" +
                "code='" + code + '\'' +
                ", info=" + info +
                '}';
    }
}
