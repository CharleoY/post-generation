package com.umidbek.webapi.controller;

import com.umidbek.webapi.dto.Prediction;
import com.umidbek.webapi.dto.open.ai.GenerationRequest;
import com.umidbek.webapi.dto.open.ai.Response;
import com.umidbek.webapi.exception.OpenAiException;
import com.umidbek.webapi.service.ImageGeneratorService;
import com.umidbek.webapi.service.OpenAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/open-ai")
public class OpenAIController {

    private static final Logger LOGGER = Logger.getLogger(OpenAIController.class.getCanonicalName());
    private final OpenAiService openAiService;
    private final ImageGeneratorService imageGeneratorService;

    public OpenAIController(OpenAiService openAiService, ImageGeneratorService imageGeneratorService) {
        this.openAiService = openAiService;
        this.imageGeneratorService = imageGeneratorService;
    }

    @GetMapping("/prediction/{username}")
    public ResponseEntity<?> getPrediction(@PathVariable String username) {
        try {
            Prediction response = openAiService.getPrediction(username);

            return ResponseEntity.ok(response);
        } catch (OpenAiException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-image")
    public ResponseEntity<String> getImageUrl() throws IOException, InterruptedException {

        String path = imageGeneratorService.getGeneratedImageUrl();
        String url = "{\"url\":\"" + path + "\"}";
        return ResponseEntity.ok(url);
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generate(@RequestBody List<String> texts) {

        LOGGER.info("Request received: " + texts.toString());

        try {
            Response response = openAiService.sentTextsToOpenAi(texts);

            return ResponseEntity.ok(response);
        } catch (OpenAiException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/generate/with/properties")
    public ResponseEntity<?> generateWithProperties(@RequestBody GenerationRequest generationRequest) {
        LOGGER.info("Request received: " + generationRequest.toString());

        try {
            Response response = openAiService.sentTextsToOpenAi(generationRequest.getTexts(), generationRequest.getProperty());

            return ResponseEntity.ok(response);
        } catch (OpenAiException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
