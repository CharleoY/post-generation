package com.umidbek.webapi.dto.twitter;

import lombok.Data;

import java.util.List;

@Data
public class Url{
    public int start;
    public int end;
    public String url;
    public String expanded_url;
    public String display_url;
    public List<Image> images;
    public int status;
    public String title;
    public String description;
    public String unwound_url;
}

