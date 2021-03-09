package com.umidbek.webapi.dto.twitter;

import lombok.Data;

@Data
public class Meta{
    public String oldest_id;
    public String newest_id;
    public int result_count;
    public String next_token;
    public String previous_token;
}
