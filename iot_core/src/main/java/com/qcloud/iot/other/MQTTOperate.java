package com.qcloud.iot.other;

import android.content.Context;
import android.util.Log;

import com.qcloud.iot.common.Status;
import com.qcloud.iot.mqtt.TXMqttActionCallBack;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by hengweiyu on 2018/8/24.
 */

public class MQTTOperate {

    private static MQTTOperate mqttOperate;
    private final MQTTSample mMQTTSample;

    public static MQTTOperate getMQTTHandler(Context context, String productID, String deviceId, String secretKey) {
        if (mqttOperate == null) {
            synchronized (MQTTOperate.class) {
                if (mqttOperate == null) {
                    mqttOperate = new MQTTOperate(context, productID, deviceId, secretKey);
                }
            }
        }
        return mqttOperate;
    }

    public MQTTOperate(Context context, String productID, String deviceId, String secretKey) {
        mMQTTSample = new MQTTSample(context, productID, deviceId, secretKey, new SelfMqttActionCallBack());
    }


    /**
     * 订阅
     *
     * @param operate 操作名称
     */
    public void mqttSubscribeTopic(String operate) {
        mMQTTSample.subscribeTopic(operate);
    }

    /**
     * 订阅
     *
     * @param operate 操作名称
     */
    public void mqttSubscribeTopic2(String operate) {
        mMQTTSample.subscribeTopic2(operate);
    }

    /**
     * 取消订阅
     *
     * @param operate 操作名称
     */
    public void mqttUnSubscribeTopic(String operate) {
        mMQTTSample.unSubscribeTopic(operate);
    }

    /**
     * 建立连接
     */
    public void mqttConnect() {
        mMQTTSample.connect();
    }

    /**
     * 取消连接
     */
    public void mqttUnConnect() {
        mMQTTSample.disconnect();
    }

    /**
     * 发布消息
     *
     * @param operate 参数名称
     * @param data    传递数据的map集合
     */
    public void mqttPushData(String operate, HashMap<String, String> data) {
        mMQTTSample.publishTopic(operate, data);
    }

    private MQTTRecDataListener mListener;

    public void setMQTTListener(MQTTRecDataListener listener) {
        this.mListener = listener;
    }

    public interface MQTTRecDataListener {
        void onConnectCompleted(Status status, boolean reconnect, Object userContext, String msg);

        void onConnectionLost(Throwable cause);

        void onDisconnectCompleted(Status status, Object userContext, String msg);

        void onPublishCompleted(Status status, IMqttToken token, Object userContext, String errMsg);

        void onSubscribeCompleted(Status status, IMqttToken asyncActionToken, Object userContext, String errMsg);

        void onMessageReceived(final String topic, final MqttMessage message);

        void onUnSubscribeCompleted(Status status, IMqttToken asyncActionToken, Object userContext, String errMsg);
    }

    /**
     * 实现TXMqttActionCallBack回调接口
     */
    private class SelfMqttActionCallBack extends TXMqttActionCallBack {

        @Override
        public void onConnectCompleted(Status status, boolean reconnect, Object userContext, String msg) {
            Log.e("recData", "onConnectCompleted: 连接成功");
            String userContextInfo = "";
            if (userContext instanceof MQTTRequest) {
                userContextInfo = userContext.toString();
            }
            String logInfo = String.format("onConnectCompleted, status[%s], reconnect[%b], userContext[%s], msg[%s]",
                    status.name(), reconnect, userContextInfo, msg);
            mListener.onConnectCompleted(status, reconnect, userContext, msg);
        }

        @Override
        public void onConnectionLost(Throwable cause) {
            String logInfo = String.format("onConnectionLost, cause[%s]", cause.toString());
            mListener.onConnectionLost(cause);
        }

        @Override
        public void onDisconnectCompleted(Status status, Object userContext, String msg) {
            String userContextInfo = "";
            if (userContext instanceof MQTTRequest) {
                userContextInfo = userContext.toString();
            }
            String logInfo = String.format("onDisconnectCompleted, status[%s], userContext[%s], msg[%s]", status.name(), userContextInfo, msg);
            mListener.onDisconnectCompleted(status, userContext, msg);
        }

        @Override
        public void onPublishCompleted(Status status, IMqttToken token, Object userContext, String errMsg) {
            String userContextInfo = "";
            if (userContext instanceof MQTTRequest) {
                userContextInfo = userContext.toString();
            }
            String logInfo = String.format("onPublishCompleted, status[%s], topics[%s],  userContext[%s], errMsg[%s]",
                    status.name(), Arrays.toString(token.getTopics()), userContextInfo, errMsg);
            mListener.onPublishCompleted(status, token, userContext, errMsg);
        }

        @Override
        public void onSubscribeCompleted(Status status, IMqttToken asyncActionToken, Object userContext, String errMsg) {
            Log.e("recData", "onSubscribeCompleted: 订阅成功");
            String userContextInfo = "";
            if (userContext instanceof MQTTRequest) {
                userContextInfo = userContext.toString();
            }
            String logInfo = String.format("onSubscribeCompleted, status[%s], topics[%s], userContext[%s], errMsg[%s]",
                    status.name(), Arrays.toString(asyncActionToken.getTopics()), userContextInfo, errMsg);
            mListener.onSubscribeCompleted(status, asyncActionToken, userContext, errMsg);
        }

        @Override
        public void onUnSubscribeCompleted(Status status, IMqttToken asyncActionToken, Object userContext, String errMsg) {
            String userContextInfo = "";
            if (userContext instanceof MQTTRequest) {
                userContextInfo = userContext.toString();
            }
            String logInfo = String.format("onUnSubscribeCompleted, status[%s], topics[%s], userContext[%s], errMsg[%s]",
                    status.name(), Arrays.toString(asyncActionToken.getTopics()), userContextInfo, errMsg);
            mListener.onUnSubscribeCompleted(status, asyncActionToken, userContext, errMsg);
        }

        @Override
        public void onMessageReceived(final String topic, final MqttMessage message) {
            String logInfo = String.format("receive command, topic[%s], message[%s]", topic, message.toString());
            mListener.onMessageReceived(topic, message);
        }
    }


}
