package com.imstlife.marvsmart_mqtt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qcloud.iot.common.Status;
import com.qcloud.iot.mqtt.TXMqttActionCallBack;
import com.qcloud.iot.other.MQTTRequest;
import com.qcloud.iot.other.MQTTSample;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements com.imstlife.marvsmart_mqtt.MQTTOperate.MQTTRecDataListener {

    private MQTTOperate mqttHandler;

    /**
     * 产品ID
     */
    private static final String PRODUCT_ID = "1WHKWZ0YWD";

    /**
     * 设备名称
     */
    public static final String DEVICE_NAME = "run_45_192893791";


    /**
     * 密钥
     */
    private static final String SECRET_KEY = "zlpMcfobAiEqFyA92QRcOA==";
    private String TAG = "recData";
    private MQTTSample mMQTTSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMQTTSample = new MQTTSample(this, PRODUCT_ID, DEVICE_NAME, SECRET_KEY, new SelfMqttActionCallBack());
//        mqttHandler = MQTTOperate.getMQTTHandler(this, PRODUCT_ID, DEVICE_NAME, SECRET_KEY);
//        mqttHandler = new MQTTOperate(this, PRODUCT_ID, DEVICE_NAME, SECRET_KEY);
//        mqttHandler.setMQTTListener(this);
//        com.imstlife.marvsmart_mqtt.MQTTOperate
        mqttHandler = new com.imstlife.marvsmart_mqtt.MQTTOperate(this, PRODUCT_ID, DEVICE_NAME, SECRET_KEY);
        mqttHandler.setMQTTListener(this);
    }

    public void connect(View view) {
        mqttHandler.mqttConnect();
//        mMQTTSample.connect();
    }

    public void sub(View view) {
        mqttHandler.mqttSubscribeTopic("custom_data");
//        mMQTTSample.subscribeTopic("custom_data");
    }

    @Override
    public void onConnectCompleted(Status status, boolean reconnect, Object userContext, String msg) {

    }

    @Override
    public void onConnectionLost(Throwable cause) {

    }

    @Override
    public void onDisconnectCompleted(Status status, Object userContext, String msg) {

    }

    @Override
    public void onPublishCompleted(Status status, IMqttToken token, Object userContext, String errMsg) {

    }

    @Override
    public void onSubscribeCompleted(Status status, IMqttToken asyncActionToken, Object userContext, String errMsg) {

    }

    @Override
    public void onMessageReceived(String topic, MqttMessage mqttMessage) {
        Log.e("recData", "onMessageReceived: topic = " + topic + ", message = " + mqttMessage.toString() + ".messageID = " + mqttMessage.getId());
        Log.i(TAG, "onUnSubscribeCompleted: " + Arrays.toString(mqttMessage.getPayload()));
        MQTTInfo mqttInfo = GsonUtils.deserialize(mqttMessage.toString(), MQTTInfo.class);
        decodeUnicode(mqttInfo.getInfo().getData().getLog());
        decodeUnicode(mqttInfo.getInfo().getMsg());
        Log.i(TAG, "onMessageReceived: log = " + decodeUnicode(mqttInfo.getInfo().getData().getLog()));
        Log.i(TAG, "onMessageReceived: message = " + decodeUnicode(mqttInfo.getInfo().getMsg()));
    }

    @Override
    public void onUnSubscribeCompleted(Status status, IMqttToken asyncActionToken, Object userContext, String errMsg) {

    }

    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
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
        }

        @Override
        public void onConnectionLost(Throwable cause) {
            String logInfo = String.format("onConnectionLost, cause[%s]", cause.toString());
        }

        @Override
        public void onDisconnectCompleted(Status status, Object userContext, String msg) {
            String userContextInfo = "";
            if (userContext instanceof MQTTRequest) {
                userContextInfo = userContext.toString();
            }
            String logInfo = String.format("onDisconnectCompleted, status[%s], userContext[%s], msg[%s]", status.name(), userContextInfo, msg);
        }

        @Override
        public void onPublishCompleted(Status status, IMqttToken token, Object userContext, String errMsg) {
            String userContextInfo = "";
            if (userContext instanceof MQTTRequest) {
                userContextInfo = userContext.toString();
            }
            String logInfo = String.format("onPublishCompleted, status[%s], topics[%s],  userContext[%s], errMsg[%s]",
                    status.name(), Arrays.toString(token.getTopics()), userContextInfo, errMsg);
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
        }

        @Override
        public void onUnSubscribeCompleted(Status status, IMqttToken asyncActionToken, Object userContext, String errMsg) {
            String userContextInfo = "";
            if (userContext instanceof MQTTRequest) {
                userContextInfo = userContext.toString();
            }
            String logInfo = String.format("onUnSubscribeCompleted, status[%s], topics[%s], userContext[%s], errMsg[%s]",
                    status.name(), Arrays.toString(asyncActionToken.getTopics()), userContextInfo, errMsg);
        }

        @Override
        public void onMessageReceived(final String topic, final MqttMessage message) {
            String logInfo = String.format("receive command, topic[%s], message[%s]", topic, message.toString());
            Log.i(TAG, "onMessageReceived: " + message.toString());
        }
    }
}
