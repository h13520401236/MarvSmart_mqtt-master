package com.qcloud.iot.mqtt;


public interface TXOTAConstansts {
    enum ReportState {
        BURNING,    //正在升级
        FAIL,       //升级失败
        DONE,       //升级完成
    }
}
