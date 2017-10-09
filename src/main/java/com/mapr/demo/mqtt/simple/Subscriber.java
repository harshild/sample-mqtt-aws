package com.mapr.demo.mqtt.simple;

import com.mapr.demo.mqtt.sampleUtil.SampleUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscriber {

  public static void main(String[] args) throws MqttException {

    System.out.println("== START SUBSCRIBER ==");

    String clientEndpoint =  SampleUtil.getConfig("clientEndpoint");

    String certificateFile =SampleUtil.getConfig("certificateFile");
    String privateKeyFile = SampleUtil.getConfig("privateKeyFile");
    String testTopic = SampleUtil.getConfig("thingName");
    String caCertFile = SampleUtil.getConfig("caCert");

    MqttClient client = new MqttClient("ssl://"+clientEndpoint+":"+8883, MqttClient.generateClientId());

    MqttConnectOptions options = new MqttConnectOptions();
    try {
      options.setSocketFactory(SampleUtil.getSocketFactory(
              caCertFile,
              certificateFile,
              privateKeyFile
      ));
    } catch (Exception e) {
      e.printStackTrace();
    }

    options.setAutomaticReconnect(true);

    client.setCallback(new SimpleMqttCallBack());
    client.connect(options);
    client.subscribe(testTopic);

  }

}
