package com.umidbek.webapi.controller;

import com.umidbek.webapi.dto.twitter.Root;
import com.umidbek.webapi.dto.twitter.user.User;
import com.umidbek.webapi.service.TwitterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/twitter")
public class TwitterIntegrationController {

    private final TwitterService twitterService;

    public TwitterIntegrationController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<?> findUser(@PathVariable String username) {
        try {
            User user = twitterService.getUserInfo(username);

            if (user.getData() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            return ResponseEntity.ok(user.getData());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find/tweet/byUsername/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        try {
            Root root = twitterService.getTweets(userId);

            return ResponseEntity.ok(root);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{userId}/findByPaginationToken/{token}")
    public ResponseEntity<?> getUser(@PathVariable String userId, @PathVariable String token) {

        try {
            return ResponseEntity.ok(twitterService.getTweetsByPaginationToken(userId, token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
