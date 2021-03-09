package com.umidbek.webapi.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class ImageGeneratorService {

    private static final Logger LOGGER = Logger.getLogger(ImageGeneratorService.class.getCanonicalName());

    public static void main(String[] args) throws IOException, InterruptedException {
        new ImageGeneratorService().getGeneratedImageUrl();
    }

    public String getGeneratedImageUrl() throws IOException, InterruptedException {

        Path path =
                FileSystems.getDefault().getPath("./").toAbsolutePath().getParent();

        String pathToGanModel = path.toString() + "/gan/";

        Process pr = Runtime.getRuntime().exec("python3 generate_figures.py");
        LOGGER.info("Model is running at" + new Date().toString());
        int exitVal = pr.waitFor();
        LOGGER.info("Model finished you job, exit value is : " + exitVal);

        List<Path> list = Files.list(Paths.get(pathToGanModel + "/results/"))
                .filter(p -> p.toFile().isFile())
                .collect(Collectors.toList());

        LOGGER.info("count = " + list.size());

        int i = new Random().nextInt(list.size());
        String date = new Date().getTime() + "";

        Path randomPath = list.get(i);

        LOGGER.info("Selected: " + randomPath.toString());

        Path destinationPath = Paths.get(path.toString() + "/images/" + date + ".png");

        Files.copy(randomPath, destinationPath, REPLACE_EXISTING);

        return date + ".png";

    }
}
