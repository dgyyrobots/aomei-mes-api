package com.dofast.framework.common.config;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;


@Configuration
@ConfigurationProperties(prefix = "mqtt")
@Data
public class MqttConfig {

    private String brokerUrl;
    private String clientId;
    private String username;
    private String password;
    private String topic;
    private int qos;
    private int connectionTimeout;
    private int keepAliveInterval;

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setServerURIs(new String[]{brokerUrl});
        options.setConnectionTimeout(connectionTimeout);
        options.setKeepAliveInterval(keepAliveInterval);
        options.setAutomaticReconnect(true); // 自动重连
        return options;
    }

    @Bean
    public IMqttClient mqttClient() throws MqttException {
        System.out.println("brokerUrl:" + brokerUrl);
        System.out.println("clientId:" + clientId);
        if (clientId == null) {
            throw new IllegalArgumentException("clientId不能为空");
        }
        IMqttClient client = new MqttClient(brokerUrl, clientId);
        client.connect(mqttConnectOptions());
        return client;
    }


}
