package com.umidbek.webapi.dto.twitter;

import lombok.Data;

@Data
public class Mention {
    public int start;
    public int end;
    public String username;
}
