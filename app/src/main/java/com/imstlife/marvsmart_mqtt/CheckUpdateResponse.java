package com.imstlife.marvsmart_mqtt;

import java.io.Serializable;

/**
 * Created by lihaifeng on 17/3/18.
 */

public class CheckUpdateResponse implements Serializable {


    private int status;

    private String msg;

    private UpdateInfo data;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UpdateInfo getData() {
        return data;
    }

    public void setData(UpdateInfo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CheckUpdateResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
