package com.umidbek.webapi.service;

import com.umidbek.webapi.dto.twitter.Datum;
import com.umidbek.webapi.dto.twitter.Entities;
import com.umidbek.webapi.dto.twitter.Root;
import com.umidbek.webapi.dto.twitter.user.User;
import com.umidbek.webapi.exception.TweetNotFound;
import com.umidbek.webapi.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TwitterService {

    private static final Logger LOGGER = Logger.getLogger(TwitterService.class.getCanonicalName());

    @Value("${twitter.access_token}")
    private String accessToken;

    @Value("${twitter.find.user.by.username}")
    private String findByUsername;

    @Value("${twitter.find.tweets.by.user_id}")
    private String findTweetsByUserIdUrl;

    @Value("${twitter.find.tweets.by.user_id_params}")
    private String tweetFields;

    @Value("${twitter.find.tweets.by.media.fields}")
    private String mediaFields;

    public User getUserInfo(String username) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<User> responseEntity = restTemplate.exchange(findByUsername + "/" + username, HttpMethod.GET,request, User.class);

            LOGGER.info(String.valueOf(responseEntity.getStatusCodeValue()));

            if (responseEntity.getBody() == null ||
                    responseEntity.getBody().getData() == null) {

                throw new UserNotFoundException("User not found by username: " + username);
            }

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException | HttpServerErrorException e) {
            LOGGER.warning(e.getMessage());
            throw new UserNotFoundException("User not found with username: " + username);
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new UserNotFoundException("User not found with username: " + username);
        }
    }

    public Root modify(String userId, String token) {
        Root root = getTweetsByPaginationToken(userId, token);

        if (!root.getData().isEmpty()) {
            List<Datum> datumList = root.getData();

            for( Datum datum : datumList) {
                String text = datum.getText();
                Entities entities = datum.getEntities();

                String trimmedText =
                        entities.getHashtags()
                                .stream()
                                .map(hashtag -> text.replace("#" + hashtag, ""))
                                .collect(Collectors.joining());

                datum.setText(trimmedText);
            }
        }

        return root;
    }

    public Root getTweetsByPaginationToken(String userId, String token) {
        String url = findTweetsByUserIdUrl + "/" + userId + "/tweets";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", accessToken);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("tweet.fields", tweetFields)
                .queryParam("media.fields", mediaFields)
                .queryParam("pagination_token", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Root> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Root.class);

            LOGGER.info(String.valueOf(responseEntity.getStatusCodeValue()));

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException | HttpServerErrorException e) {
            LOGGER.warning(e.getMessage());
            throw new TweetNotFound("Tweet not found with user_id:" + userId + " and pagination_token");
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new TweetNotFound("Tweet not found with user_id:" + userId + " and pagination_token");
        }
    }

    public Root getTweets(String userId) {
        String url = findTweetsByUserIdUrl + "/" + userId + "/tweets";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", accessToken);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("tweet.fields", tweetFields)
                .queryParam("media.fields", mediaFields);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Root> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Root.class);

            LOGGER.info(String.valueOf(responseEntity.getStatusCodeValue()));

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException e) {
            LOGGER.warning(e.getMessage());
            throw new TweetNotFound("Tweet not found with user_id:" + userId);

        } catch (HttpServerErrorException e) {
            LOGGER.warning(e.getMessage());
            throw new TweetNotFound("Tweet not found with user_id:" + userId);
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new TweetNotFound("Tweet not found with user_id:" + userId);
        }
    }
}
