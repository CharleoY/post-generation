package com.umidbek.webapi.dto.twitter;

import lombok.Data;

import java.util.List;

@Data
public class Entities{
    public List<Hashtag> hashtags;
    public List<Url> urls;
    public List<Mention> mentions;
    public List<Annotation> annotations;
}
