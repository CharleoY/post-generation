package com.umidbek.webapi.dto.twitter;

import lombok.Data;

import java.util.Date;

@Data
public class Datum{
    public Date created_at;
    public String id;
    public String text;
    public String author_id;
    public String in_reply_to_user_id;
    public Entities entities;
    public String conversation_id;
}

