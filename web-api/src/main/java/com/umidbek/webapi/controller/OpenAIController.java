package com.umidbek.webapi.controller;

import com.umidbek.webapi.dto.open.ai.GenerationRequest;
import com.umidbek.webapi.dto.open.ai.Property;
import com.umidbek.webapi.dto.open.ai.Response;
import com.umidbek.webapi.exception.OpenAiException;
import com.umidbek.webapi.service.OpenAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/open-ai")
public class OpenAIController {

    private static final Logger LOGGER = Logger.getLogger(OpenAIController.class.getCanonicalName());
    private final OpenAiService openAiService;

    public OpenAIController(OpenAiService openAiService) {
        this.openAiService = openAiService;
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
