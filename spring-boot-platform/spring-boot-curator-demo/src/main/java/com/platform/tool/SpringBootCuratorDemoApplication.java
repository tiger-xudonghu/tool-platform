package com.platform.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.platform.tool"})
public class SpringBootCuratorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCuratorDemoApplication.class, args);
    }

}
