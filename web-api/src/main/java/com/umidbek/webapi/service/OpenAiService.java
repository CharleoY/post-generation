package com.umidbek.webapi.service;

import com.google.gson.Gson;
import com.umidbek.webapi.dto.open.ai.Property;
import com.umidbek.webapi.dto.open.ai.Response;
import com.umidbek.webapi.exception.OpenAiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@Service
public class OpenAiService {

    private static final Logger LOGGER = Logger.getLogger(OpenAiService.class.getCanonicalName());

    @Value("${open.ai.text}")
    private String text;

    @Value("${open.ai.url}")
    private String url;

    @Value("${open.ai.token}")
    private String token;

    @Value("${open.ai.temperature}")
    private Double temperature;

    @Value("${open.ai.max_tokens}")
    private int maxTokens;

    @Value("${open.ai.top_p}")
    private int top;

    @Value("${open.ai.frequency_penalty}")
    private int frequencyPenalty;

    @Value("${open.ai.presence_penalty}")
    private int presencePenalty;

    public Response sentTextsToOpenAi(List<String> texts) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);

        Property property = new Property();
        property.setTop(top);
        property.setMaxTokens(maxTokens);
        property.setPrompt(ideas(texts));
        property.setTemperature(temperature);
        property.setPresencePenalty(presencePenalty);
        property.setFrequencyPenalty(frequencyPenalty);

        String json = new Gson().toJson(property);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        try {
            ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Response.class);

            LOGGER.info("Response from openai: " + responseEntity.getBody().toString());
            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e) {
            LOGGER.warning("Client exception message: " + e.getMessage());
            throw new OpenAiException(e.getMessage());
        }
        catch (HttpServerErrorException e) {
            LOGGER.warning("Server exception message: " + e.getMessage());
            throw new OpenAiException(e.getMessage());
        }
        catch (Exception e) {
            LOGGER.warning("Exception message: " + e.getMessage());
            throw new OpenAiException(e.getMessage());
        }
    }

    public Response sentTextsToOpenAi(List<String> texts, Property property) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);

        property.setPrompt(ideas(texts));
        String properties = new Gson().toJson(property);

        HttpEntity<String> entity = new HttpEntity<>(properties, headers);

        try {
            ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Response.class);

            LOGGER.info("Response from openai: " + responseEntity.getBody().toString());
            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e) {
            LOGGER.warning("Client exception message: " + e.getMessage());
            throw new OpenAiException(e.getMessage());
        }
        catch (HttpServerErrorException e) {
            LOGGER.warning("Server exception message: " + e.getMessage());
            throw new OpenAiException(e.getMessage());
        }
        catch (Exception e) {
            LOGGER.warning("Exception message: " + e.getMessage());
            throw new OpenAiException(e.getMessage());
        }
    }

    private String ideas(List<String> texts) {
        StringBuilder builder = new StringBuilder();
        builder.append(text).append(".\n");

        final int[] count = {0};
        texts.forEach(line -> {
            count[0]++;
            builder.append(line).append(System.getProperty("line.separator"));
        });

        return builder.toString();
    }

}