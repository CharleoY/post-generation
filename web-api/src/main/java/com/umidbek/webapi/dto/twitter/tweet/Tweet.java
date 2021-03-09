package com.umidbek.webapi.dto.twitter.tweet;

import lombok.Data;

import java.util.List;

@Data
public class Tweet {
    private String text;
    private List<String> urls;
}
