package com.example.paperpersonalizing.config;

import com.gearwenxin.client.ernie.ErnieBotClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErnieBotClientConfig {

    @Bean
    public ErnieBotClient ernieBotClient() {
        // 配置并返回ErnieBotClient的实例
        return new ErnieBotClient() {
            @Override
            protected String getAccessToken() {
                return null;
            }
        };
    }
}
