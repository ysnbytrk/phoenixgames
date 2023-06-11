package com.spotlight.platform.userprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Main class for the Userprofile application.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class UserprofileApplication {

    /**
     * Entry point of the Userprofile application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(UserprofileApplication.class, args);
    }

}
