package com.qcloud.iot.other;

import android.content.Context;

import com.qcloud.iot.mqtt.TXMqttActionCallBack;
import com.qcloud.iot.mqtt.TXMqttConnection;
import com.qcloud.iot.mqtt.TXMqttConstants;
import com.qcloud.iot.util.AsymcSslUtils;
import com.qcloud.iot.util.TXLog;

import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;


public class Airconditioner {

    public static final String TAG = "iot.scenarized.Airconditioner";

    /**
     * 产品ID
     */
    private static final String PRODUCT_ID = "9ZUNHLTDG3";

    /**
     * 设备名称
     */
    protected static final String DEVICE_NAME = "493668431";

  
    /**
     * 密钥
     */
    private static final String SECRET_KEY = "c6PXTEye9TdcQ3bzFLMDtOhpmY9f1CKM";
    /**
     * 设备证书名
     */
    private static final String DEVICE_CERT_NAME = "493668431_cert.crt";

    /**
     * 设备私钥文件名
     */
    private static final String DEVICE_KEY_NAME = "493668431_private.key";

    private Context mContext;

    private TXMqttConnection mqttConnection;

    private MqttConnectOptions options;

    public Airconditioner(Context context, TXMqttActionCallBack callBack) {
        this.mContext = context;

        mqttConnection = new TXMqttConnection(mContext, PRODUCT_ID, DEVICE_NAME, SECRET_KEY, callBack);

        options = new MqttConnectOptions();
        options.setConnectionTimeout(8);
        options.setKeepAliveInterval(240);
        options.setAutomaticReconnect(true);
        options.setSocketFactory(AsymcSslUtils.getSocketFactoryByAssetsFile(mContext, DEVICE_CERT_NAME, DEVICE_KEY_NAME));

        mqttConnection.connect(options, null);
        DisconnectedBufferOptions bufferOptions = new DisconnectedBufferOptions();
        bufferOptions.setBufferEnabled(true);
        bufferOptions.setBufferSize(1024);
        bufferOptions.setDeleteOldestMessages(true);
        mqttConnection.setBufferOpts(bufferOptions);
    }

    public void subScribeTopic() {
        TXLog.d(TAG, "subScribeTopic");
        String topic = String.format("%s/%s/%s", PRODUCT_ID, DEVICE_NAME, "control");
        mqttConnection.subscribe(topic, TXMqttConstants.QOS1, null);
    }

    public void closeConnection() {
        if (null != mqttConnection) {
            mqttConnection.disConnect(null);
        }
    }
}
