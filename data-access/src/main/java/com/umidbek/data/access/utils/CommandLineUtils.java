package com.umidbek.data.access.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Path;

public class CommandLineUtils implements CommandLineRunner
{
    private final String id;

    public CommandLineUtils(String id) {
        this.id = id;
    }

    public static Path loadProfileTweets() throws IOException {
        return new ClassPathResource("sample2.json").getFile().toPath();
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
