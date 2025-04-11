package com.exp.spring_ai_demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationRunner;


@SpringBootApplication
public class SpringAiDemoApplication implements ApplicationRunner {
    @Value("${system.properties.https.proxyHost:}")
    private String proxyHost;

    @Value("${system.properties.https.proxyPort:}")
    private String proxyPort;

    public static void main(String[] args) {
        SpringApplication.run(SpringAiDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!proxyHost.isEmpty() && !proxyPort.isEmpty()) {
            // 可选：配置代理，访问openai时加上防止链接超时
            System.setProperty("https.proxyHost", proxyHost);
            System.setProperty("https.proxyPort", proxyPort);
            System.out.println("✅ 已设置代理: " + proxyHost + ":" + proxyPort);
        }
    }
}
