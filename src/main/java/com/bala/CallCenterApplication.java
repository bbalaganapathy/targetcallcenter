package com.bala;

/**
 * Created by hp on 11/29/2017.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.bala"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class CallCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallCenterApplication.class, args);
    }
}