/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlatformApplication {
    private static final Logger log = LoggerFactory.getLogger(PlatformApplication.class);

    public static void main(String[] args) {
        log.info("Starting the application");
        SpringApplication.run(PlatformApplication.class, args);
    }
}
