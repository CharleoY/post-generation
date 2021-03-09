package com.umidbek.data.access;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {"com.umidbek"})
public class DataAccessApplication implements CommandLineRunner {

    public static void main(String[] args)
    {
        SpringApplication.run(DataAccessApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Runtime.getRuntime().exec("docker-compose up");
    }
}
