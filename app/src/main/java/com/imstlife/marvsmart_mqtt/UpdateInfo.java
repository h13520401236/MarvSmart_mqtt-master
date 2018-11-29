package com.imstlife.marvsmart_mqtt;

/**
 * Created by lihaifeng on 17/3/18.
 */

public class UpdateInfo {

    private int id;
    private String key;
    private String time;
    private int versionCode;
    private String versionName;
    private String downLoadUrl;
    private String log;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", time='" + time + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", downLoadUrl='" + downLoadUrl + '\'' +
                ", log='" + log + '\'' +
                '}';
    }
}
