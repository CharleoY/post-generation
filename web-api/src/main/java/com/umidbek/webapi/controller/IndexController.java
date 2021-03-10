package com.umidbek.webapi.controller;

import com.umidbek.data.access.service.ProfileService;
import com.umidbek.webapi.service.ImageGeneratorService;
import com.umidbek.webapi.service.OpenAiService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class IndexController {

    private final ProfileService userService;
    private final OpenAiService openAiService;
    public IndexController(ProfileService userService, OpenAiService openAiService) {
        this.userService = userService;
        this.openAiService = openAiService;
    }

    @RequestMapping(path = "/img/{name}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable String name) throws IOException {


        Path path =
                FileSystems.getDefault().getPath("./").toAbsolutePath().getParent();
        File file = new File(path.toString() + "/images/" + name);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/png");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping
    public ModelAndView index(Model model) throws IOException, InterruptedException {
        List<String> list = new ArrayList<>();
        list.add("What was the last song you played in your BMW?");
        list.add("It depends on your personal taste and preferences. If you require any assistance in making the right choice, we would suggest contacting your BMW dealer directly by telephone or online.");
        list.add("Which one is your favourite model?");

        openAiService.sentTextsToOpenAi(list);
        return new ModelAndView("index");
    }
}
