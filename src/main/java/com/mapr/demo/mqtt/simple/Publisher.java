package com.mapr.demo.mqtt.simple;

import com.mapr.demo.mqtt.sampleUtil.SampleUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher {


  public static void main(String[] args) throws MqttException {


    String clientEndpoint =  SampleUtil.getConfig("clientEndpoint");

    String certificateFile =SampleUtil.getConfig("certificateFile");
    String privateKeyFile = SampleUtil.getConfig("privateKeyFile");
    String testTopic = SampleUtil.getConfig("thingName");
    String caCertFile = SampleUtil.getConfig("caCert");


    System.out.println("== START PUBLISHER ==");

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

    client.connect(options);


    int counter = 0;
    while (true) {
      if(client.isConnected()) {
        String messageString = "Hello World from "+counter;

        client.publish(testTopic, messageString.getBytes(),0,false);

        System.out.println("\tMessage '" + messageString + "' to 'iot_data'");

        counter++;
      }
    }

  }


}
